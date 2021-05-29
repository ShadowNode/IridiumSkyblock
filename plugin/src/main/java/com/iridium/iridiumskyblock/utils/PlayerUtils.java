package com.iridium.iridiumskyblock.utils;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.IslandBank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Various utils for working with {@link Player}'s.
 */
public class PlayerUtils {

    public static boolean pay(@NotNull Player player, @NotNull Island island, double money) {

        if (IridiumSkyblock.getInstance().getEconomy().getBalance(player) >= money) {
            IridiumSkyblock.getInstance().getEconomy().withdrawPlayer(player, money);
            return true;
        }
        return false;
    }

    /**
     * Sends an island's border to a player.
     *
     * @param player The specified Player
     * @param island The specified Island
     */
    public static void sendBorder(@NotNull Player player, @NotNull Island island) {
        Bukkit.getScheduler().runTask(IridiumSkyblock.getInstance(), () -> IridiumSkyblock.getInstance().getNms().sendWorldBorder(player, island.getColor(), island.getSize() + 1, island.getCenter(player.getWorld())));
    }

    /**
     * Teleports the specified player to spawn.
     *
     * @param player The player we are teleporting
     */

    public static void teleportSpawn(Player player) {
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
    }

}
