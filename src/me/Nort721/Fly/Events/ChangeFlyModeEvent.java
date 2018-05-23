package me.Nort721.Fly.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChangeFlyModeEvent extends Event implements Cancellable{
	
    private Player p;
    private boolean Cancel = false;
   
    public ChangeFlyModeEvent(Player p) {
            this.p = p;
    }
    
    public Player getPlayer() { return p; }
    public void setCancelled(boolean cancel) { this.Cancel = cancel; }
    public boolean isCancelled() { return this.Cancel; }
   
    private static final HandlerList handlers = new HandlerList();
     
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
