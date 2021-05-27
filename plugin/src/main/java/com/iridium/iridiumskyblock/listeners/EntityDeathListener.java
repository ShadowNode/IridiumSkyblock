package com.iridium.iridiumskyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorEntityDeath(EntityDeathEvent event) {
        /*
        Player player = event.getEntity().getKiller();
        if (player == null) return;

        User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
        Optional<Island> island = user.getIsland();
        island.ifPresent(value -> {
            IridiumSkyblock.getInstance().getIslandManager().incrementMission(value, "KILL:" + event.getEntityType().name(), 1);
            IslandBooster islandBooster = IridiumSkyblock.getInstance().getIslandManager().getIslandBooster(island.get(), "experience");
            if (islandBooster.isActive()) {
                event.setDroppedExp(event.getDroppedExp() * 2);
            }
        });
        */
    }

}
