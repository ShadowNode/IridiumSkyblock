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
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (IridiumSkyblock.getInstance().getConfiguration().debugFakePlayers) {
            System.out.print("PlayerInteract - " + event.getPlayer().getUniqueId() + "-" + event.getPlayer().getName() + "\n");
        }
        if (IridiumSkyblock.getInstance().getConfiguration().fakePlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
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
                if (!event.getPlayer().getWorld().equals(IridiumSkyblock.getInstance().getIslandManager().getWorld()) || !IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().spawnPortal, "portal")) {
                    player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotUseSpawnPortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                    event.setCancelled(true);
                }
                if (event.getPlayer().getInventory().containsAtLeast(new ItemStack(Material.OBSIDIAN),17)) {
                    if (spawnPortal(event.getClickedBlock().getLocation(), 4, 5, player.getFacing(), player)) {
                        player.getInventory().removeItem(new ItemStack(Material.OBSIDIAN, 17));
                    }
                } else {
                    player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotSpawnPortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                    event.setCancelled(true);
                }
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

    public static boolean spawnPortal(Location loc, int length, int height, BlockFace direction, Player player) {
        World world = loc.getWorld();
        if (world == null) {
            return false;
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
                        return false;
                    }
                }
            }
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX+l, rootY+h, rootZ).setType(Material.OBSIDIAN, false);
                    }
                    //portal block
                    else {
                        world.getBlockAt(rootX+l, rootY+h, rootZ).setType(Material.NETHER_PORTAL, false);
                    }
                }
            }
        }
        //increment Z and Y
        else if(direction == BlockFace.EAST) {
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    if (world.getBlockAt(rootX, rootY+h, rootZ+l).getType() != Material.AIR && !world.getBlockAt(rootX, rootY+h, rootZ+l).equals(loc.getBlock())) {
                        player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotCreatePortal.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
                        return false;
                    }
                }
            }
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX, rootY+h, rootZ+l).setType(Material.OBSIDIAN, false);
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
                        return false;
                    }
                }
            }
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX-l, rootY+h, rootZ).setType(Material.OBSIDIAN, false);
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
                        return false;
                    }
                }
            }
            for(int l=0; l<=length; l++) {
                for(int h=0; h<=height; h++) {
                    //obsidian walls
                    if(l == 0 || h == 0 || l == length || h == height) {
                        world.getBlockAt(rootX, rootY+h, rootZ-l).setType(Material.OBSIDIAN, false);
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
        return true;
    }
}
