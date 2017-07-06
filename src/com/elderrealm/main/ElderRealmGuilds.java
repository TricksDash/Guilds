package com.elderrealm.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.elderrealm.main.commands.GuildAccept;
import com.elderrealm.main.commands.GuildBase;
import com.elderrealm.main.commands.GuildCreate;
import com.elderrealm.main.commands.GuildDeny;
import com.elderrealm.main.commands.GuildDeposit;
import com.elderrealm.main.commands.GuildDisband;
import com.elderrealm.main.commands.GuildHelp;
import com.elderrealm.main.commands.GuildInformation;
import com.elderrealm.main.commands.GuildInvite;
import com.elderrealm.main.commands.GuildKick;
import com.elderrealm.main.commands.GuildLeave;
import com.elderrealm.main.commands.GuildSetbase;
import com.elderrealm.main.commands.GuildVIP;
import com.elderrealm.main.commands.GuildWithdraw;
import com.elderrealm.main.events.GuildMembersNoDamage;
import com.elderrealm.main.utils.Permissions;

import net.milkbowl.vault.economy.Economy;

public class ElderRealmGuilds extends JavaPlugin {

	public static Economy eco = null;

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		
		new BukkitRunnable() {

			public void run() {

				for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
					Object guildName = getConfig()
							.get("Player-Data." + onlinePlayers.getUniqueId().toString() + ".GuildName");
					String guildTag = "";
					if (!(getConfig().get("Player-Data." + onlinePlayers.getUniqueId() + ".InGuild") == null)) {
						guildTag = C.gray + " [" + C.white + guildName + C.gray + "]";
						if (getConfig().get("Guild-Data." + guildName + ".GuildLevel").equals(1)) {
							guildTag = C.gray + " [" + C.green + guildName + C.gray + "]";
						}
					}
					

					if (onlinePlayers.hasPermission(Permissions.admin)) {
						onlinePlayers.setPlayerListName(C.yellow + "*" + C.red + onlinePlayers.getName() + guildTag);
						
					} else if (onlinePlayers.hasPermission(Permissions.srMod)) {
						onlinePlayers.setPlayerListName(C.yellow + "*" + C.dgreen + onlinePlayers.getName() + guildTag);

					} else if (onlinePlayers.hasPermission(Permissions.moderator)) {
						onlinePlayers.setPlayerListName(C.yellow + "*" + C.purple + onlinePlayers.getName() + guildTag);

					} else if (onlinePlayers.hasPermission(Permissions.assistant)) {
						onlinePlayers.setPlayerListName(C.yellow + "*" + C.blue + onlinePlayers.getName() + guildTag);

					} else if (onlinePlayers.hasPermission(Permissions.emerald)) {
						onlinePlayers.setPlayerListName(C.green + onlinePlayers.getName() + guildTag);

					} else if (onlinePlayers.hasPermission(Permissions.diamond)) {
						onlinePlayers.setPlayerListName(C.daqua + onlinePlayers.getName() + guildTag);

					} else if (onlinePlayers.hasPermission(Permissions.gold)) {
						onlinePlayers.setPlayerListName(C.gold + onlinePlayers.getName() + guildTag);

					} else if (onlinePlayers.hasPermission(Permissions.iron)) {
						onlinePlayers.setPlayerListName(C.white + onlinePlayers.getName() + guildTag);
					} else
						onlinePlayers.setPlayerListName(C.dgray + onlinePlayers.getName() + guildTag);
				}

			}
		}.runTaskTimer(this,20*5,20*5);

	registerCommands();

	registerConfig();

	registerEvents();

		logger.info(pdfFile.getName() + "Has been enabled - Version " + pdfFile.getVersion());
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + "Has been disabled - Version " + pdfFile.getVersion());
	}

	public void registerCommands() {
		getCommand("guildhelp").setExecutor(new GuildHelp());
		getCommand("guildcreate").setExecutor(new GuildCreate(this));
		getCommand("guilddisband").setExecutor(new GuildDisband(this));
		getCommand("guildinvite").setExecutor(new GuildInvite(this));
		getCommand("guildaccept").setExecutor(new GuildAccept(this));
		getCommand("guilddeny").setExecutor(new GuildDeny(this));
		getCommand("guildkick").setExecutor(new GuildKick(this));
		getCommand("guildleave").setExecutor(new GuildLeave(this));
		getCommand("guildinformation").setExecutor(new GuildInformation(this));
		getCommand("guilddeposit").setExecutor(new GuildDeposit(this));
		getCommand("guildwithdraw").setExecutor(new GuildWithdraw(this));
		getCommand("guildsetbase").setExecutor(new GuildSetbase(this));
		getCommand("guildbase").setExecutor(new GuildBase(this));
		getCommand("guildvip").setExecutor(new GuildVIP(this));

	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GuildMembersNoDamage(this), this);

	}

	public void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

}
