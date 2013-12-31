package com.github.lyokofirelyte.WCSpleef.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCSpleef.WCSpleef;
import com.github.lyokofirelyte.WCSpleef.Internal.SpleefPlayer;

public class GamePlay implements Listener {

	WCSpleef pl;

	public GamePlay(WCSpleef i) {
		pl = i;
	}
	
	@EventHandler
	public void onRound(GameStartEvent e){
		
		if (e.getRound() == 1){
			for (SpleefPlayer sp : pl.spleefPlayers.values()){
				sp.setScore(0);
				sp.setDisq(false);
			}
		}
		
		pl.spleef.setRemainingPlayers(new ArrayList<String>(pl.spleef.getPlayers()));

		Random rand = new Random();

		for (String p : pl.spleef.getPlayers()){
			Location l = pl.arenaLocations.get(rand.nextInt(pl.arenaLocations.size()-1));
			Bukkit.getPlayer(p).teleport(new Location(l.getWorld(), l.getX(), l.getY()+1, l.getZ()));
			pl.manager.spleefKit(p);
			Location pp = Bukkit.getPlayer(p).getLocation();
		    List<Location> poles = pl.spleef.getPoles();
			poles.add(new Location(pp.getWorld(), pp.getX()+1, pp.getY(), pp.getZ()));
			poles.add(new Location(pp.getWorld(), pp.getX(), pp.getY(), pp.getZ()+1));
			poles.add(new Location(pp.getWorld(), pp.getX()-1, pp.getY(), pp.getZ()));
			poles.add(new Location(pp.getWorld(), pp.getX(), pp.getY(), pp.getZ()-1));
			pl.spleef.setPoles(poles);
			for (Location ll : poles){
				if (ll.getBlock().getType() == Material.AIR){
					ll.getBlock().setType(Material.STAINED_GLASS);
				}
			}
		}
		
		pl.manager.gameMsg(e.getPlayer().getDisplayName() + " has started round &6" + e.getRound() + "&d!");
		pl.spleef.setCounter(5);
		pl.manager.spleefCount();	
	}
	
	@EventHandler
	public void onFall(PlayerFallEvent e){
		
		pl.manager.gameMsg(e.getPlayer().getDisplayName() + " &6was &oprobably &6spleefed by " + e.getSpleefer().getDisplayName() + "&6!");
		pl.spleef.remRemainingPlayer(e.getPlayer().getName());
		
		if (pl.spleef.getRemainingPlayers().size() == 1){
			if (pl.spleef.getRound() >= 7){
				cleanGame();
			} else {
				cleanArena();
			}
		}
		
		e.getPlayer().teleport(pl.spleef.getExitLocation());
	}
	
	public void cleanGame(){
		
		Bukkit.getPlayer(pl.spleef.getRemainingPlayers().get(0)).teleport(pl.spleef.getExitLocation());
		List<Integer> scores = new ArrayList<Integer>();
		
		for (SpleefPlayer sp : pl.spleefPlayers.values()){
			scores.add(sp.getScore());
		}
		
		Collections.sort(scores);
		StringBuilder sb = new StringBuilder();
		
		for (int i : scores){
			for (String s : pl.spleefPlayers.keySet()){
				if (pl.spleefPlayers.get(s).getScore() == i){
					sb.append(s + "&f: " + i + "  ");
				}
			}
		}
		
		pl.manager.gameMsg("&6Spleef Scores:");
		pl.manager.gameMsg(sb.toString().replaceAll("  ", "&f, ").trim());
		
		pl.manager.updateArena(Material.WOOL);
		pl.spleef.setGameStarted(false);
		pl.spleef.setPlayers(new ArrayList<String>());
		pl.spleef.setRemainingPlayers(new ArrayList<String>());
	}
	
	public void cleanArena(){
		
		Player winner = Bukkit.getPlayer(pl.spleef.getRemainingPlayers().get(0));
		SpleefPlayer sp = pl.spleefPlayers.get(winner.getName());
		sp.setScore(sp.getScore() + 1);
		pl.spleefPlayers.put(winner.getName(), sp);
		WCUtils.effects(winner);
		pl.manager.updateArena(Material.GLASS);
		pl.spleef.setGameStarted(false);
		pl.manager.gameMsg("Round winner: " + winner.getDisplayName() + "&d. Score: &6" + sp.getScore());
		pl.manager.gameMsg("&6Spleef is ready for another round.");
	}
}
