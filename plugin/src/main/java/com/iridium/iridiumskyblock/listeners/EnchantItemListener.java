package com.iridium.iridiumskyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantItemListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorItemEnchant(EnchantItemEvent event) {
        /*
        Player player = event.getEnchanter();
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
        Optional<Island> island = user.getIsland();
        XMaterial material = XMaterial.matchXMaterial(event.getItem().getType());
        island.ifPresent(value -> IridiumSkyblock.getInstance().getIslandManager().incrementMission(value, "ENCHANT:" + material.name(), 1));
        */
    }
}
