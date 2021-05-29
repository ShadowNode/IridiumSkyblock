package com.iridium.iridiumskyblock.database;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumskyblock.Color;
import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.IslandRank;
import com.iridium.iridiumskyblock.configs.Schematics;
import com.iridium.iridiumskyblock.managers.IslandManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents an Island of IridiumSkyblock.
 */
@Getter
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "islands")
public final class Island {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    @Setter(AccessLevel.PRIVATE)
    private int id;

    @DatabaseField(columnName = "name", canBeNull = false, unique = true)
    private @NotNull String name;

    /*
    The islands home relative to the island center as a string.
    Format: x,y,z,pitch,yaw
    */
    @DatabaseField(columnName = "home")
    private @NotNull String home;

    @DatabaseField(columnName = "visit")
    private boolean visitable;

    @DatabaseField(columnName = "create_time")
    private long time;

    @DatabaseField(columnName = "experience")
    private int experience;

    @DatabaseField(columnName = "color", canBeNull = false)
    private @NotNull Color color;

    /**
     * The default constructor.
     *
     * @param name The name of this island
     */
    public Island(@NotNull String name, @NotNull Schematics.SchematicConfig schematicConfig) {
        this.name = name;
        this.visitable = IridiumSkyblock.getInstance().getConfiguration().defaultIslandPublic;
        this.home = schematicConfig.xHome + "," + schematicConfig.yHome + "," + schematicConfig.zHome + ",0,0";
        this.time = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.color = Color.BLUE;
    }

    /**
     * Used for comparing
     */
    public Island(int id) {
        this.id = id;
    }

    /**
     * Gets the island's level.
     * TODO: Change the equation
     *
     * @return The islands level
     */
    public int getLevel() {
        return (int) Math.abs(Math.cbrt(experience + 1));
    }

    /**
     * Returns the minimum experience required to reach this level
     * The inverse of getLevel
     *
     * @param level The level
     * @return The experience required to reach this level
     */
    private int getExperienceRequired(int level) {
        return -1 + (level * level * level);
    }

    /**
     * Gets the players current experience (resets to 0 each levelup)
     *
     * @return Gets the players current experience (resets to 0 each levelup)
     */
    public int getExperience() {
        return getTotalExperience() - getExperienceRequired(getLevel());
    }

    /**
     * Gets the players total experience
     *
     * @return The players total experience
     */
    public int getTotalExperience() {
        return experience;
    }

    /**
     * Gets the required experience required to levelup
     *
     * @return the required experience required to levelup
     */
    public int getExperienceRequiredToLevelUp() {
        return getExperienceRequired(getLevel() + 1);
    }

    /**
     * Gets the remaining experience required to levelup
     *
     * @return the remaining experience required to levelup
     */
    public int getExperienceRemainingToLevelUp() {
        return getExperience() - getExperienceRequiredToLevelUp();
    }

    /**
     * Gets a list of Island members as Users.
     *
     * @return A list of all Users belonging to the island
     */
    public List<User> getMembers() {
        return IridiumSkyblock.getInstance().getIslandManager().getIslandMembers(this);
    }

    /**
     * Gets the Islands owner.
     *
     * @return The owner of the Island
     */
    public User getOwner() {
        return IridiumSkyblock.getInstance().getIslandManager().getIslandMembers(this).stream().filter(user ->
                user.getIslandRank().equals(IslandRank.OWNER)
        ).findFirst().orElse(new User(UUID.randomUUID(), IridiumSkyblock.getInstance().getMessages().none));
    }

    /**
     * The Location of the home of this island.
     *
     * @return The home location
     */
    public @NotNull Location getHome() {
        String[] params = home.split(",");
        World world = IridiumSkyblock.getInstance().getIslandManager().getWorld();
        return new Location(world, Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]), Float.parseFloat(params[4]), Float.parseFloat(params[3])).add(getCenter(world));
    }

    /**
     * Alters the spawn Location of this island.
     *
     * @param location The new home Location
     */
    public void setHome(@NotNull Location location) {
        Location homeLocation = location.subtract(getCenter(location.getWorld()));
        this.home = homeLocation.getX() + "," + homeLocation.getY() + "," + homeLocation.getZ() + "," + homeLocation.getPitch() + "," + homeLocation.getYaw();
    }

    /**
     * The date this island was created.
     *
     * @return A LocalDateTime of this island was created
     */
    public LocalDateTime getCreateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(getTime()), ZoneId.systemDefault());
    }

    /**
     * Gets the Islands current size.
     * Must be lower than the distance between Islands.
     */
    public int getSize() {
        int sizeLevel = IridiumSkyblock.getInstance().getIslandManager().getIslandUpgrade(this, "size").getLevel();
        return IridiumSkyblock.getInstance().getUpgrades().sizeUpgrade.upgrades.get(sizeLevel).size;
    }

    public void setColor(@NotNull Color color) {
        this.color = color;
        IridiumSkyblock.getInstance().getIslandManager().sendIslandBorder(this);
    }

    /**
     * Finds the center of this Island.
     * Function based of: https://stackoverflow.com/a/19287714.
     *
     * @param world The world where this island is in
     * @return The center Location of this island
     */
    public Location getCenter(World world) {
        //As per SN prefrences convert this to a per Region island type
        if (id == 1) return new Location(world, 256, 0, 256);
        int n = id - 2;
        int r = (int) (Math.floor((Math.sqrt(n + 1) - 1) / 2) + 1);
        int p = (8 * r * (r - 1)) / 2;
        int en = r * 2;
        int a = (1 + n - p) % (r * 8);

        Location location;

        switch (a / (r * 2)) {
            case 0:
                location = new Location(world, (((a - r) << 5) << 4) + 256, 0, ((-r << 5) << 4) + 256) ;
                break;
            case 1:
                location = new Location(world, ((r << 5) << 4) + 256, 0, ((((a % en) - r) << 5) << 4) + 256);
                break;
            case 2:
                location = new Location(world, (((r - (a % en)) << 5) << 4) + 256, 0, ((r << 5) << 4) + 256);
                break;
            case 3:
                location = new Location(world, ((-r << 5) << 4) + 256, 0, (((r - (a % en)) << 5) << 4) + 256);
                break;
            default:
                throw new IllegalStateException("Could not find island location with ID:");
        }
        return location;
    }

    /**
     * Returns the first corner point Location of this Island.
     * Is smaller than {@link Island#getPos2(World)}.
     *
     * @param world The world where this island is in
     * @return The Location of the first corner point
     */
    public Location getPos1(World world) {
        double size = getSize() / 2.00;
        return getCenter(world).subtract(new Location(world, size, 0, size));
    }

    /**
     * Returns the second corner point Location of this Island.
     * Is greater than {@link Island#getPos1(World)}.
     *
     * @param world The world where this island is in
     * @return The Location of the second corner point
     */
    public Location getPos2(World world) {
        double size = getSize() / 2.00;
        return getCenter(world).add(new Location(world, size, 0, size));
    }

    /**
     * Returns if a location is inside this Island or not.
     *
     * @param location The location we are testing
     * @return if the location is inside the island
     */
    public boolean isInIsland(@NotNull Location location) {
        IslandManager islandManager = IridiumSkyblock.getInstance().getIslandManager();
        World world = location.getWorld();
        if (Objects.equals(world, islandManager.getWorld())) {
            return isInIsland(location.getBlockX(), location.getBlockZ());
        } else {
            return false;
        }
    }

    /**
     * Returns if the provided x and z coordinates are inside this Island or not.
     *
     * @param x The x coordinates
     * @param z The z coordinates
     * @return Whether or not the coordinates are in this island
     */
    public boolean isInIsland(int x, int z) {
        Location pos1 = getPos1(null);
        Location pos2 = getPos2(null);

        return pos1.getX() <= x && pos1.getZ() <= z && pos2.getX() >= x && pos2.getZ() >= z;
    }

}