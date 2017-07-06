package com.elderrealm.main.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmGuilds;

public class GuildMembersNoDamage implements Listener {

	private ElderRealmGuilds plugin;

	public GuildMembersNoDamage(ElderRealmGuilds pl) {
		plugin = pl;

	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Player) {
			Player target = (Player) event.getEntity();
			Player player = (Player) event.getDamager();

			Object playersGuild = plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".GuildName");
			Object targetsGuild = plugin.getConfig().get("Player-Data." + target.getUniqueId().toString() + ".GuildName");

			if(plugin.getConfig().get("Player-Data." + player.getUniqueId().toString() + ".InGuild") == null) {
				return;
			}
			
			if(plugin.getConfig().get("Player-Data." + target.getUniqueId().toString() + ".InGuild") == null) {
				return;
			}
			
			if(playersGuild == null) {
				return;
			}
			
			if(targetsGuild == null) {
				return;
			}
			
			if(playersGuild.equals(targetsGuild)) {
				player.sendMessage(C.gray + "[" + C.red + "Guilds" + C.gray + "]: " + C.gray + "You cannot attack "+ C.yellow + target.getName() + C.gray + " as you're in the same Guild");
				event.setCancelled(true);
			}else
				return;
		}
		return;
	}
}
