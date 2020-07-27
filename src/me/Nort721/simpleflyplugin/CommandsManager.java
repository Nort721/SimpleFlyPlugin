package me.nort721.simpleflyplugin;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsManager implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            SimpleFlyPlugin.getInstance().sendConsoleMessage("You must be a player to use this command");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            if (!p.hasPermission("player.bypass.disabledworlds") && ConfigUtils.getStringListFromConfig("Disable_worlds").contains(p.getWorld().getName())) {
                p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix") + ConfigUtils.getMessageFromConfig("Messages settings.disabled world message"));
                return true;
            }
            if (!p.hasPermission("cmd.fly.use")) {
                p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                        + ConfigUtils.getMessageFromConfig("Messages settings.no permissions message").replaceAll("%player%", p.getName()));
                return true;
            }
            if (SimpleFlyPlugin.getInstance().isUseEconomy() && ConfigUtils.getBooleanFromConfig("take money for using fly command") && !p.hasPermission("cmd.flyfree.use")) {

                ChangeFlyModeEvent event = new ChangeFlyModeEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) return true;

                EconomyResponse response = SimpleFlyPlugin.getInstance().getEcon().withdrawPlayer(p.getName(), ConfigUtils.getIntegerFromConfig("Economy settings.price for using fly command"));
                if (response.transactionSuccess()) {
                    boolean flying = !p.getAllowFlight();
                    p.setAllowFlight(flying);
                    p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                            + (flying ? ConfigUtils.getMessageFromConfig("Messages settings.enable fly message").replaceAll("%player%", p.getName())
                            : ConfigUtils.getMessageFromConfig("Messages settings.disable fly message").replaceAll("%player%", p.getName())));
                    SoundUtils.playSmallBlockNotePling(p);
                } else {
                    p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                            + ConfigUtils.getMessageFromConfig("Messages settings.no money message").replaceAll("%player%", p.getName()));
                    SoundUtils.playSmallItemBreak(p);
                }
            } else {

                ChangeFlyModeEvent event = new ChangeFlyModeEvent(p);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) return true;

                boolean flying = !p.getAllowFlight();
                p.setAllowFlight(flying);
                p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                        + (flying ? ConfigUtils.getMessageFromConfig("Messages settings.enable fly message").replaceAll("%player%", p.getName())
                        : ConfigUtils.getMessageFromConfig("Messages settings.disable fly message").replaceAll("%player%", p.getName())));
                SoundUtils.playSmallBlockNotePling(p);
            }
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!p.hasPermission("cmd.fly.reload")) {
                    p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                            + ConfigUtils.getMessageFromConfig("Messages settings.no permissions message").replaceAll("%player%", p.getName()));
                    return true;
                }
                SimpleFlyPlugin.getInstance().reloadConfig();
                p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix") + ConfigUtils.getMessageFromConfig("Messages settings.reload config successfully"));
                SoundUtils.playUIButtonClick(p);
            } else {
                if (!p.hasPermission("cmd.flyother.use")) {
                    p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                            + ConfigUtils.getMessageFromConfig("Messages settings.no permissions message").replaceAll("%player%", p.getName()));
                    return true;
                }

                Player otherPlayer = Bukkit.getPlayer(args[0]);
                if (otherPlayer == null) {
                    p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName()) + ChatColor.RED + "can't find " + args[0]);
                    return true;
                }

                ChangeFlyModeEvent event = new ChangeFlyModeEvent(otherPlayer);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) return true;

                boolean flying = !otherPlayer.getAllowFlight();
                otherPlayer.setAllowFlight(flying);
                otherPlayer.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                        + (flying ? ConfigUtils.getMessageFromConfig("Messages settings.enable fly message other (player message)").replaceAll("%player%", p.getName())
                        : ConfigUtils.getMessageFromConfig("Messages settings.disable fly message other (player message)").replaceAll("%player%", p.getName())));

                otherPlayer.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                        + (flying ? ConfigUtils.getMessageFromConfig("Messages settings.enable fly message other (admin message)").replaceAll("%player%", otherPlayer.getName())
                        : ConfigUtils.getMessageFromConfig("Messages settings.disable fly message other (admin message)").replaceAll("%player%", otherPlayer.getName())));

                SoundUtils.playBlockNotePling(p);
                SoundUtils.playSmallBlockNotePling(otherPlayer);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("all")) {
                if (!p.hasPermission("cmd.flyall.use")) {
                    p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                            + ConfigUtils.getMessageFromConfig("Messages settings.no permissions message").replaceAll("%player%", p.getName()));
                    return true;
                }
                if (!args[1].equalsIgnoreCase("enable") && !args[1].equalsIgnoreCase("disable")) return true;

                boolean toggle = args[1].equalsIgnoreCase("enable");
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (p.getUniqueId() != player.getUniqueId()) {

                        ChangeFlyModeEvent event = new ChangeFlyModeEvent(p);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {

                            player.setAllowFlight(toggle);
                            player.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                                    + (player.getAllowFlight() ? ConfigUtils.getMessageFromConfig("Messages settings.enable fly message other (player message)")
                                    .replaceAll("%player%", p.getName()) : ConfigUtils.getMessageFromConfig("Messages settings.disable fly message other (player message)").replaceAll("%player%", p.getName())));
                            SoundUtils.playSmallBlockNotePling(player);
                        }
                    }
                });
                p.sendMessage(ConfigUtils.getMessageFromConfig("Messages settings.prefix").replaceAll("%player%", p.getName())
                        + (toggle ? ConfigUtils.getMessageFromConfig("Messages settings.fly all enable message").replaceAll("%player%", p.getName())
                        : ConfigUtils.getMessageFromConfig("Messages settings.fly all disable message").replaceAll("%player%", p.getName())));

                SoundUtils.playAnvilSound(p);
            }
        }
        return true;
    }
}
