package com.iridium.iridiumskyblock.configs;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumskyblock.generators.GeneratorType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public List<UUID> fakePlayers = Arrays.asList(
            //Mob Masher
            UUID.fromString("b18836e2-b89d-3cde-a2b0-b130b0af3bdb"),
            //Industrial Forgoing
            UUID.fromString("ec5b5875-ebb5-4b47-833b-0de37ac9e6d7"),
            //clickmachine
            UUID.fromString("36f373ac-29ef-4150-b664-e7e6006efcd8"),
            //computercraft
            UUID.fromString("0d0c4ca0-4ff1-11e4-916c-0800200c9a66"),
            //Minecraft
            UUID.fromString("41c82c87-7afb-4024-ba57-13d2c99cae77"),
            //null profiles
            UUID.fromString("00000000-0000-0000-0000-000000000000"));
    public boolean debugFakePlayers = false;
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
