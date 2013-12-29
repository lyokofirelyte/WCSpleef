package com.github.lyokofirelyte.WCSpleef.Internal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import com.github.lyokofirelyte.WCSpleef.WCSpleef;

public class Spleef {

	WCSpleef pl;

	public Spleef(WCSpleef i) {
		pl = i;
	}
	
	Location arena;
	List<String> players = new ArrayList<>();
	
	public Location getArena(){
		return arena;
	}
	
	public List<String> getPlayers(){
		return players;
	}
	
	
	
	public void setArena(Location a){
		arena = a;
	}
	
	public void setPlayers(List<String> a){
		players = a;
	}
}
