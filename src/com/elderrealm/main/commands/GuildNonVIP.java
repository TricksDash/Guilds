package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;
import com.elderrealm.main.utils.Permissions;

public class GuildNonVIP implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildNonVIP(ElderRealmGuilds pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}

		if (!(sender instanceof ConsoleCommandSender || sender.hasPermission(Permissions.admin))) {
			sender.sendMessage("�7[�cPermissions�7]: �7This is an �cAdmin �7command");
			return false;
		} else {
			
			if(args[0].equals(null)) {
				return false;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			Object guildName = plugin.getConfig().get("Player-Data." + target.getUniqueId().toString() + ".GuildName");
			
			target.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Your Guild has been downgraded to Default");

			/* Making guild details */
			plugin.getConfig().set("Guild-Data." + guildName + ".GuildLevel", 0);
			plugin.saveConfig();

		}
		return true;

	}
}
