package com.github.lyokofirelyte.WCSpleef.Gui;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCSpleef.WCSpleef;

public class GuiItemSelect extends WCGui {

	private WCSpleef main;
	List<String> players = new ArrayList<String>();
	List<Material> mats = new ArrayList<Material>(Arrays.asList(Material.GRAVEL, Material.SAND, Material.GLASS, Material.LEAVES, Material.NETHERRACK, Material.SNOW_BLOCK, Material.GRASS, Material.BEDROCK, Material.ICE, Material.SOUL_SAND, Material.WOOL, Material.PACKED_ICE, Material.STAINED_GLASS, Material.STONE));

	public GuiItemSelect(WCSpleef main){
		
		super(18, "&dMaterial Select");
		this.main = main;
	}
	
	@Override
	public void create(){

		int x = 0;
		
		for (Material m : mats){
			addButton(x, createItem(m.name().toString().toLowerCase(), new String[] { "" }, m, 1, 0));
			x++;
		}
	}
	
	@Override
	public void actionPerformed(Player p){
	
		if (item != null){
			main.manager.updateArena(item.getType());
			p.closeInventory();
		}
	}
}