package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;
import com.elderrealm.main.utils.Permissions;

public class GuildInvite implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildInvite(ElderRealmGuilds pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;

		/* Permission Check */
		if (player.hasPermission(Permissions.player)) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.red
					+ "To have Access to Guilds, purchase a rank at \n" + C.gold + "http://store.elderrealm.net");
			return false;
		}
		/* Args check */
		if (args.length == 0 || args.length > 1) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildInvite (Player)");
			return false;
		}

		Player target = Bukkit.getPlayer(args[0]);

		/* If target player is in a guild */
		if (plugin.getConfig().get("Player-Data." + player.getUniqueId() + ".InGuild") == null) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not in a Guild");
			return false;
		}
		/* If target target is in a guild */
		if (!(plugin.getConfig().get("Player-Data." + target.getUniqueId() + ".InGuild") == null)) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.yellow + target.getName() + C.gray
					+ " is already in a Guild");
			return false;
		}
		String guildName = (String) plugin.getConfig()
				.get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
		String guildOwnerUUID = (String) plugin.getConfig().get("Guild-Data." + guildName + ".GuildOwner");

		/* Only allow guild owners to invite players */
		if (!(guildOwnerUUID.toString().equals(player.getUniqueId().toString()))) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not the Guild's owner");
			return false;
		}
		
        int guildSlots = 5;
        if(plugin.getConfig().get("Guild-Data." + guildName + ".GuildLevel").equals(1)) {
        	guildSlots = 10;
        }
		
		int amountOfMembers = plugin.getConfig().getInt("Guild-Data." + guildName + ".AmountOfMembers");
		if (amountOfMembers == guildSlots) {
				player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
						+ "You have reached the maximum amount of Members for your Guild");
				return false;
			}

			/* If player has a pending invite */
			if (plugin.getConfig().get("Player-Data." + target.getUniqueId() + ".GuildInvite") == guildName) {
				player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
						+ "You have already invited " + C.yellow + target.getName());
				return false;
			}

			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.yellow + target.getName() + C.gray
					+ " has been invited to your Guild");

			target.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.yellow + player.getName() + C.gray
					+ " has invited you to join " + C.daqua + guildName + C.gray
					+ " use §6/GuildAccept§7 to accept or §6/GuildDeny §7to decline their offer");

			// * Making player details */
			plugin.getConfig().set("Player-Data." + target.getUniqueId().toString() + ".GuildInvite", guildName);
			plugin.getConfig().set("Player-Data." + target.getUniqueId().toString() + ".GuildInviteFrom",
					player.getName());

		return true;

	}
}
