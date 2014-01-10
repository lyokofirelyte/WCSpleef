package com.github.lyokofirelyte.WCSpleef;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCNode;
import com.github.lyokofirelyte.WCSpleef.Game.GamePlay;
import com.github.lyokofirelyte.WCSpleef.Internal.Manager;
import com.github.lyokofirelyte.WCSpleef.Internal.Spleef;
import com.github.lyokofirelyte.WCSpleef.Internal.SpleefControl;
import com.github.lyokofirelyte.WCSpleef.Internal.SpleefPlayer;

public class WCSpleef extends WCNode {
	
	public WCAPI api;
	public Spleef spleef;
	public Manager manager;
	public File spleefFile;
	public YamlConfiguration spleefYaml;
	public List<Location> arenaLocations = new ArrayList<Location>();
	public List<Location> arenaWalkLocations = new ArrayList<Location>();
	public Map<String, SpleefPlayer> spleefPlayers = new HashMap<>();

	public void onEnable(){
		Plugin WCAPI = Bukkit.getServer().getPluginManager().getPlugin("WCAPI");
		api = (WCAPI) WCAPI;
		spleef = new Spleef(this);
		manager = new Manager(this);
		manager.load();
		getServer().getPluginManager().registerEvents(new GamePlay(this), this);
		getServer().getPluginManager().registerEvents(new SpleefControl(this), this);
		api.reg.registerCommands(new ArrayList<Class<?>>(Arrays.asList(GamePlay.class)), this);
	}
	
	public void onDisable(){	
		manager.save();
	}
}