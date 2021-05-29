package com.iridium.iridiumskyblock;

import lombok.Getter;

import java.util.Arrays;

/**
 * Represents a rank of a {@link com.iridium.iridiumskyblock.database.User} on an {@link com.iridium.iridiumskyblock.database.Island}.
 */
@Getter
public enum IslandRank {

    OWNER(5),
    CO_OWNER(4),
    MODERATOR(3),
    MEMBER(2),
    TRUSTED( 1),
    VISITOR(0);

    /**
     * The level of the rank, used to see which ranks are above and below others
     */
    private final int level;

    /**
     * The default constructor.
     * The higher the level, the more permissions this rank has.
     *
     * @param level The index of this rank
     */
    IslandRank(int level) {
        this.level = level;
    }

    /**
     * Gets an IslandRank by its level
     *
     * @param level The level of the Island Rank
     * @return The Island Rank
     */
    public static IslandRank getByLevel(int level) {
        return Arrays.stream(values())
                .filter(rankLevel -> rankLevel.level == level)
                .findAny()
                .orElse(null);
    }

}
