package com.iridium.iridiumskyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorPlayerFish(PlayerFishEvent event) {
        /*
        Entity caughtEntity = event.getCaught();
        if (caughtEntity == null) return;

        User user = IridiumSkyblock.getInstance().getUserManager().getUser(event.getPlayer());
        Optional<Island> island = user.getIsland();
        island.ifPresent(value -> IridiumSkyblock.getInstance().getIslandManager().incrementMission(value, "FISH:" + caughtEntity.getType().name(), 1));
         */
    }

}
