package com.iridium.iridiumskyblock.configs;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumskyblock.Item;
import com.iridium.iridiumskyblock.Upgrade;
import com.iridium.iridiumskyblock.upgrades.SizeUpgrade;

import java.util.Arrays;

public class Upgrades {
    public Upgrade<SizeUpgrade> sizeUpgrade = new Upgrade<>(true,
            new Item(XMaterial.GRASS_BLOCK, 13, 1, "&b&lIsland Size", Arrays.asList(
                    "&7Need more room to expand? Buy this",
                    "&7upgrade to increase your island size.",
                    "",
                    "&b&lInformation:",
                    "&b&l * &7Current Level: &b%level%",
                    "&b&l * &7Current Size: &b%size%x%size% Blocks",
                    "&b&l * &7Upgrade Cost: $%vaultcost%",
                    "&b&lLevels:",
                    "&b&l * &7Level 1: &b128x128 Blocks",
                    "&b&l * &7Level 2: &b160x160 Blocks",
                    "&b&l * &7Level 3: &b192x192 Blocks",
                    "",
                    "&b&l[!] &bLeft Click to Purchase this Upgrade"
            )), ImmutableMap.<Integer, SizeUpgrade>builder()
            .put(1, new SizeUpgrade(1000, 128))
            .put(2, new SizeUpgrade(2000, 160))
            .put(3, new SizeUpgrade(3000, 192))
            .build());
}
