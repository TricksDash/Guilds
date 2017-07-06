package com.elderrealm.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;

import net.milkbowl.vault.economy.Economy;

public class GuildWithdraw implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildWithdraw(ElderRealmGuilds pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;
		Economy eco = ElderRealmGuilds.eco;

		/* Args check */
		if (args.length == 0 || args.length > 1) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildWithdraw (Amount)");
			return false;
		}

		if (plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not in a Guild");
			return false;
		}

		Object guildName = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
		Object guildOwnerUUID = plugin.getConfig().get("Guild-Data." + guildName + ".GuildOwner");

		if (!(guildOwnerUUID.toString().equals(player.getUniqueId().toString()))) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not the Guild's owner");
			return false;
		}

		try {
			Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildWithdraw (Amount)");
			return false;
		}

		if (plugin.getConfig().getInt("Guild-Data." + guildName + ".GuildBank") < Integer.parseInt(args[0])) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
					+ "Your Guild does not have that many Coins");
			return false;
		}

		char firstChar = args[0].charAt(0);

		if (firstChar == '-') {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildWithdraw (Amount)");
			return false;
		}

		plugin.getConfig().set("Guild-Data." + guildName + ".GuildBank",
				plugin.getConfig().getInt("Guild-Data." + guildName + ".GuildBank", 0) - Integer.parseInt(args[0]));
		plugin.saveConfig();

		player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You just withdew " + C.daqua
				+ Integer.parseInt(args[0]) + C.gray + " to your Guilds bank");
		eco.depositPlayer(player, Integer.parseInt(args[0]));

		return true;

	}
}
