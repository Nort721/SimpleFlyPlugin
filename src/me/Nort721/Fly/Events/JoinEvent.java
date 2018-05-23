package me.Nort721.Fly.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Nort721.Fly.SimpleFlyPlugin;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (SimpleFlyPlugin.joinaction.equalsIgnoreCase("enabled")) {
			Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
			if (p.hasPermission("cmd.fly.use"))
				p.setAllowFlight(true);
		} else {
			if (SimpleFlyPlugin.joinaction.equalsIgnoreCase("disabled")) {
				Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
				p.setAllowFlight(false);
			}
		}
	}
}
