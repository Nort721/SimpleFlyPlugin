package me.nort721.simpleflyplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PluginListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!p.hasPermission("player.bypass.disabledworlds") && ConfigUtils.getStringListFromConfig("Disable_worlds").contains(p.getWorld().getName())) return;

		if (ConfigUtils.getStringFromConfig("Other settings.when player join fly is enabled/disabled/none").equalsIgnoreCase("enabled")) {

			ChangeFlyModeEvent event = new ChangeFlyModeEvent(p);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (event.isCancelled()) return;

			if (p.hasPermission("cmd.fly.use")) {
				p.setAllowFlight(true);
				p.setFlying(true);
			}
		} else if (ConfigUtils.getStringFromConfig("Other settings.when player join fly is enabled/disabled/none").equalsIgnoreCase("disabled")) {

			ChangeFlyModeEvent event = new ChangeFlyModeEvent(p);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (event.isCancelled()) return;

			p.setAllowFlight(false);
		}
	}

	@EventHandler
	public void onFlyToggle(PlayerToggleFlightEvent e) {
		if (!e.getPlayer().hasPermission("player.bypass.disabledworlds") && ConfigUtils.getStringListFromConfig("Disable_worlds").contains(e.getPlayer().getWorld().getName())) return;

		if (ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when starting fly")) {
			if (e.isFlying())
				SoundUtils.playFireWorkSound(e.getPlayer());
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		if (!e.getPlayer().hasPermission("player.bypass.disabledworlds") && ConfigUtils.getStringListFromConfig("Disable_worlds").contains(e.getPlayer().getWorld().getName())) {
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
		}
	}
}
