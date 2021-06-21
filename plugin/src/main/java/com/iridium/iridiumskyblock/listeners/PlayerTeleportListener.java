package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.Color;
import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import com.iridium.iridiumskyblock.utils.LocationUtils;
import com.iridium.iridiumskyblock.utils.PlayerUtils;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;

public class PlayerTeleportListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (IridiumSkyblock.getInstance().getConfiguration().fakePlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (IridiumSkyblock.getInstance().getConfiguration().debug) {
            System.out.print("PlayerTeleport - " + event.getPlayer().getUniqueId() + "-" + event.getPlayer().getName() + "\n");
        }
        Player player = event.getPlayer();
        if (event.getTo() != null && event.getTo().getWorld() != null) {
            if (event.getTo().getWorld().equals(IridiumSkyblock.getInstance().getIslandManager().getWorld())) {
                Optional<Island> optionalIsland = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getTo());
                optionalIsland.ifPresent(island -> PlayerUtils.sendBorder(player, island));
            } else {
                Bukkit.getScheduler().runTaskLater(IridiumSkyblock.getInstance(), () -> {
                            if (isOutsideOfBorder(player, player.getWorld().getWorldBorder())) {
                                User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
                                Optional<Island> island = user.getIsland();
                                Location spawn = island.map(Island::getHome).orElseGet(() -> Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                                PaperLib.teleportAsync(player, LocationUtils.getSafeLocation(spawn, island.orElse(null)));
                                Bukkit.getScheduler().runTaskLater(IridiumSkyblock.getInstance(), () -> island.ifPresent(islandPlayer -> PlayerUtils.sendBorder(player, islandPlayer)), 1);
                                event.setCancelled(true);
                            }
                        }, 2);
                Bukkit.getScheduler().runTask(IridiumSkyblock.getInstance(), () -> IridiumSkyblock.getInstance().getNms().sendWorldBorder(player, Color.BLUE, event.getTo().getWorld().getWorldBorder().getSize(), event.getTo().getWorld().getWorldBorder().getCenter()));
            }
        }
    }

    public boolean isOutsideOfBorder(Player p, WorldBorder border) {

        Location loc = p.getLocation();
        double size = border.getSize()/2;
        Location center = border.getCenter();

        double x = loc.getX() - center.getX(), z = loc.getZ() - center.getZ();
        return ((x > size || (-x) > size) || (z > size || (-z) > size));
    }
}