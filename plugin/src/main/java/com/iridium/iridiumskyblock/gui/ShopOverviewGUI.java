package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.shop.ShopCategory;
import com.iridium.iridiumskyblock.utils.InventoryUtils;
import com.iridium.iridiumskyblock.utils.StringUtils;
import java.util.List;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ShopOverviewGUI implements GUI {

    /**
     * Called when there is a click in this GUI. Cancelled automatically.
     *
     * @param event The InventoryClickEvent provided by Bukkit
     */
    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getItemMeta() == null) {
            return;
        }

        String itemName = currentItem.getItemMeta().getDisplayName();
        Optional<ShopCategory> shopCategory = IridiumSkyblock.getInstance().getShopManager().getCategoryByFormattedName(itemName);

        if (!shopCategory.isPresent()) {
            return;
        }

        event.getWhoClicked().openInventory(new ShopCategoryGUI(shopCategory.get()).getInventory());
    }

    /**
     * Called when updating the Inventories contents
     */
    @Override
    public void addContent(Inventory inventory) {
        inventory.clear();

        InventoryUtils.fillInventory(inventory, IridiumSkyblock.getInstance().getShop().overviewBackground);

        for (ShopCategory category : IridiumSkyblock.getInstance().getShopManager().getCategories()) {
            ItemStack itemStack = category.overviewItem.parseItem();

            ItemMeta itemMeta = itemStack.getItemMeta();
            if (!category.itemLore.isEmpty()) {
                itemMeta.setLore(category.itemLore);
            }
            itemMeta.setDisplayName(category.formattedName);
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(category.slot, itemStack);
        }
    }

    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(
            this,
            IridiumSkyblock.getInstance().getShop().overviewSize,
            StringUtils.color(IridiumSkyblock.getInstance().getShop().overviewTitle)
        );

        addContent(inventory);

        return inventory;
    }

}
