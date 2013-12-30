package com.github.lyokofirelyte.WCSpleef.Internal;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import static com.github.lyokofirelyte.WCAPI.WCUtils.s;
import com.github.lyokofirelyte.WCSpleef.WCSpleef;
import com.github.lyokofirelyte.WCSpleef.Game.PlayerFallEvent;

public class SpleefControl implements Listener {
	
	WCSpleef pl;

	public SpleefControl(WCSpleef i) {
		pl = i;
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		
		if (pl.arenaLocations.contains(e.getBlock().getLocation()) && !e.getPlayer().hasPermission("wa.staff")){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		if (pl.arenaLocations.contains(e.getBlock().getLocation()) && !pl.spleef.getGameStarted()){
			s(e.getPlayer(), "The game has not started yet, so you can't break blocks.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e){

		if (arenaWalkCheck(e.getFrom()) && !pl.spleef.getGameStarted()){
			e.getPlayer().setVelocity(new Vector(0, 0, 0));
		}
		
		if (!arenaWalkCheck(e.getFrom()) && arenaWalkCheck(e.getTo())){	
			if (pl.spleef.getGameStarted()){
				e.getPlayer().teleport(e.getFrom());
				s(e.getPlayer(), "The game is in progress.");
			}
		}
		
		if (arenaWalkCheck(e.getFrom()) && !arenaWalkCheck(e.getTo())){	
			if (pl.spleef.getGameStarted()){
				pl.getServer().getPluginManager().callEvent(new PlayerFallEvent(e.getPlayer()));
			}
			e.getPlayer().getInventory().clear();
		}
	}
	
	@EventHandler
	public void onLog(PlayerQuitEvent e){
		
		List<String> players = pl.spleef.getPlayers();
		List<String> remPlayers = pl.spleef.getRemainingPlayers();
		
		if (players.contains(e.getPlayer().getName())){
			players.remove(e.getPlayer().getName());
			pl.spleef.setPlayers(players);
			pl.manager.gameMsg(e.getPlayer().getDisplayName() + " was resigned from the game due to logging out. They can re-join on the next round.");
		}
		
		if (remPlayers.contains(e.getPlayer().getName())){
			remPlayers.remove(e.getPlayer().getName());
			pl.spleef.setRemainingPlayers(remPlayers);
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		
		if (pl.spleef.getPlayers().contains(e.getPlayer().getName()) && arenaWalkCheck(e.getPlayer().getLocation()) && !e.getMessage().toLowerCase().startsWith("/spleef")){
			s(e.getPlayer(), "Only /spleef is allowed during a game! Wait until you get out or win to do that.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(ItemSpawnEvent e){
		
		if (pl.arenaLocations.contains(e.getLocation())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDecay(LeavesDecayEvent e){

		if (pl.arenaLocations.contains(e.getBlock().getLocation())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onGravity(EntityChangeBlockEvent e){
		
		if (pl.arenaLocations.contains(e.getBlock().getLocation()) && !(e.getEntity() instanceof Player)){
			if (e.getBlock().getType().equals(Material.SAND) || e.getBlock().getType().equals(Material.GRAVEL)){ 
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onFade(BlockFadeEvent e){
		
		if (pl.arenaLocations.contains(e.getBlock().getLocation())){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		
		if (pl.arenaLocations.contains(e.getLocation())){
			e.setCancelled(true);
		}
	}
	
	public Boolean arenaWalkCheck(Location l){
		
		List<Location> spleef = pl.arenaWalkLocations;
		
		for (Location a : spleef){
			if (Math.round(l.getX()) == Math.round(a.getX()) && Math.round(l.getY()) == Math.round(a.getY()) && Math.round(l.getZ()) == Math.round(a.getZ()) && l.getWorld() == a.getWorld()){
				return true;
			}
		}
		return false;
	}
}