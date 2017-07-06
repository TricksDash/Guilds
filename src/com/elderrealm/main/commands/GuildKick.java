package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;
import com.elderrealm.main.utils.Permissions;

public class GuildKick implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildKick(ElderRealmGuilds pl) {
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
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildKick (Player)");
			return false;
		}
		
		if(plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not in a Guild");
			return false;
		}
		
		if(Bukkit.getPlayer(args[0]) == null) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildKick (Player)");
			return false;
		}
		
		if(Bukkit.getPlayer(args[0]).equals(player)) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You cannot kick yourself");
			return false;
		}
		
        Object guildName = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
        Object guildOwnerUUID =  plugin.getConfig().get("Guild-Data." + guildName + ".GuildOwner");
        
        if(!(guildOwnerUUID.toString().equals(player.getUniqueId().toString()))) {
        	player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not the Guild's owner");
        	return false;
        }
		
		Player target = Bukkit.getPlayer(args[0]);
		
		Object playersGuild = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
		Object targetsGuild = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
		
		
		if(playersGuild == targetsGuild) {

		
		
		if(playersGuild == targetsGuild) {
			
			plugin.getConfig().set("Guild-Data." + guildName + ".AmountOfMembers", 
					plugin.getConfig().getInt("Guild-Data." + guildName + ".AmountOfMembers", 0) - 1);
			plugin.getConfig().set("Player-Data." + target.getUniqueId().toString() + ".InGuild", null);
			plugin.getConfig().set("Player-Data." + target.getUniqueId().toString() + ".GuildName", null);
			plugin.saveConfig();
			
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.yellow + target.getName() + C.gray + " has been kicked from your Guild");
			target.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You have been kicked from the Guild");
		
		}else
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "That player is not in your Guild");
		}
		return true;

	}
}
