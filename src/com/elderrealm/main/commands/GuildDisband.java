package com.elderrealm.main.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;

import net.milkbowl.vault.economy.Economy;

public class GuildDisband implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildDisband(ElderRealmGuilds pl) {
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
		if (args.length > 0) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildLeave");
			return false;
		}

		if (plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not in a Guild");
			return false;
		}

		Object guildName = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
		Object guildOwnerUUID = plugin.getConfig().get("Guild-Data." + guildName + ".GuildOwner");

		if (!(guildOwnerUUID.toString().equals(player.getUniqueId().toString()))) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
					+ "Only Guild Owners can disband the Guild");
			return false;
		}

		if (eco.getBalance(player) < 1000) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You do not have enough coins");
			return false;
		}
		
		if (plugin.getConfig().getInt("Guild-Data." + guildName + ".GuildBank") > 0) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Withdraw the Coins from your Guild before Disbanding!");
			return false;
		}

		List<String> playersInGuild = new ArrayList<>();

		for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig()
					.get("Player-Data." + onlinePlayers.getUniqueId().toString() + ".GuildName") == guildName)
				playersInGuild.add(onlinePlayers.getUniqueId().toString());
		}

		if (!(plugin.getConfig().get("Guild-Data." + guildName + ".AmountOfMembers").equals(playersInGuild.size()))) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Not all Guild members are online");
			return false;
		} else

			eco.withdrawPlayer(player, 1000);
			for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
				if (plugin.getConfig()
						.get("Player-Data." + onlinePlayers.getUniqueId().toString() + ".GuildName") == guildName) {

					plugin.getConfig().set("Player-Data." + onlinePlayers.getUniqueId().toString() + ".InGuild", null);
					plugin.getConfig().set("Player-Data." + onlinePlayers.getUniqueId().toString() + ".GuildName",
							null);
					plugin.getConfig().set("Guild-Data." + guildName, null);
					plugin.saveConfig();

					onlinePlayers.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
							+ "Your Guild has been Disbanded");
				}
			}
		return true;

	}
}
