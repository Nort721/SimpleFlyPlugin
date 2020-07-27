package me.nort721.simpleflyplugin;

import org.bukkit.ChatColor;

import java.util.List;

public class ConfigUtils {

    public static String getStringFromConfig(String path) {
        return SimpleFlyPlugin.getInstance().getConfig().getString(path, "");
    }

    public static String getMessageFromConfig(String path) {
        return ChatColor.translateAlternateColorCodes('&', SimpleFlyPlugin.getInstance().getConfig().getString(path, ""));
    }

    public static int getIntegerFromConfig(String path) {
        return SimpleFlyPlugin.getInstance().getConfig().getInt(path, 0);
    }

    public static boolean getBooleanFromConfig(String path) {
        return SimpleFlyPlugin.getInstance().getConfig().getBoolean(path, false);
    }

    public static List<String> getStringListFromConfig(String path) {
        return SimpleFlyPlugin.getInstance().getConfig().getStringList(path);
    }
}
