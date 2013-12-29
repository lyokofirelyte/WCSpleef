package com.github.lyokofirelyte.WCSpleef.Gui;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCSpleef.WCSpleef;
import com.github.lyokofirelyte.WCSpleef.Game.GameStartEvent;
import com.github.lyokofirelyte.WCSpleef.Internal.SpleefPlayer;

import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

public class GuiSpleef extends WCGui {

	private WCSpleef main;
	List<String> players = new ArrayList<String>();

	public GuiSpleef(WCSpleef main){
		
		super(9, "&dSpleef");
		this.main = main;
	}
	
	@Override
	public void create(){

		addButton(0, createItem("&aJoin", new String[] { "&3Join a game!" }, Material.STAINED_GLASS, 1, 0));
		addButton(1, createItem("&aLeave", new String[] { "&3Leave a game!" }, Material.STAINED_GLASS, 1, 1));
		addButton(2, createItem("&aCreate", new String[] { "&3Create an arena!" }, Material.STAINED_GLASS, 1, 3));
		addButton(3, createItem("&aFree", new String[] { "&3Free-Choice" }, Material.STAINED_GLASS, 1, 4));
		addButton(4, createItem("&aStart", new String[] { "&3Start a game!" }, Material.STAINED_GLASS, 1, 5));
		addButton(5, createItem("&aReset", new String[] { "&3Force-Reset a game!" }, Material.STAINED_GLASS, 1, 6));
		addButton(6, createItem("&aNext Round", new String[] { "&3Start the next round!" }, Material.STAINED_GLASS, 1, 7));
		addButton(7, createItem("&aExit Location", new String[] { "&3Set exit location" }, Material.STAINED_GLASS, 1, 8));
		addButton(8, createItem("&cClose", new String[] { "&cClose Menu" }, Material.STAINED_GLASS, 1, 9));
	}
	
	@Override
	public void actionPerformed(Player p){
	
		switch (slot){
			
			case 0:
				
				players = main.spleef.getPlayers();
				
				if (players.contains(p.getName())){
					s(p, "You've already joined the game!");
				} else {
					players.add(p.getName());
					main.spleef.setPlayers(players);
					main.manager.gameMsg(p.getDisplayName() + " &dhas joined the game!");
					if (!main.spleefPlayers.containsKey(p.getName())){
						SpleefPlayer sp = new SpleefPlayer(p.getName());
						main.spleefPlayers.put(p.getName(), sp);
					}
				}
				
				break;
				
			case 1:
				
				players = main.spleef.getPlayers();
				
				if (!players.contains(p.getName())){
					s(p, "You've not joined the game!");
				} else {
					main.manager.gameMsg(p.getDisplayName() + " &dhas left the game!");
					players.remove(p.getName());
					main.spleef.setPlayers(players);
				}
				
				break;
				
			case 2:
				
				if (main.spleef.getGameStarted()){
					s(p, "The game has started; you can't make a new arena!");
				} else if (permCheck(p, "wa.staff")){
					main.spleef.setArena(p.getLocation());
					main.arenaLocations = WCUtils.circle(p.getLocation(), 20, 1, false, false, 0);
					main.arenaWalkLocations = WCUtils.circle(p.getLocation(), 20, 2, false, false, 1);
					main.manager.updateArena(Material.GLASS);
				}
				
				break;
				
			case 3:
				
				if (permCheck(p, "wa.staff")){
					main.api.wcm.displayGui(p, new GuiItemSelect(main));
				}
			
				break;
				
			case 4:
				
				if (permCheck(p, "wa.staff")){
					if (main.spleef.getGameStarted() || main.spleef.getRound() > 0){
						s(p, "The game is already started!");
					} else {
						main.manager.gameMsg("The spleef game has started! Please wait for the countdown...");
				  		main.getServer().getPluginManager().callEvent(new GameStartEvent(p, 1));
					}
				} 
				
				break;
				
			case 5:
				
				if (permCheck(p, "wa.staff")){
					main.spleef.setRound(0);
					main.spleef.setPlayers(new ArrayList<String>());
					main.spleef.setRemainingPlayers(new ArrayList<String>());
					main.manager.updateArena(Material.WOOL);
				} 
				
				break;
				
			case 6:
				
				if (permCheck(p, "wa.staff")){
					if (main.spleef.getRound() > 0){
				  		main.getServer().getPluginManager().callEvent(new GameStartEvent(p, main.spleef.getRound()+1));
					} else {
						main.api.wcm.displayGui(p, new GuiMessage(main, "&c&lThe game isn't started yet!", 3, p, this));
					}
				}
					
				break;
			
			case 7:
				
				if (permCheck(p, "wa.staff")){
					main.spleef.setExitLocation(p.getLocation());
					s(p, "Location set!");
				}
				
				break;
			
			case 8:
				
				p.closeInventory();	
				break;
		}
	}
	
	public Boolean permCheck(Player p, String node){
		if (p.hasPermission(node)){
			return true;
		}
		main.api.wcm.displayGui(p, new GuiMessage(main, "&c&lYou don't have permission!", 3, p, this));
		return false;
	}
}