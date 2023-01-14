package me.nort721.simpleflyplugin;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SimpleFlyPlugin extends JavaPlugin {

    static String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "").replace(".", "");
    private String consolePrefix;
    private boolean useEconomy;
    private Economy econ = null;

    static SimpleFlyPlugin getInstance() {
        return SimpleFlyPlugin.getPlugin(SimpleFlyPlugin.class);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        consolePrefix = ChatColor.RED + "[" + getDescription().getName() + "] " + ChatColor.RESET;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);

        getCommand("fly").setExecutor(new CommandsManager());

        if (ConfigUtils.getBooleanFromConfig("Economy settings.use economy")) {
            if (!setupEconomy()) {
                sendConsoleMessage("no economy plugin or vault was found, economy disabled");
                useEconomy = false;
            } else
                useEconomy = true;
        }

        runParticlesTimer();
        sendConsoleMessage("has been enabled");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        sendConsoleMessage("has been disabled");
    }

    void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(consolePrefix + message);
    }

    private boolean setupEconomy() {
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

    private void playParticle(Player player) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_14)) {
            player.spigot().playEffect(player.getLocation().add(0.0, -0.6, 0.0), Effect.valueOf("CLOUD"), 0, 0, 0.0f, 0.0f, 0.0f, 0.1f, 14, 1);
        }
    }

    private void runParticlesTimer() {
        if (!ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play particle effect while flying")) return;
        Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("player.bypass.disabledworlds") || !ConfigUtils.getStringListFromConfig("Disable_worlds").contains(player.getWorld().getName())) {
                        if (player.isFlying() && player.hasPermission("fly.effect.use"))
                            playParticle(player);
                    }
                }
            }
        }, 10, 2);
    }
}
