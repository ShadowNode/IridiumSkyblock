package com.iridium.iridiumskyblock.upgrades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iridium.iridiumskyblock.utils.Placeholder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UpgradeData {
    public int money;

    @JsonIgnore
    public List<Placeholder> getPlaceholders() {
        return Collections.emptyList();
    }
}
