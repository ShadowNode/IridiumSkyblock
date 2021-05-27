package com.iridium.iridiumskyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;

public class PotionBrewListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorPotionBrew(BrewEvent event) {
        /*
        Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getBlock().getLocation());

        for (int i = 0; i < 3; i++) {
            ItemStack itemStack = event.getContents().getItem(i);
            if (itemStack != null && itemStack.getItemMeta() instanceof PotionMeta) {
                PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                island.ifPresent(value -> IridiumSkyblock.getInstance().getIslandManager().incrementMission(value, "BREW:" + potionMeta.getBasePotionData().getType().name() + ":" + (potionMeta.getBasePotionData().isUpgraded() ? 1 : 2), 1));
            }
        }
         */
    }

}
