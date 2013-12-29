package com.github.lyokofirelyte.WCSpleef;

import java.io.File;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCSpleef.Internal.Manager;
import com.github.lyokofirelyte.WCSpleef.Internal.Spleef;

public class WCSpleef extends JavaPlugin implements CommandExecutor {
	
	public WCAPI api;
	public Spleef spleef;
	public Manager manager;
	public File spleefFile;
	public YamlConfiguration spleefYaml;
	public List<Location> arenaLocations;

	public void onEnable(){
		api = (WCAPI) api;
		spleef = new Spleef(this);
		manager = new Manager(this);
		manager.load();
		getCommand("spleef").setExecutor(this);
	}
	
	public void onDisable(){	
		manager.save();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  
		if (label.equalsIgnoreCase("spleef")){
			api.wcm.displayGui((Player)sender, new GuiSpleef(this));
		}
		
		return true;
	}
}