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
	Location exit;
	Boolean gameStarted = false;
	int round = 0;
	List<String> players = new ArrayList<>();
	List<String> remainingPlayers = new ArrayList<>();
	
	public Location getArena(){
		return arena;
	}
	
	public Location getExitLocation(){
		return exit;
	}
	
	public Boolean getGameStarted(){
		return gameStarted;
	}
	
	public int getRound(){
		return round;
	}
	
	public List<String> getPlayers(){
		return players;
	}
	
	public List<String> getRemainingPlayers(){
		return remainingPlayers;
	}
	
	
	public void setArena(Location a){
		arena = a;
	}
	
	public void setExitLocation(Location a){
		exit = a;
	}
	
	public void setGameStarted(Boolean a){
		gameStarted = a;
	}
	
	public void setRound(int a){
		round = a;
	}
	
	public void setPlayers(List<String> a){
		players = a;
	}
	
	public void setRemainingPlayers(List<String> a){
		remainingPlayers = a;
	}
	
	public void remRemainingPlayer(String a){
		remainingPlayers.remove(a);
	}
}
