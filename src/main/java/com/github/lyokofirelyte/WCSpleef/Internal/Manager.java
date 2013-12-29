package com.github.lyokofirelyte.WCSpleef.Internal;

import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_7_R1.ChunkCoordIntPair;
import net.minecraft.server.v1_7_R1.EnumSkyBlock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;
import com.github.lyokofirelyte.WCSpleef.WCSpleef;

public class Manager {

	WCSpleef pl;

	public Manager(WCSpleef i) {
		pl = i;
	}
	
	public int y = 3;
	
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
		
		Location a = pl.spleef.getArena();
		Location b = pl.spleef.getExitLocation();
		
		pl.spleefYaml.set("ArenaCenter", a.getWorld().getName() + " " + a.getX() + " " + a.getY() + " " + a.getZ());
		pl.spleefYaml.set("ArenaExit", b.getWorld().getName() + " " + b.getX() + " " + b.getY() + " " + b.getZ());
		
		try {
			pl.spleefYaml.save(pl.spleefFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setup(YamlConfiguration spleefYaml){
		
		if (spleefYaml.getString("ArenaCenter") != null && spleefYaml.getString("ArenaExit") != null){
			List<String> c = Arrays.asList(spleefYaml.getString("ArenaCenter").split(" "));
			List<String> d = Arrays.asList(spleefYaml.getString("ArenaExit").split(" "));
			pl.spleef.setArena(new Location(Bukkit.getWorld(c.get(0)), Double.parseDouble(c.get(1)), Double.parseDouble(c.get(2)), Double.parseDouble(c.get(3))));
			pl.spleef.setExitLocation(new Location(Bukkit.getWorld(d.get(0)), Double.parseDouble(d.get(1)), Double.parseDouble(d.get(2)), Double.parseDouble(d.get(3))));
			pl.arenaLocations = WCUtils.circle(pl.spleef.getArena(), 20, 1, false, false, 0);
			Location l = pl.spleef.getArena();
			pl.arenaWalkLocations = WCUtils.circle(new Location(l.getWorld(), l.getX(), l.getY()+1, l.getZ()), 20, 2, false, false, 0);
		}
	}
	
	public void forceBlockLight(Player player, int x, int y, int z, int level) {
		net.minecraft.server.v1_7_R1.World w = ((CraftWorld) player.getWorld()).getHandle();
		w.b(EnumSkyBlock.BLOCK, x, y, z, level);
	}
    
    @SuppressWarnings("unchecked")
	public void queueChunk(Player player, int cx, int cz) {
    	((CraftPlayer) player).getHandle().chunkCoordIntPairQueue.add(new ChunkCoordIntPair(cx, cz));
    }
    
    public void updateArena(Material m){
    	for (Location l : pl.arenaLocations){
    		l.getBlock().setType(m);
			for (Player q : Bukkit.getOnlinePlayers()){
				if (q.getWorld() == l.getWorld()){
					pl.manager.forceBlockLight(q, (int)l.getX(), (int)l.getY(), (int)l.getZ(), 15);
					pl.manager.queueChunk(q, l.getChunk().getX(), l.getChunk().getZ());
				}
			}
    	}
    }
    
    public void gameMsg(String s){
		for (Player q : Bukkit.getOnlinePlayers()){
			if (pl.spleef.getPlayers().contains(q.getName())){
				s(q, s);
			}
		}
    }
    
    public void spleefCount(){
    	
    	long delay = 0L;
    	y = 3;
    	
    	for (int x = 3; x > 0; x--){
    		  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
    		  public void run() { 
    				  
    			  gameMsg("&6Spleef in " + y);
    			  
    		  } }, delay);
    		  
    		  delay = delay + 20L;
    		  y = y - 1;
    	}
    	
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
		public void run() { 
			  
			pl.spleef.setGameStarted(true);
			  
		} }, delay);
    }
    
    public void spleefKit(String p){
    	
    	List<Material> items = Arrays.asList(Material.DIAMOND_AXE, Material.DIAMOND_SPADE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SWORD, Material.SHEARS);
    
    	for (Material m : items){
    		ItemStack i = InventoryManager.createItem(WCUtils.AS("&aSpleef Item"), new String[] { "It's Awesome!" }, Enchantment.DIG_SPEED, 2, m, 0, 1);
    		Bukkit.getPlayer(p).getInventory().addItem(i);
    	}
    }
}