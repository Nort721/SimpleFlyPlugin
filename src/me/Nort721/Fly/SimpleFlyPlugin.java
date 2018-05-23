package me.Nort721.Fly;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.Nort721.Fly.Events.ChangeFlyModeEvent;
import me.Nort721.Fly.Events.JoinEvent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class SimpleFlyPlugin extends JavaPlugin implements CommandExecutor {
	public static Economy econ = null;
	public static String prefix, enablefly, disablefly, enableflyother, disableflyother, enableflyotheradmin,
			disableflyotheradmin, flyallenable, flyalldisable, joinaction, nomoney;
	public static int price;
	public static boolean take, econo;

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		registerConfig();
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
		enablefly = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Enable fly message"));
		disablefly = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Disable fly message"));
		enableflyother = ChatColor.translateAlternateColorCodes('&',
				getConfig().getString("enable fly message other (player message)"));
		disableflyother = ChatColor.translateAlternateColorCodes('&',
				getConfig().getString("disable fly message other (player message)"));
		enableflyotheradmin = ChatColor.translateAlternateColorCodes('&',
				getConfig().getString("enable fly message other (admin message)"));
		disableflyotheradmin = ChatColor.translateAlternateColorCodes('&',
				getConfig().getString("disable fly message other (admin message)"));
		flyallenable = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Fly all enable message"));
		flyalldisable = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Fly all disable message"));
		joinaction = getConfig().getString("when player join fly is Enabled/Disabled/none");
		price = getConfig().getInt("price for using fly command");
		take = getConfig().getBoolean("take money for using fly command");
		nomoney = ChatColor.translateAlternateColorCodes('&', getConfig().getString("no money message"));
		econo = getConfig().getBoolean("use Economy");
		registerPermissions();
		if (econo) {
			if (!setupEconomy()) {
				getLogger().severe(String.format("[%s] - no economy plugin or vault was found, plugin disabled",
						getDescription().getName()));
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
		} else {
			take = false;
		}
		registerEvents();
		logger.info(pdfFile.getName() + " has been enabled!");
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled");
	}

	private void registerConfig() {
		saveDefaultConfig();
	}

	public boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new JoinEvent(), this);
	}

	private void registerPermissions() {
		Permission p = new Permission("cmd.fly.use");
		Permission p1 = new Permission("cmd.flyother.use");
		Permission p2 = new Permission("cmd.fly.reload");
		Permission p3 = new Permission("cmd.flyall.use");
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(p);
		pm.addPermission(p1);
		pm.addPermission(p2);
		pm.addPermission(p3);
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("you must be a player to use this command!");
			return false;
		}
		if (command.getName().equalsIgnoreCase("fly")) {
			Player p = (Player) sender;
			prefix = prefix.replaceAll("%player%", p.getName());
			if (args.length == 0) {
				if (p.hasPermission("cmd.fly.use")) {
					if (take) {
						EconomyResponse r = econ.withdrawPlayer(p.getName(), price);
						if (!r.transactionSuccess()) {
							nomoney = nomoney.replaceAll("%player%", p.getName());
							p.sendMessage(prefix + nomoney);
							return true;
						}
					}
					if (!p.getAllowFlight()) {
						Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
						p.setAllowFlight(true);
						enablefly = enablefly.replaceAll("%player%", p.getName());
						p.sendMessage(prefix + enablefly);
					} else {
						Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
						p.setAllowFlight(false);
						disablefly = disablefly.replaceAll("%player%", p.getName());
						p.sendMessage(prefix + disablefly);
					}
				}
			} else {
				if (!(args.length == 0)) {
					if (args[0].equalsIgnoreCase("reload")) {
						if (p.hasPermission("cmd.fly.reload"))
							registerConfig();
						p.sendMessage(prefix + ChatColor.GREEN + "config file has been reloaded!");
					} else {
						if (args[0].equalsIgnoreCase("all") && args.length >= 2) {
							if (p.hasPermission("cmd.flyall.use")) {
								if (args[1].equalsIgnoreCase("enable")) {
									for (Player pl : Bukkit.getOnlinePlayers()) {
										Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
										pl.setAllowFlight(true);
									}
									flyallenable = flyallenable.replaceAll("%player%", p.getName());
									p.sendMessage(prefix + flyallenable);
								} else {
									if (args[1].equalsIgnoreCase("disable")) {
										for (Player pl : Bukkit.getOnlinePlayers()) {
											Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
											pl.setAllowFlight(false);
										}
										flyalldisable = flyalldisable.replaceAll("%player%", p.getName());
										p.sendMessage(prefix + flyalldisable);
									} else {
										p.sendMessage(prefix + ChatColor.RED + "quick fix - /fly all enable/disable");
										return true;
									}
								}
							}
						} else {
							if (p.hasPermission("cmd.flyother.use")) {
								Player pp = Bukkit.getPlayer(args[0]);
								if (pp == null) {
									p.sendMessage(prefix + ChatColor.RED + "player must be online");
									return true;
								}
								if (pp.getAllowFlight()) {
									Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
									pp.setAllowFlight(false);
									disableflyother = disableflyother.replaceAll("%player%", p.getName());
									pp.sendMessage(prefix + disableflyother);
									disableflyotheradmin = disableflyotheradmin.replaceAll("%player%", p.getName());
									p.sendMessage(prefix + disableflyotheradmin);
								} else {
									Bukkit.getServer().getPluginManager().callEvent(new ChangeFlyModeEvent(p));
									pp.setAllowFlight(true);
									enableflyother = enableflyother.replaceAll("%player%", p.getName());
									pp.sendMessage(prefix + enableflyother);
									enableflyotheradmin = enableflyotheradmin.replaceAll("%player%", p.getName());
									p.sendMessage(prefix + enableflyotheradmin);
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
}
