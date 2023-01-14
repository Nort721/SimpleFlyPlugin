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
	public void onFlyToggle(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();

		if (!player.getPlayer().hasPermission("player.bypass.disabledworlds") && ConfigUtils.getStringListFromConfig("Disable_worlds").contains(player.getWorld().getName())) return;

		if (ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when starting fly")) {
			if (event.isFlying())
				SoundUtils.playFireWorkSound(player);
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPermission("player.bypass.disabledworlds") && ConfigUtils.getStringListFromConfig("Disable_worlds").contains(player.getWorld().getName())) {
			player.setAllowFlight(false);
			player.setFlying(false);
		}
	}
}
