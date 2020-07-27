package me.nort721.simpleflyplugin;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event will launch just before the plugin changes players allow fly field
 */
public class ChangeFlyModeEvent extends Event implements Cancellable {
	
    @Getter private Player player;
    @Getter @Setter private boolean cancelled = false;
   
    public ChangeFlyModeEvent(Player player) {
            this.player = player;
    }
   
    private static final HandlerList handlers = new HandlerList();
     
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
