package com.elderrealm.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;

public class GuildLeave implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildLeave(ElderRealmGuilds pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;
		
		/* Args check */
		if (args.length > 0) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildLeave");
			return false;
		}
		
		if(plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not in a Guild");
			return false;
		}
		
        Object guildName = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
        Object guildOwnerUUID =  plugin.getConfig().get("Guild-Data." + guildName + ".GuildOwner");
        
        if(guildOwnerUUID.toString().equals(player.getUniqueId().toString())) {
        	player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are the Guilds Owner, use /GuildDisband");
        	return false;
        }
			
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You have left the Guild");
		
			plugin.getConfig().set("Guild-Data." + guildName + ".AmountOfMembers", 
					plugin.getConfig().getInt("Guild-Data." + guildName + ".AmountOfMembers", 0) - 1);
			
			plugin.getConfig().set("Player-Data." + player.getUniqueId().toString() + ".InGuild", null);
			plugin.getConfig().set("Player-Data." + player.getUniqueId().toString() + ".GuildName", null);
			plugin.saveConfig();
		return true;

	}
}
