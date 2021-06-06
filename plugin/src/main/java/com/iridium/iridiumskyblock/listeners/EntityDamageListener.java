package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import com.iridium.iridiumskyblock.utils.LocationUtils;
import com.iridium.iridiumskyblock.utils.StringUtils;
import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Optional;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDMG(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getFinalDamage() > player.getHealth()) {

                User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
                Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(player.getLocation());
                Optional<Island> homeIsland = user.getIsland();
                Location spawn = homeIsland.map(Island::getHome).orElseGet(() -> Bukkit.getServer().getWorlds().get(0).getSpawnLocation());

                if (island.isPresent() && !IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().blockPlace, "blockPlace")) {
                    PaperLib.teleportAsync(player, LocationUtils.getSafeLocation(spawn, island.orElse(null)));
                    player.setHealth(0.5);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (IridiumSkyblock.getInstance().getConfiguration().debugFakePlayers) {
            System.out.print("EntityDamage - " + event.getDamager().getUniqueId() + "-" + event.getDamager().getName() + "\n");
        }
        if (IridiumSkyblock.getInstance().getConfiguration().fakePlayers.contains(event.getDamager().getUniqueId())) {
            return;
        }
        Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getEntity().getLocation());
        if (!island.isPresent()) return;
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), IridiumSkyblock.getInstance().getUserManager().getUser(player), IridiumSkyblock.getInstance().getPermissions().killMobs, "killMobs")) {
                return;
            }

            event.setCancelled(true);
            player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotHurtMobs.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
        }

        if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Player player = (Player) projectile.getShooter();
                if (IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), IridiumSkyblock.getInstance().getUserManager().getUser(player), IridiumSkyblock.getInstance().getPermissions().killMobs, "killMobs")) {
                    return;
                }

                event.setCancelled(true);
                player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotHurtMobs.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
            }
        }
    }
}
