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
import com.elderrealm.main.utils.Permissions;

public class GuildInformation implements CommandExecutor {
	
	private ElderRealmGuilds plugin;

	public GuildInformation(ElderRealmGuilds pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;
		
        /* If target player is in a guild */
        if (plugin.getConfig().get("Player-Data." + player.getUniqueId() + ".InGuild") == null) {
            player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not in a Guild");
            return false;
        }
        Object guildName = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");

        String guildRank = C.gray + "Default";
        String guildSlots = "/5";
        if(plugin.getConfig().get("Guild-Data." + guildName + ".GuildLevel").equals(1)) {
        	guildRank = C.green + "VIP";
        	guildSlots = "/10";
        }
        
		String nameColor;
		if(player.hasPermission(Permissions.admin)){
			nameColor = "§c";
		}else if(player.hasPermission(Permissions.srMod)){
			nameColor = "§2";
		}else if(player.hasPermission(Permissions.moderator)){
			nameColor = "§d";
		}else if(player.hasPermission(Permissions.assistant)){
			nameColor = "§9";
		}else if(player.hasPermission(Permissions.emerald)){
			nameColor = "§a";
		}else if(player.hasPermission(Permissions.diamond)){
			nameColor = "§3";
		}else if(player.hasPermission(Permissions.gold)){
			nameColor = "§6";
		}else if(player.hasPermission(Permissions.iron)){
			nameColor = "§f";
		}else{
			nameColor = "§8";
		}	
        
        
		List<String> playersInGuild = new ArrayList<>();

		for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig()
					.get("Player-Data." + onlinePlayers.getUniqueId().toString() + ".GuildName") == guildName)
				playersInGuild.add(nameColor + onlinePlayers.getName().toString() + C.gray);
		}
   
        
		/* Guild Help */
        
        player.sendMessage("");
				
				player.sendMessage(C.gray + "[" + C.red + "ElderRealm Guilds" + C.gray + "] ");
				
				player.sendMessage(C.gray + "Guild Name: " + C.gold + guildName);
								
				player.sendMessage(C.gray + "Guild Bank: " + C.gold + plugin.getConfig().get("Guild-Data." + guildName + ".GuildBank") + " Coins");
				
				player.sendMessage(C.gray + "Guild Members: " +  C.gold + plugin.getConfig().get("Guild-Data." + guildName + ".AmountOfMembers") + guildSlots);
				
				player.sendMessage(C.gray + "Guild Created: " + C.gold + plugin.getConfig().get("Guild-Data." + guildName + ".DateCreated"));

				player.sendMessage(C.gray + "Online Members: " + nameColor + playersInGuild.toString().replace("[", "").replace("]", "").trim());
				
				player.sendMessage(C.gray + "Guild Rank: " + guildRank);
				
				player.sendMessage("");
		return true;
	}
}

