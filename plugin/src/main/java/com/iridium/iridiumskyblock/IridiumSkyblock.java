package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.commands.CommandManager;
import com.iridium.iridiumskyblock.configs.*;
import com.iridium.iridiumskyblock.gui.GUI;
import com.iridium.iridiumskyblock.listeners.*;
import com.iridium.iridiumskyblock.managers.DatabaseManager;
import com.iridium.iridiumskyblock.managers.IslandManager;
import com.iridium.iridiumskyblock.managers.SchematicManager;
import com.iridium.iridiumskyblock.managers.UserManager;
import com.iridium.iridiumskyblock.multiversion.MultiVersion;
import com.iridium.iridiumskyblock.nms.NMS;
import com.iridium.iridiumskyblock.placeholders.ClipPlaceholderAPI;
import com.iridium.iridiumskyblock.placeholders.MVDWPlaceholderAPI;
import com.iridium.iridiumskyblock.utils.PlayerUtils;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * The main class of this plugin which handles initialization
 * and shutdown of the plugin.
 */
@Getter
public class IridiumSkyblock extends JavaPlugin {

    private static IridiumSkyblock instance;

    private Persist persist;
    private NMS nms;
    private MultiVersion multiversion;

    private CommandManager commandManager;
    private DatabaseManager databaseManager;
    private IslandManager islandManager;
    private UserManager userManager;
    private SchematicManager schematicManager;

    private Configuration configuration;
    private Messages messages;
    private SQL sql;
    private Schematics schematics;
    private Inventories inventories;
    private Permissions permissions;
    private Upgrades upgrades;
    private Commands commands;

    private ChunkGenerator chunkGenerator;

    private HashMap<String, Permission> permissionList;
    private HashMap<String, Upgrade> upgradesList;

    private Economy economy;

    /**
     * The default constructor.
     */
    public IridiumSkyblock() {
        instance = this;
    }

    /**
     * Returns the plugin's instance of this class.
     *
     * @return Instance of this class
     */
    public static IridiumSkyblock getInstance() {
        return instance;
    }

    /**
     * Code that should be executed before this plugin gets enabled.
     * Initializes the configurations and sets the default world generator.
     */
    @Override
    public void onLoad() {
        // Create the data folder in order to make Jackson work
        getDataFolder().mkdir();

        // Initialize the configs
        this.persist = new Persist(Persist.PersistType.YAML, this);
        loadConfigs();
        saveConfigs();

        // Initialize the ChunkGenerator
        this.chunkGenerator = configuration.generatorSettings.generatorType.getChunkGenerator();
    }

    /**
     * Plugin startup logic.
     */
    @Override
    public void onEnable() {

        this.nms = setupNMS();
        if (this.nms == null) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.multiversion = setupMultiversion();

        if (!PaperLib.isSpigot()) {
            // isSpigot returns true if the server is using spigot or a fork
            getLogger().warning("CraftBukkit isnt supported, please use spigot or one of its forks");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize the commands
        this.commandManager = new CommandManager("iridiumskyblock");

        // Initialize the manager classes (bad) and create the world
        this.islandManager = new IslandManager();
        this.userManager = new UserManager();
        this.islandManager.createWorld(World.Environment.NORMAL, configuration.worldName);
        //this.islandManager.createWorld(World.Environment.NETHER, configuration.worldName + "_nether");
        //this.islandManager.createWorld(World.Environment.THE_END, configuration.worldName + "_the_end");

        this.databaseManager = new DatabaseManager();
        // Try to connect to the database
        try {
            databaseManager.init();
        } catch (SQLException exception) {
            // We don't want the plugin to start if the connection fails
            exception.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize the API
        IridiumSkyblockAPI.initializeAPI(this);

        this.schematicManager = new SchematicManager(new File(getDataFolder(), "schematics"));

        // Save data regularly
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, this::saveData, 0, 20 * 60 * 5);

        registerListeners();

        // Initialize Vault economy support
        this.economy = setupEconomy();

        // Register Placeholders Support
        Plugin MVDWPlaceholderAPI = getServer().getPluginManager().getPlugin("MVdWPlaceholderAPI");
        if (MVDWPlaceholderAPI != null && MVDWPlaceholderAPI.isEnabled()) {
            new MVDWPlaceholderAPI();
            getLogger().info("Successfully registered placeholders with MVDWPlaceholderAPI.");
        }

        Plugin PlaceholderAPI = getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (PlaceholderAPI != null && PlaceholderAPI.isEnabled()) {
            if (new ClipPlaceholderAPI().register()) {
                getLogger().info("Successfully registered placeholders with PlaceholderAPI.");
            }
        }

        // Send island border to all players
        Bukkit.getOnlinePlayers().forEach(player -> getIslandManager().getIslandViaLocation(player.getLocation()).ifPresent(island -> PlayerUtils.sendBorder(player, island)));


        // Automatically update all inventories
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            InventoryHolder inventoryHolder = player.getOpenInventory().getTopInventory().getHolder();
            if (inventoryHolder instanceof GUI) {
                ((GUI) inventoryHolder).addContent(player.getOpenInventory().getTopInventory());
            }
        }), 0, 20);

        new Metrics(this, 5825);

        getLogger().info("----------------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Enabled!");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info("");
        getLogger().info("----------------------------------------");

    }

    /**
     * Sets the {@link ChunkGenerator} for the Skyblock worlds.
     */
    @Nullable
    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return this.chunkGenerator;
    }

    /**
     * Plugin shutdown logic.
     */
    @Override
    public void onDisable() {
        saveData();
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Disabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
    }

    /**
     * Registers the plugin's listeners.
     */
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BucketListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityPickupItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPistonListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockExplodeListener(), this);
        //Bukkit.getPluginManager().registerEvents(new PlayerPortalListener(), this);
    }

    /**
     * Saves islands, users and other data to the database.
     */
    public void saveData() {
        getDatabaseManager().getUserTableManager().save();
        getDatabaseManager().getIslandTableManager().save();
        getDatabaseManager().getIslandInviteTableManager().save();
        getDatabaseManager().getIslandPermissionTableManager().save();
        getDatabaseManager().getIslandBlocksTableManager().save();
        getDatabaseManager().getIslandSpawnersTableManager().save();
        getDatabaseManager().getIslandBankTableManager().save();
        getDatabaseManager().getIslandMissionTableManager().save();
        getDatabaseManager().getIslandRewardTableManager().save();
        getDatabaseManager().getIslandUpgradeTableManager().save();
        getDatabaseManager().getIslandTrustedTableManager().save();
        getDatabaseManager().getIslandBoosterTableManager().save();
        getDatabaseManager().getIslandWarpTableManager().save();
        getDatabaseManager().getIslandLogTableManager().save();
    }

    /**
     * Tries to initialize the Vault support.
     *
     * @return Vault's economy interface, null if none is found
     */
    private Economy setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            getLogger().warning("You do not have an economy plugin installed (like Essentials)");
            return null;
        }
        return economyProvider.getProvider();
    }

    /**
     * Automatically gets the correct NMS version from minecraft version
     *
     * @return The correct NMS Version
     */
    private NMS setupNMS() {
        String version = Bukkit.getServer().getClass().getPackage().getName().toUpperCase().split("\\.")[3];
        try {
            return (NMS) Class.forName("com.iridium.iridiumskyblock.nms." + version).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            getLogger().warning("Un-Supported Minecraft Version: " + version);
        }
        return null;
    }

    /**
     * Automatically gets the correct Multiversion version from minecraft version
     *
     * @return The correct Multiversion Version
     */
    private MultiVersion setupMultiversion() {
        String version = Bukkit.getServer().getClass().getPackage().getName().toUpperCase().split("\\.")[3];
        try {
            return (MultiVersion) Class.forName("com.iridium.iridiumskyblock.multiversion." + version).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            getLogger().warning("Un-Supported Minecraft Version: " + version);
        }
        return null;
    }

    /**
     * Loads the configurations required for this plugin.
     *
     * @see Persist
     */
    public void loadConfigs() {
        this.configuration = persist.load(Configuration.class);
        this.messages = persist.load(Messages.class);
        this.sql = persist.load(SQL.class);
        this.schematics = persist.load(Schematics.class);
        this.inventories = persist.load(Inventories.class);
        this.permissions = persist.load(Permissions.class);
        this.upgrades = persist.load(Upgrades.class);
        this.commands = persist.load(Commands.class);

        this.permissionList = new HashMap<>();
        this.permissionList.put("redstone", permissions.redstone);
        this.permissionList.put("blockPlace", permissions.blockPlace);
        this.permissionList.put("blockBreak", permissions.blockBreak);
        this.permissionList.put("bucket", permissions.bucket);
        this.permissionList.put("doors", permissions.doors);
        this.permissionList.put("killMobs", permissions.killMobs);
        this.permissionList.put("openContainers", permissions.openContainers);
        this.permissionList.put("spawners", permissions.spawners);
        this.permissionList.put("changePermissions", permissions.changePermissions);
        this.permissionList.put("kick", permissions.kick);
        this.permissionList.put("invite", permissions.invite);
        this.permissionList.put("regen", permissions.regen);
        this.permissionList.put("promote", permissions.promote);
        this.permissionList.put("demote", permissions.demote);
        this.permissionList.put("pickupItems", permissions.pickupItems);
        this.permissionList.put("dropItems", permissions.dropItems);
        this.permissionList.put("interactEntities", permissions.interactEntities);
        this.permissionList.put("trust", permissions.trust);
        this.permissionList.put("border", permissions.border);
        this.permissionList.put("portal", permissions.spawnPortal);
        this.permissionList.put("biomes", permissions.changeBiomes);

        this.upgradesList = new HashMap<>();
        if (upgrades.sizeUpgrade.enabled) upgradesList.put("size", upgrades.sizeUpgrade);

        File schematicFolder = new File(getDataFolder(), "schematics");
        if (!schematicFolder.exists()) {
            schematicFolder.mkdir();
        }
        saveFile(schematicFolder, "island.iridiumschem");
    }

    private void saveFile(File parent, String name) {
        File file = new File(parent, name);
        if (!file.exists()) {
            if (getResource(name) != null) {
                InputStream in = this.getResource(name);
                if (in != null) {
                    try {
                        OutputStream out = new FileOutputStream(file);
                        byte[] buf = new byte[1024];

                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    } catch (IOException var10) {
                        getLogger().log(Level.SEVERE, "Could not save " + file.getName() + " to " + file, var10);
                    }

                }
            }
        }
    }


    /**
     * Saves changes to the configuration files.
     *
     * @see Persist
     */
    public void saveConfigs() {
        this.persist.save(configuration);
        this.persist.save(messages);
        this.persist.save(sql);
        this.persist.save(schematics);
        this.persist.save(inventories);
        this.persist.save(permissions);
        this.persist.save(upgrades);
        this.persist.save(commands);
    }

}
