package com.iridium.iridiumskyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class ItemCraftListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorItemCraft(CraftItemEvent event) {
        /*
        int amount = event.isShiftClick() ? Arrays.stream(event.getInventory().getMatrix())
                .filter(Objects::nonNull)
                .map(ItemStack::getAmount)
                .sorted()
                .findFirst()
                .orElse(1) * event.getRecipe().getResult().getAmount() : event.getRecipe().getResult().getAmount();

        Player player = (Player) event.getWhoClicked();
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
        Optional<Island> island = user.getIsland();
        XMaterial material = XMaterial.matchXMaterial(event.getRecipe().getResult().getType());

        island.ifPresent(value -> IridiumSkyblock.getInstance().getIslandManager().incrementMission(value, "CRAFT:" + material.name(), amount));
         */
    }

}
