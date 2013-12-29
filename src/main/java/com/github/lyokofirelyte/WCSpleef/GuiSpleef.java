package com.github.lyokofirelyte.WCSpleef;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WCAPI.WCGui;
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
		addButton(3, createItem("&aDelete", new String[] { "&3Delete current arena!" }, Material.STAINED_GLASS, 1, 4));
		addButton(4, createItem("&aStart", new String[] { "&3Start a game!" }, Material.STAINED_GLASS, 1, 5));
		addButton(5, createItem("&aStop", new String[] { "&3Force-Stop a game!" }, Material.STAINED_GLASS, 1, 6));
		addButton(6, createItem("&aNext Round", new String[] { "&3Start the next round!" }, Material.STAINED_GLASS, 1, 7));
	}
	
	@Override
	public void actionPerformed(Player p){
	
		switch (slot){
			
		case 0:
			
			players = main.spleef.getPlayers();
			
			if (players.contains(p.getName())){
				s(p, "You've already joined the game!");
				break;
			}
			
			players.add(p.getName());
			main.spleef.setPlayers(players);
			s(p, "Joined the game!");
			break;
			
		case 1:
			
			players = main.spleef.getPlayers();
			
			if (!players.contains(p.getName())){
				s(p, "You've not joined the game!");
			} else {
				players.remove(p.getName());
				main.spleef.setPlayers(players);
				s(p, "Left!");
			}
			
			break;
			
		case 2:
			
			
		}
	}
}
