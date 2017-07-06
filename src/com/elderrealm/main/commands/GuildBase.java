*package com.elderrealm.main.commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;
import com.elderrealm.main.utils.TimeUtil;

public class GuildBase implements CommandExecutor {

	private ElderRealmGuilds plugin;

	public GuildBase(ElderRealmGuilds pl) {
		plugin = pl;

	}
	
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;
		
		/* Args check */
		if (!(args.length == 0)) {
			player.sendMessage(
					C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Usage: /GuildBbase");
			return false;
		}
		
		if(plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You are not in a Guild");
			return false;
		}
		Object guildName = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
		
		if(plugin.getConfig().get("Guild-Data." + guildName + ".GuildBaseLocation") == null) {
			player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Guild Owner has not set a Base");
			return false;
		}
		
		int cooldownTime = 3600;

		if (cooldowns.containsKey(sender.getName())) {
			long secondsLeft = ((cooldowns.get(sender.getName()) / 1000) + cooldownTime)
					- (System.currentTimeMillis() / 1000);
			if(secondsLeft == 0) {
				cooldowns.remove(player.getName());
			}
			
			if (secondsLeft > 0) {
				String timeString = TimeUtil.formatIntoHHMMSS(secondsLeft);

				player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray
						+ "Base Teleport is on cooldown " + C.dgray + "[" + timeString + "]");
				return true;
			}
		}
		
		cooldowns.put(sender.getName(), System.currentTimeMillis());
		player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "Teleporting to Guild base");
        Location baseLoc = (Location) plugin.getConfig().get("Guild-Data." + guildName + ".GuildBaseLocation");
        player.teleport(baseLoc);
        
		return true;

	}
}
