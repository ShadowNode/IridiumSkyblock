package com.iridium.iridiumskyblock.configs;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumskyblock.generators.GeneratorType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * The main configuration of IridiumSkyblock (configuration.yml).
 * Is deserialized automatically on plugin startup and reload.
 */
public class Configuration {

    public String prefix = "<SOLID:555555>[<SOLID:8342B5>ShadowNode<SOLID:555555>]&r ";
    public String worldName = "IridiumSkyblock";
    public String islandCreateTitle = "&b&lIsland Created";
    public String islandCreateSubTitle = "";
    public String dateTimeFormat = "EEEE, MMMM dd HH:mm:ss";

    public boolean defaultIslandPublic = true;
    public boolean voidTeleport = true;

    public int schematicPastingDelay = 1;
    public int teleportDelay = 0;
    public String visualtool = "GLOWSTONE_DUST";

    public GeneratorSettings generatorSettings = new GeneratorSettings();

    /**
     * Settings for the {@link org.bukkit.generator.ChunkGenerator} of IridiumSkyblock.
     * Allows fine-tuning of the {@link com.iridium.iridiumskyblock.generators.OceanGenerator}.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GeneratorSettings {

        public GeneratorType generatorType = GeneratorType.SKYBLOCK;
        public int waterHeight = 60;
        public int minOceanFloorLevel = 10;
        public int maxOceanFloorLevel = 25;
        public XMaterial oceanFloorBottomMaterial = XMaterial.GRAVEL;
        public XMaterial oceanFloorTopMaterial = XMaterial.SAND;

    }

}
