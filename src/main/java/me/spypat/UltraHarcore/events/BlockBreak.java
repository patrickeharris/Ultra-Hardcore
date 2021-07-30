package me.spypat.UltraHarcore.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.spypat.UltraHarcore.UltraHardcore;

public class BlockBreak implements Listener{

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(event.getBlock().getType()!=null){
			if(event.getBlock().getType().equals(Material.BEACON)){
				if(event.getBlock().hasMetadata("owner")){
					String owner = event.getBlock().getMetadata("owner").get(0).asString();
					String ownerName = event.getBlock().getMetadata("ownerName").get(0).asString();
					if(UltraHardcore.instance.isBeacon(event.getPlayer(), event.getBlock().getLocation())){
						if(event.getPlayer().getInventory().getItem(9)!=null){
							event.getPlayer().sendMessage(ChatColor.RED+"You Must Remove The Item From Your Top Left Inventory Slot Before Mining Your Beacon!");
							event.setCancelled(true);
							return;
						}
						ItemStack beacon = new ItemStack(Material.BEACON);
            			ItemMeta meta = beacon.getItemMeta();
            			meta.setDisplayName(ChatColor.BLUE+event.getPlayer().getName()+"'s" + ChatColor.GOLD+" Beacon");
            			ArrayList<String> lore = new ArrayList<String>();
            			lore.add(event.getPlayer().getUniqueId().toString());
            			meta.setLore(lore);
            			meta.addEnchant(Enchantment.LUCK, 1, true);
            			beacon.setItemMeta(meta);
            			event.getPlayer().getInventory().setItem(9, beacon);
						event.setDropItems(false);
						Bukkit.broadcastMessage(ChatColor.DARK_AQUA+event.getPlayer().getDisplayName()+ChatColor.GREEN+" Has Mined "+ChatColor.DARK_AQUA+event.getPlayer().getDisplayName()+"'s"+ChatColor.GREEN+" Beacon!");
					}else{
						UltraHardcore.instance.mineBeacon(event.getPlayer(), ownerName, owner);
						event.setDropItems(false);
					}
				}
			}
		}
	}
}
