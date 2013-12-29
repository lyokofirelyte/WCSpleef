package com.github.lyokofirelyte.WCSpleef.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.github.lyokofirelyte.WCAPI.WCEvent;

public class GameStartEvent extends WCEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    public boolean cancelled = false;
    
    Player player;
    int round;
    
    public GameStartEvent(Player p, int r){
    	player = p;
    	round = r;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public boolean isCancelled(){
    	return cancelled;
    }

	public void setCancelled(boolean a) {
		cancelled = a;
	}
	
	public void setPlayer(Player a){
		player = a;
	}
	
	public void setRound(int a){
		round = a;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getRound(){
		return round;
	}
}
