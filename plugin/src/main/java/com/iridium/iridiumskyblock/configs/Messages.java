package com.iridium.iridiumskyblock.configs;

import java.util.Arrays;
import java.util.List;

/**
 * The message configuration used by IridiumSkyblock (messages.yml).
 * Is deserialized automatically on plugin startup and reload.
 */
public class Messages {

    public String reloaded = "%prefix% &7Configuration has been reloaded.";
    public String noPermission = "%prefix% &7You don't have permission for that.";
    public String mustBeAPlayer = "%prefix% &7You must be a player to execute this command.";
    public String playerDoesntExist = "%prefix% &7That player doesnt exist.";
    public String unknownCommand = "%prefix% &7Unknown Command, Try /is help.";
    public String helpCommandHeader = "&8===== &b&lIridiumSkyblock Help &r&8=====";
    public String helpCommandMessage = "<GRADIENT:09C6F9>/is %command%</GRADIENT:045DE9>&r: &7%description%";
    public String helpCommandFooter = " &7Page %page% of %max_page% ";
    public String helpCommandPreviousPage = "&b<<";
    public String helpCommandNextPage = "&b>>";
    public String helpCommandNextPageHover = "&7Click to go to the next page.";
    public String helpCommandPreviousPageHover = "&7Click to go to the previous page.";
    public String creatingIsland = "%prefix% &7Creating Island...";
    public String regeneratingIsland = "%prefix% &7Regenerating Island...";
    public String alreadyHaveIsland = "%prefix% &7You already have an Island.";
    public String dontHaveIsland = "%prefix% &7You don't have an Island.";
    public String islandWithNameAlreadyExists = "%prefix% &7An Island with that name already exists.";
    public String islandSchematicNotFound = "%prefix% &7No schematic with that name exists.";
    public String islandDeleted = "%prefix% &7Your island has been deleted.";
    public String teleportingHome = "%prefix% &7Teleporting to your Island Home";
    public String teleportingOtherHome = "%prefix% &7Teleporting you to %player%'s Island Home";
    public String setHome = "%prefix% &7Your Island home has been set to this location.";
    public String isNotSafe = "%prefix% &7This location is not safe.";
    public String invitedPlayer = "%prefix% &7You have invited %player% to join your island.";
    public String userInvitedPlayer = "%prefix% &7%inviter% has invited %player% to join your island.";
    public String youHaveBeenInvited = "%prefix% &7%inviter% has invited you to join their island.";
    public String alreadyInYourIsland = "%prefix% &7This player is already a member of your Island.";
    public String inviteRevoked = "%prefix% &7Island invite for %player% has been revoked.";
    public String inviteDoesntExist = "%prefix% &7%player% has no active invite for your island.";
    public String alreadyInvited = "%prefix% &7You have already invited this user to your island.";
    public String playerJoinedYourIsland = "%prefix% &7%player% has joined your island!";
    public String noInvite = "%prefix% &7You have not been invited to this island.";
    public String userNoIsland = "%prefix% &7That user doesn't have an island.";
    public String cannotLeaveIsland = "%prefix% &7You cannot leave your island, do /is delete instead.";
    public String youHaveLeftIsland = "%prefix% &7You have left your island.";
    public String playerLeftIsland = "%prefix% &7%player% has left your island.";
    public String userNotInYourIsland = "%prefix% &7That user is not in your island.";
    public String youKickedPlayer = "%prefix% &7You have kicked %player%.";
    public String kickedPlayer = "%prefix% &7%kicker% has kicked %player%.";
    public String islandIsPrivate = "%prefix% &7That player's island is private.";
    public String islandNowPrivate = "%prefix% &7Your island is now private.";
    public String islandNowPublic = "%prefix% &7Your island is now public.";
    public String youHaveBeenKicked = "%prefix% &7You have been kicked by %player%.";
    public String cannotKickUser = "%prefix% &7You cannot kick this user.";
    public String cannotDeleteIsland = "%prefix% &7Only the Island owner can delete an island.";
    public String cannotPromoteUser = "%prefix% &7You cannot promote this user.";
    public String promotedPlayer = "%prefix% &7You have promoted %player% to %rank%.";
    public String userPromotedPlayer = "%prefix% &7%promoter% has promoted %player% to %rank%.";
    public String cannotDemoteUser = "%prefix% &7You cannot demote this user.";
    public String demotedPlayer = "%prefix% &7You have demoted %player% to %rank%.";
    public String userDemotedPlayer = "%prefix% &7%promoter% has demoted %player% to %rank%.";
    public String transferredOwnership = "%prefix% &7%oldowner% has transferred Island ownership to %newowner%.";
    public String cannotTransferYourself = "%prefix% &7You cannot transfer the island ownership to yourself.";
    public String cannotBreakBlocks = "%prefix% &7You cannot break blocks on this island.";
    public String cannotPlaceBlocks = "%prefix% &7You cannot place blocks on this island.";
    public String cannotUseBuckets = "%prefix% &7You cannot use buckets on this island.";
    public String cannotOpenDoors = "%prefix% &7You cannot open doors on this island.";
    public String cannotOpenContainers = "%prefix% &7You cannot open containers on this island.";
    public String cannotInteract = "%prefix% &7You cannot interact on this island.";
    public String cannotChangePermissions = "%prefix% &7You cannot change this permission.";
    public String cannotUseRedstone = "%prefix% &7You cannot use redstone on this island.";
    public String cannotUseSpawnPortal = "%prefix% &7You cannot spawn a portal on this island.";
    public String cannotSpawnPortal = "%prefix% &7You cannot afford to spawn a portal on this island. You need 17 obsidian in your inventory.";
    public String cannotHurtMobs = "%prefix% &7You cannot hurt mobs on this island.";
    public String cannotInviteMember = "%prefix% &7You cannot invite members to this island.";
    public String cannotRegenIsland = "%prefix% &7You cannot regenerate your island.";
    public String cannotInteractEntities = "%prefix% &7You cannot interact with entities on this island.";
    public String cannotCreatePortal = "%prefix% &7You cannot create this portal they are blocks in the way, clear a 4x1x5 area to the right where you are looking from.";
    public String cannotDropItems = "%prefix% &7You cannot drop items on this island.";
    public String cannotTransferOwnership = "%prefix% &7Only the Island owner can transfer ownership.";
    public String cannotManageBiome = "%prefix% &7You cannot change the biomes on this island.";
    public String nowBypassing = "%prefix% &7You are now bypassing island restrictions.";
    public String noLongerBypassing = "%prefix% &7You are no longer bypassing island restrictions.";
    public String notABiome = "%prefix% &7This biome cannot be found.";
    public String biomeChanged = "%prefix% &7Biome changed to %biome% please go to spawn and come back to update the biome on your client.";
    public String setSchematicPosition = "%prefix% &7The position has been set.";
    public String addedSchematic = "%prefix% &7Schematic has been added.";
    public String invalidPositionCommandSyntax = "%prefix% &7Please use /is position <1/2>.";
    public String invalidSaveSchematicCommandSyntax = "%prefix% &7Please use /is saveSchematic <Name>.";
    public String invalidSchematicPositions = "%prefix% &7You haven't set valid schematic positions with /is position.";
    public String missingSchematicConfirmation = "%prefix% &7A schematic by that name already exists, to overwrite it use /is save <name> confirm.";
    public String islandBorderChanged = "%prefix% &7%player% has changed your island border to %color%.";
    public String notAColor = "%prefix% &7That is not a valid color.";
    public String maxLevelReached = "%prefix% &7Maximum level reached.";
    public String cannotAfford = "%prefix% &7You cannot afford this.";
    public String unknownUpgrade = "%prefix% &7Unknown Island upgrade.";
    public String alreadyTrusted = "%prefix% &7This player is already trusted.";
    public String trustedPlayer = "%prefix% &7%truster% has trusted %player% to your island.";
    public String playerTrustedYou = "%prefix% &7%truster% has trusted you to their island.";
    public String trustRevoked = "%prefix% &7Island Trust for %player% has been revoked.";
    public String trustDoesntExist = "%prefix% &7%player% is not trusted in your island.";
    public String teleportingWarp = "%prefix% &7Teleporting to warp %name%.";
    public String cannotManageTrusts = "%prefix% &7You cannot manage Island Trusts.";
    public String cannotManageBorder = "%prefix% &7You cannot change the Island Border.";
    public String teleportCanceled = "%prefix% &7Teleportation Canceled, Please stand still.";
    public String unknownSchematic = "%prefix% &7Unknown Schematic.";
    public String onlySetHomeOnIsland = "%prefix% &7You can only set your Island Home on your island.";
    public String dataReset = "%prefix% &7All data has been reset.";
    public String yes = "Yes";
    public String no = "No";
    public String none = "None";

    public List<String> infoCommand = Arrays.asList(
            "&8===== &b&lIsland Info for %player% &r&8=====",
            "<SOLID:8342B5>Island Name&r: &7%island_name%",
            "<SOLID:8342B5>Island Owner&r: &7%owner%",
            "<SOLID:8342B5>Island Members&r: &7%members%",
            "<SOLID:8342B5>Island Trusted Members&r: &7%trustedMembers%",
            "<SOLID:8342B5>Island Visitable&r: &7%visitable%",
            "<SOLID:8342B5>Island Created&r: &7%island_creation%",
            "<SOLID:8342B5>Owner Last Login&r: &7%lastonline%"
    );
}
