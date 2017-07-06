package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;

public class GuildDeny implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildDeny(ElderRealmGuilds pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;
		if (!(args.length == 0)) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildAccept");
			return false;
		}
		if (!(plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null)) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are already in a Guild");
			return false;
		}
		if (plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildInvite") == null) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You have no pending Guild invites");
			return false;
		}
		Object guildName = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildInvite");
		Object guildOwnerUUID = (plugin.getConfig().get("Guild-Data." + guildName + ".GuildOwner"));
		String guildOfferFrom = (String) plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildInviteFrom");

		Player guildOfferFromPlayer = null;
		if (!(Bukkit.getPlayer(guildOfferFrom) == null)) {
			guildOfferFromPlayer = Bukkit.getPlayer(guildOfferFrom);
		}

		Bukkit.getPlayer(guildOwnerUUID.toString());

		player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
				+ "You have declined the Guild invite from " + C.daqua + guildName);

		if (!(guildOfferFromPlayer == null)) {

			guildOfferFromPlayer.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.yellow
					+ player.getName() + C.gray + " has declined your Guild invite offer");

		}

		// * Making player details */
		plugin.getConfig().set("Player-Data." + player.getUniqueId().toString() + ".GuildInvite", null);
		plugin.saveConfig();
		return true;

	}
}
