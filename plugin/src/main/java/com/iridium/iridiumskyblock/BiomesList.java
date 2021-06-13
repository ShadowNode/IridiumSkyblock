package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;

public enum BiomesList {
    badlands,
    bamboo_jungle,
    beach,
    dark_forest,
    deep_ocean,
    deep_warm_ocean,
    desert,
    forest,
    frozen_ocean,
    ice_spikes,
    jungle,
    lukewarm_ocean,
    mountains,
    mushroom_fields,
    ocean,
    plains,
    river,
    savanna,
    snowy_mountains,
    swamp,
    taiga,
    warm_ocean,
    wooded_hills;

    public static BiomesList getBiomes(String biomes) {
        return Arrays.stream(BiomesList.values()).filter(biome1 -> biome1.name().equalsIgnoreCase(biomes)).findFirst().orElse(null);
    }

    public void replaceRegionBiomes(Island island, Player player, Biome biome) {
        int waterHeight = IridiumSkyblock.getInstance().getConfiguration().generatorSettings.waterHeight;
        World world = player.getWorld();

        Location pos1 = island.getPos1(world);
        Location pos2 = island.getPos2(world);

        for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
            for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                for (int y = waterHeight + 1; y <= 256; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setBiome(biome);
                }
            }
        }

        player.sendMessage(StringUtils.color(IridiumSkyblock.getInstance().getMessages().biomeChanged.replace("%prefix%", IridiumSkyblock.getInstance().getConfiguration().prefix).replace("%biome%", biome.name().toLowerCase().replace("_"," "))));
    }
}