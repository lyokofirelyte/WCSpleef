package com.github.lyokofirelyte.WCSpleef.Internal;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCSpleef.WCSpleef;

public class Manager {

	WCSpleef pl;

	public Manager(WCSpleef i) {
		pl = i;
	}
	
	
	public void load(){
		
		File spleefFile = new File("plugins/WCSpleef/data.yml");
		
		if (!spleefFile.exists()){
			try {
				spleefFile.mkdirs();
				spleefFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		pl.spleefFile = spleefFile;
		pl.spleefYaml = YamlConfiguration.loadConfiguration(spleefFile);
		setup(pl.spleefYaml);
	}
	
	public void save(){
		
		pl.spleefYaml.set("ArenaCenter", pl.spleef.getArena());
		
		try {
			pl.spleefYaml.save(pl.spleefFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setup(YamlConfiguration spleefYaml){
		
		List<String> c = Arrays.asList(spleefYaml.getString("ArenaCenter").split(" "));
		pl.spleef.setArena(new Location(Bukkit.getWorld(c.get(0)), Double.parseDouble(c.get(1)), Double.parseDouble(c.get(2)), Double.parseDouble(c.get(3))));
		pl.arenaLocations = WCUtils.circle(pl.spleef.getArena(), 50, 1, false, false, 0);
	}
}
