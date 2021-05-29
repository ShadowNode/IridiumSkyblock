package com.iridium.iridiumskyblock.configs;

import com.cryptomorin.xseries.XBiome;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumskyblock.Item;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * The schematic configuration used by IridiumSkyblock (schematics.yml).
 * Is deserialized automatically on plugin startup and reload.
 */
public class Schematics {

    public Map<String, SchematicConfig> schematics = ImmutableMap.<String, SchematicConfig>builder()
            .put("default", new SchematicConfig(new Item(XMaterial.PLAYER_HEAD, 13, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY0OTNkZDgwNjUzM2Q5ZDIwZTg0OTUzOTU0MzY1ZjRkMzY5NzA5Y2ViYzlkZGVmMDIyZDFmZDQwZDg2YTY4ZiJ9fX0=", 1, "&b&lDefault Island", Collections.singletonList("&7A starter island.")),
                    -3.5, 96, -5.5, new SchematicWorld(XBiome.PLAINS, "island.iridiumschem")))
            .build();

    @NoArgsConstructor
    public static class SchematicConfig {
        public Item item;
        public double xHome;
        public double yHome;
        public double zHome;
        public SchematicWorld overworld;

        public SchematicConfig(Item item, double xHome, double yHome, double zHome, SchematicWorld overworld) {
            this.item = item;
            this.xHome = xHome;
            this.yHome = yHome;
            this.zHome = zHome;
            this.overworld = overworld;
        }
    }

    @NoArgsConstructor
    public static class SchematicWorld {
        public XBiome biome;
        public String schematicID;

        public SchematicWorld(XBiome biome, String schematicID) {
            this.biome = biome;
            this.schematicID = schematicID;
        }
    }

}
