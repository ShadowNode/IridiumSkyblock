package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.Optional;

public class EntityPickupItemListener implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (IridiumSkyblock.getInstance().getConfiguration().fakePlayers.contains(event.getEntity().getUniqueId())) {
            return;
        }
        if (IridiumSkyblock.getInstance().getConfiguration().debug) {
            System.out.print("EntityPickup - " + event.getEntity().getUniqueId() + "-" + event.getEntity().getName() + "\n");
        }
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getEntity();
        Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getItem().getLocation());
        if (!island.isPresent()) return;

        if (!IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), IridiumSkyblock.getInstance().getUserManager().getUser(player), IridiumSkyblock.getInstance().getPermissions().pickupItems, "pickupItems")) {
            event.setCancelled(true);
        }
    }

}
