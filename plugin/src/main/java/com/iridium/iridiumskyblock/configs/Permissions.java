package com.iridium.iridiumskyblock.configs;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumskyblock.IslandRank;
import com.iridium.iridiumskyblock.Item;
import com.iridium.iridiumskyblock.Permission;

import java.util.Arrays;

/**
 * The Island permission configuration used by IridiumSkyblock (permissions.yml).
 * Is deserialized automatically on plugin startup and reload.
 */
public class Permissions {

    public String allowed = "&a&lALLOWED";
    public String denied = "&c&lDENIED";
    public Permission blockBreak = new Permission(new Item(XMaterial.DIAMOND_PICKAXE, 10, 1, "&bBreak Blocks", Arrays.asList("&7Grant the ability to break any blocks on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission blockPlace = new Permission(new Item(XMaterial.GRASS_BLOCK, 11, 1, "&bPlace Blocks", Arrays.asList("&7Grant the ability to place any blocks on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission bucket = new Permission(new Item(XMaterial.BUCKET, 12, 1, "&bUse Buckets", Arrays.asList("&7Grant the ability to fill and empty buckets on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission doors = new Permission(new Item(XMaterial.OAK_DOOR, 13, 1, "&bUse Doors", Arrays.asList("&7Grant the ability to use doors or trapdoors on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission redstone = new Permission(new Item(XMaterial.REDSTONE, 14, 1, "&bUse Redstone", Arrays.asList("&7Grant the ability to use buttons, levels, or pressure plates on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission spawners = new Permission(new Item(XMaterial.SPAWNER, 15, 1, "&bBreak Spawners", Arrays.asList("&7Grant the ability to mine spawners on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission openContainers = new Permission(new Item(XMaterial.CHEST, 16, 1, "&bOpen Containers", Arrays.asList("&7Grant the ability to open containers on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission killMobs = new Permission(new Item(XMaterial.DIAMOND_SWORD, 19, 1, "&bKill Mobs", Arrays.asList("&7Grant the ability to kill mobs on your Island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission changePermissions = new Permission(new Item(XMaterial.SUNFLOWER, 20, 1, "&bChange Permissions", Arrays.asList("&7Grant the ability to edit Island permissions.", "", "&b&lPermission", "%permission%")), IslandRank.CO_OWNER);
    public Permission kick = new Permission(new Item(XMaterial.IRON_BOOTS, 21, 1, "&bKick Users", Arrays.asList("&7Grant the ability to kick Island members.", "", "&b&lPermission", "%permission%")), IslandRank.MODERATOR);
    public Permission invite = new Permission(new Item(XMaterial.DIAMOND, 22, 1, "&bInvite Users", Arrays.asList("&7Grant the ability to invite Island members.", "", "&b&lPermission", "%permission%")), IslandRank.MODERATOR);
    public Permission regen = new Permission(new Item(XMaterial.TNT, 23, 1, "&bRegenerate Island", Arrays.asList("&7Grant the ability to regenerate your Island.", "", "&b&lPermission", "%permission%")), IslandRank.CO_OWNER);
    public Permission promote = new Permission(new Item(XMaterial.PLAYER_HEAD, 24, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmFiYWQ5MjRiMjIzNzJiYzk2NmE2ZDUxN2QyZjFiOGI1N2ZkZDI2MmI0ZTA0ZjQ4MzUyZTY4M2ZmZjkyIn19fQ==", 1, "&bPromote Users", Arrays.asList("&7Grant the ability to promote users in your island.", "", "&b&lPermission", "%permission%")), IslandRank.CO_OWNER);
    public Permission demote = new Permission(new Item(XMaterial.PLAYER_HEAD, 25, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU5YWU3YTRiZTY1ZmNiYWVlNjUxODEzODlhMmY3ZDQ3ZTJlMzI2ZGI1OWVhM2ViNzg5YTkyYzg1ZWE0NiJ9fX0=", 1, "&bDemote Users", Arrays.asList("&7Grant the ability to demote users in your island.", "", "&b&lPermission", "%permission%")), IslandRank.CO_OWNER);
    public Permission pickupItems = new Permission(new Item(XMaterial.HOPPER, 28, 1, "&bPickup Items", Arrays.asList("&7Grant the ability to pickup items on your island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission dropItems = new Permission(new Item(XMaterial.GHAST_TEAR, 29, 1, "&bDrop Items", Arrays.asList("&7Grant the ability to drop items on your island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission interactEntities = new Permission(new Item(XMaterial.CREEPER_HEAD, 30, 1, "&bInteract with Entities", Arrays.asList("&7Grant the ability to interact with entities on your island.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission trust = new Permission(new Item(XMaterial.EMERALD, 31, 1, "&bTrust Users", Arrays.asList("&7Grant the ability to manage Island Trusts.", "", "&b&lPermission", "%permission%")), IslandRank.MODERATOR);
    public Permission border = new Permission(new Item(XMaterial.BEACON, 32, 1, "&bIsland Border", Arrays.asList("&7Grant the ability to Change the Island border.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission spawnPortal = new Permission(new Item(XMaterial.OBSIDIAN, 33, 1, "&bSpawn Portal", Arrays.asList("&7Grant the ability to make a nether portal.", "", "&b&lPermission", "%permission%")), IslandRank.TRUSTED);
    public Permission changeBiomes = new Permission(new Item(XMaterial.COMPASS, 34, 1, "&bChange Biomes", Arrays.asList("&7Grant the ability to change the island biomes.", "", "&b&lPermission", "%permission%")), IslandRank.CO_OWNER);
}
