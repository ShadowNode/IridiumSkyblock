package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import com.iridium.iridiumskyblock.utils.StringUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;
import java.util.Optional;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (IridiumSkyblock.getInstance().getConfiguration().fakePlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (IridiumSkyblock.getInstance().getConfiguration().debug) {
            System.out.print("BlockPlace - " + event.getPlayer().getUniqueId() + "-" + event.getPlayer().getName() + "\n");
        }
        Player player = event.getPlayer();
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
        Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(event.getBlock().getLocation());
        if (!island.isPresent()) {
            World world = event.getBlock().getLocation().getWorld();
            if (Objects.equals(world, IridiumSkyblock.getInstance().getIslandManager().getWorld())) {
                event.setCancelled(true);
            }
            return;
        }

        if (!IridiumSkyblock.getInstance().getIslandManager().getIslandPermission(island.get(), user, IridiumSkyblock.getInstance().getPermissions().blockPlace, "blockPlace")) {
            event.setCancelled(true);
            player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().cannotPlaceBlocks.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix)));
        }
    }
}
