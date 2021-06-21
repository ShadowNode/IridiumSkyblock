package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import com.iridium.iridiumskyblock.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (IridiumSkyblock.getInstance().getConfiguration().fakePlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (IridiumSkyblock.getInstance().getConfiguration().debug) {
            System.out.print("PlayerMove - " + event.getPlayer().getUniqueId() + "-" + event.getPlayer().getName() + "\n");
        }
        Player player = event.getPlayer();
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(player);
        if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
            if (user.getTeleportingTask() != null) {
                user.getTeleportingTask().cancel();
                user.setTeleportingTask(null);
                player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().teleportCanceled
                        .replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix))
                );
            }
        }
        if (event.getTo().getY() < 0 & IridiumSkyblock.getInstance().getConfiguration().voidTeleport) {
            Optional<Island> island = IridiumSkyblock.getInstance().getIslandManager().getIslandViaLocation(player.getLocation());
            island.ifPresent(value -> IridiumSkyblock.getInstance().getIslandManager().teleportHome(player, value, 0));
        }
    }

}
