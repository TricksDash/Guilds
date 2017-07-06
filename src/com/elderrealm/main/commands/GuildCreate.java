package com.elderrealm.main.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;
import com.elderrealm.main.utils.ChatFilterWords;
import com.elderrealm.main.utils.Permissions;

import net.milkbowl.vault.economy.Economy;

public class GuildCreate implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildCreate(ElderRealmGuilds pl) {
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

		if (!(plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null)) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are already in a guild");
			return false;
		}

		if (args.length == 0 || args.length > 1) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildCreate (Guild Name)");
			return false;
		} else {
			String guildName = args[0].toUpperCase();

			if (plugin.getConfig().contains("Guild-Data." + guildName)) {
				player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Guild name is taken");
				return false;
			}
			if (args[0].length() >= 10) {
				player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Guild name is to long");
				return false;
			}
			for (String b : ChatFilterWords.blockedWords) {
				if (guildName.toLowerCase().contains(b)) {
					player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
							+ "Guild name cannot contain profanity");
					return false;
				}
			}
			
			if(!guildName.matches("[a-zA-Z_]*")){
				player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
						+ "Guild name can only contain letters in the English Alphabet (A-Z)");
				return false;
			}
			if (eco.getBalance(player) < 5000) {
				player.sendMessage(
						C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You do not have enough coins");
				return false;
			}

			eco.withdrawPlayer(player, 5000);

			/* Making guild details */
			plugin.getConfig().set("Guild-Data." + guildName + ".GuildOwner", player.getUniqueId().toString());
			plugin.getConfig().createSection("Guild-Data." + guildName + "." + ".GuildMembers");
			plugin.getConfig().set("Guild-Data." + guildName + ".AmountOfMembers", 1);
			plugin.getConfig().set("Guild-Data." + guildName + ".DateCreated",
					new SimpleDateFormat("dd/MM/yy").format(new Date()));
			plugin.getConfig().set("Guild-Data." + guildName + ".GuildBank", 0);
			plugin.getConfig().set("Guild-Data." + guildName + ".GuildLevel", 0);
			plugin.saveConfig();

			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.daqua + guildName + C.gray
					+ " guild has been created");
			player.sendMessage(C.gold + "- 5,000 Coins");

			Bukkit.broadcastMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.yellow + player.getName()
					+ C.gray + " has just created the guild " + C.daqua + guildName);

			// * Making player details */
			plugin.getConfig().set("Player-Data." + player.getUniqueId().toString() + ".InGuild", true);
			plugin.getConfig().set("Player-Data." + player.getUniqueId().toString() + ".GuildName", guildName);
			plugin.saveConfig();

		}
		return true;

	}
}
