package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import com.iridium.iridiumskyblock.utils.PlayerUtils;
import com.iridium.iridiumskyblock.utils.StringUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Optional;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);

        if (event.getClickedBlock() != null) {
            Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getClickedBlock().getLocation());

            String visualtool = IridiumSkyblock.getInstance().getConfiguration().visualtool;
            if (event.getItem() != null && event.getItem().getType().equals(Material.getMaterial(visualtool))) {
                Bukkit.getScheduler().runTaskLater(IridiumSkyblock.getInstance(), () -> island.ifPresent(playerIsland -> PlayerUtils.sendBorder(player, playerIsland)), 20L * 12);
            }

            if (!island.isPresent()) {
                return;
            }

            if (event.getClickedBlock().getType().name().contains("door")) {
                if (!IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().doors, "doors")) {
                    event.setCancelled(true);
                    player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotOpenDoors.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                }
            } else if (event.getClickedBlock().getState() instanceof InventoryHolder) {
                if (!IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().openContainers, "openContainers")) {
                    event.setCancelled(true);
                    player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotOpenContainers.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                }
            } else if (event.getClickedBlock().getType().equals(Material.LEVER) || event.getClickedBlock().getType().name().contains("BUTTON") || event.getClickedBlock().getType().name().contains("PRESSURE_PLATE")) {
                if (!IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().redstone, "redstone")) {
                    event.setCancelled(true);
                    player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotUseRedstone.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                }
            } else if (event.getClickedBlock().getType().equals(Material.OBSIDIAN) && event.getItem().getType().equals(Material.FLINT_AND_STEEL)) {
                if (!IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().spawnPortal, "portal")) {
                    event.setCancelled(true);
                    player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotUseSpawnPortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                }
                spawnPortal(event.getClickedBlock().getLocation(), 4,5, player.getFacing(), player);
            } else {
                if (!IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().openContainers, "openContainers")) {
                    event.setCancelled(true);
                    player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotInteract.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
        Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getRightClicked().getLocation());

        if (!island.isPresent()) {
            return;
        }

        if (IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().interactEntities, "interactEntities")) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotInteractEntities.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
    }

    public static void spawnPortal(Location loc, int length, int height, BlockFace direction, Player player) {
        World world = loc.getWorld();
        if (world == null) {
            return;
        }
        int rootX = loc.getBlockX();
        int rootY = loc.getBlockY();
        int rootZ = loc.getBlockZ();
        //increment X and Y
        if(direction == BlockFace.NORTH) {
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    if (world.getBlockAt(rootX+l, rootY+h, rootZ).getType() != Material.AIR && !world.getBlockAt(rootX+l, rootY+h, rootZ).equals(loc.getBlock())) {
                        player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotCreatePortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                        return;
                    }
                }
            }

            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX+l, rootY+h, rootZ).setType(Material.BLACK_CONCRETE, false);
                    }
                    //portal block
                    else {
                        world.getBlockAt(rootX+l, rootY+h, rootZ).setType(Material.NETHER_PORTAL, false);
                    }
                }
            }
            loc.add(length / 2d, 1, 0);
        }
        //increment Z and Y
        else if(direction == BlockFace.EAST) {
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    if (world.getBlockAt(rootX, rootY+h, rootZ+l).getType() != Material.AIR && !world.getBlockAt(rootX, rootY+h, rootZ+l).equals(loc.getBlock())) {
                        player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotCreatePortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                        return;
                    }
                }
            }
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX, rootY+h, rootZ+l).setType(Material.BLACK_CONCRETE, false);
                    }
                    //portal block
                    else {
                        Block portalBlock = world.getBlockAt(rootX, rootY+h, rootZ+l);
                        portalBlock.setType(Material.NETHER_PORTAL, false);
                        BlockData bd = portalBlock.getBlockData();
                        Orientable orientable = (Orientable) bd;
                        orientable.setAxis(Axis.Z);
                        portalBlock.setBlockData(orientable);
                    }
                }
            }
        }
        //decrement X and increment Y
        else if(direction == BlockFace.SOUTH) {
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    if (world.getBlockAt(rootX-l, rootY+h, rootZ).getType() != Material.AIR && !world.getBlockAt(rootX-l, rootY+h, rootZ).equals(loc.getBlock())) {
                        player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotCreatePortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                        return;
                    }
                }
            }
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX-l, rootY+h, rootZ).setType(Material.BLACK_CONCRETE, false);
                    }
                    //portal block
                    else {
                        world.getBlockAt(rootX-l, rootY+h, rootZ).setType(Material.NETHER_PORTAL, false);
                    }
                }
            }
        }
        //decrement Z and increment Y
        else if(direction == BlockFace.WEST) {
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    if (world.getBlockAt(rootX, rootY+h, rootZ-l).getType() != Material.AIR && !world.getBlockAt(rootX, rootY+h, rootZ-l).equals(loc.getBlock())) {
                        player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotCreatePortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                        return;
                    }
                }
            }
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX, rootY+h, rootZ-l).setType(Material.BLACK_CONCRETE, false);
                    }
                    //portal block
                    else {
                        Block portalBlock = world.getBlockAt(rootX, rootY+h, rootZ-l);
                        portalBlock.setType(Material.NETHER_PORTAL, false);
                        BlockData bd = portalBlock.getBlockData();
                        Orientable orientable = (Orientable) bd;
                        orientable.setAxis(Axis.Z);
                        portalBlock.setBlockData(orientable);
                    }
                }
            }
        }
    }
}
