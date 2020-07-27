package me.nort721.simpleflyplugin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playUIButtonClick(Player p) {
        if (!ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when using fly command")) return;
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9))
            p.playSound(p.getEyeLocation(), Sound.valueOf("CLICK"), 1.0F, 1.0F);
        else
            p.playSound(p.getEyeLocation(), Sound.valueOf("UI_BUTTON_CLICK"), 1.0F, 1.0F);
    }

    public static void playBlockNotePling(Player p) {
        if (!ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when using fly command")) return;
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9))
            p.playSound(p.getEyeLocation(), Sound.valueOf("NOTE_PLING"), 1.0F, 1.0F);
        else
        if (ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_11) && ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_13))
            p.playSound(p.getEyeLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 1.0F, 1.0F);
        else
        if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_13))
            p.playSound(p.getEyeLocation(), Sound.valueOf("BLOCK_NOTE_BLOCK_PLING"), 1.0F, 1.0F);
    }

    public static void playSmallBlockNotePling(Player p) {
        if (!ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when using fly command")) return;
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9))
            p.playSound(p.getEyeLocation(), Sound.valueOf("NOTE_PLING"), 1.0F, 10.0F);
        else
        if (ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_11) && ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_13))
            p.playSound(p.getEyeLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 1.0F, 10.0F);
        else
        if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_13))
            p.playSound(p.getEyeLocation(), Sound.valueOf("BLOCK_NOTE_BLOCK_PLING"), 1.0F, 10.0F);
    }

    public static void playSmallItemBreak(Player p) {
        if (!ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when using fly command")) return;
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9))
            p.playSound(p.getEyeLocation(), Sound.valueOf("ITEM_BREAK"), 1.0F, 10.0F);
        else
            p.playSound(p.getEyeLocation(), Sound.valueOf("ENTITY_ITEM_BREAK"), 1.0F, 10.0F);
    }

    public static void playAnvilSound(Player p) {
        if (!ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when using fly command")) return;
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9))
            p.playSound(p.getEyeLocation(), Sound.valueOf("ANVIL_LAND"), 1.0F, 10.0F);
        else
            p.playSound(p.getEyeLocation(), Sound.valueOf("BLOCK_ANVIL_LAND"), 1.0F, 10.0F);
    }

    public static void playFireWorkSound(Player p) {
        if (!ConfigUtils.getBooleanFromConfig("Sound & Particle effects.play sound when starting fly")) return;
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9)) {
            p.playSound(p.getEyeLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
        } else if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_9) && ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_13)) {
            p.playSound(p.getEyeLocation(), Sound.valueOf("ENTITY_FIREWORK_LAUNCH"), 1.0f, 1.0f);
        } else if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_13)) {
            p.playSound(p.getEyeLocation(), Sound.valueOf("ENTITY_FIREWORK_ROCKET_LAUNCH"), 1.0f, 1.0f);
        }
    }
}
