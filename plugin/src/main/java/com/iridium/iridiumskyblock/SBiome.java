package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;

public enum SBiome {
    badlands(Biome.BADLANDS),
    bamboo_jungle(Biome.BAMBOO_JUNGLE),
    beach(Biome.BEACH),
    dark_forest(Biome.DARK_FOREST),
    deep_ocean(Biome.DEEP_OCEAN),
    deep_warm_ocean(Biome.DEEP_WARM_OCEAN),
    desert(Biome.DESERT),
    forest(Biome.FOREST),
    frozen_ocean(Biome.FROZEN_OCEAN),
    ice_spikes(Biome.ICE_SPIKES),
    jungle(Biome.JUNGLE),
    lukewarm_ocean(Biome.LUKEWARM_OCEAN),
    mountains(Biome.MOUNTAINS),
    mushroom_fields(Biome.MUSHROOM_FIELDS),
    ocean(Biome.OCEAN),
    plains(Biome.PLAINS),
    river(Biome.PLAINS),
    savanna(Biome.SAVANNA),
    snowy_mountains(Biome.SNOWY_MOUNTAINS),
    swamp(Biome.SWAMP),
    taiga(Biome.TAIGA),
    warm_ocean(Biome.WARM_OCEAN),
    wooded_hills(Biome.WOODED_HILLS),
    the_end(Biome.THE_END),
    the_nether(Biome.NETHER_WASTES);

    private Biome bukkitBiome;
    SBiome(Biome bukkitBiome) {
        this.bukkitBiome = bukkitBiome;
    }

    public Biome getBukkitBiome() {
        return bukkitBiome;
    }

    public static SBiome getBiome(String biome) {
        return Arrays.stream(SBiome.values()).filter(SBiome1 -> SBiome1.name().equalsIgnoreCase(biome)).findFirst().orElse(null);
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