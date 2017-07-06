package com.elderrealm.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;

public class GuildHelp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;
		
		/* Guild Help */
				
				player.sendMessage(C.gray + "                           [" + C.red + "ElderRealm Guilds" + C.gray + "] ");
				player.sendMessage(C.gold+C.bold + "-------------------------------------------");
				
				player.sendMessage(C.daqua + "/GuildCreate" + C.gray + " - " +  "Create a guild " + C.gold+ " (5,000 Coins)");
				
				player.sendMessage(C.daqua + "/GuildDisband" + C.gray + " - " +  "Disbands your Guild"  + C.gold+ " (1,000 Coins)");
				
				player.sendMessage(C.daqua + "/GuildSetbase" + C.gray + " - " +  "Set the Guilds base");
				
				player.sendMessage(C.daqua + "/GuildBase" + C.gray + " - " +  "Teleport to the Guilds base if it is set");
				
				player.sendMessage(C.daqua + "/GuildLeave" + C.gray + " - " +  "Leave the Guild you're in");

				player.sendMessage(C.daqua + "/GuildInfo" + C.gray + " - " +  "Show information about your Guild");
				
				player.sendMessage(C.daqua + "/GuildInvite" + C.gray + " - " +  "Invite a player to your Guild");
				
				player.sendMessage(C.daqua + "/GuildKick" + C.gray + " - " +  "Kick a player from your Guild");
				
				player.sendMessage(C.daqua + "/GuildDeposit" + C.gray + " - " +  "Deposit Coins into your Guilds bank");
				
				player.sendMessage(C.daqua + "/GuildWithdraw" + C.gray + " - " +  "Withdraw Coins from your Guilds bank");


				player.sendMessage(C.gold+C.bold + "-------------------------------------------");
		return true;
	}
}

