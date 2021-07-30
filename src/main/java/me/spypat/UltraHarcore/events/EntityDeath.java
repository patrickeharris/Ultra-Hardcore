package me.spypat.UltraHarcore.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.spypat.UltraHarcore.UltraHardcore;

public class EntityDeath implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if(event.getEntity().getType().equals(EntityType.PLAYER)){
			List<ItemStack> newDrops= new ArrayList<ItemStack>();
			for(ItemStack is:event.getDrops()){
				if(is.getType().equals(Material.BEACON)){
					if(is.hasItemMeta()){
		    			if(is.getItemMeta().hasDisplayName()){
							if(is.getItemMeta().getDisplayName().contains("'s")){
								newDrops.add(is);
							}
		    			}
					}
				}
				if(is.getType().equals(Material.STONE_PICKAXE)){
					if(is.hasItemMeta()){
		    			if(is.getItemMeta().hasDisplayName()){
							if(is.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+ "Beacon Pickaxe")){
								newDrops.add(is);
							}
		    			}
					}
				}
			}
			if(!newDrops.isEmpty()){
				for(ItemStack is : newDrops){
					event.getDrops().remove(is);
					if(is.getType().equals(Material.BEACON)){
						Block b = Bukkit.getServer().getWorld("world").getBlockAt(event.getEntity().getLocation());
						b.setType(Material.BEACON);
						b.setMetadata("owner", new FixedMetadataValue(UltraHardcore.instance, event.getEntity().getUniqueId()));
						Player p = (Player)event.getEntity();
						b.setMetadata("ownerName", new FixedMetadataValue(UltraHardcore.instance, p.getName()));
						UltraHardcore.instance.addBeacon(p, b.getLocation());
					}
				}
			}
		}
	}
}
