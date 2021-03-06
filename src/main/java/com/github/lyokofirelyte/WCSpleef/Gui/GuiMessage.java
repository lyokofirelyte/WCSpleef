package com.github.lyokofirelyte.WCSpleef.Gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCSpleef.WCSpleef;

public class GuiMessage extends WCGui {
	
	private WCSpleef main;
	private WCGui parent;
	
	private Player p;
	private int seconds;
	
	public GuiMessage(WCSpleef main, String message, int seconds, Player p, WCGui parent){
		
		super(0, message);
		this.main = main;
		this.parent = parent;
		
		this.p = p;
		this.seconds = seconds;
		
	}
	
	@Override
	public void create(){
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.main, new Runnable(){
			
			public void run(){
				
				main.api.wcm.displayGui(p, parent);
				
			}
			
		}, this.seconds * 20);
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		// No actions.
		
	}
	
}
