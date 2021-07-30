package me.spypat.UltraHarcore.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event){
		if(event.getItemDrop().getItemStack().hasItemMeta()){
			if(event.getItemDrop().getItemStack().getItemMeta().hasDisplayName()){
				if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+ "Beacon Pickaxe")){
					event.setCancelled(true);
				}
			}
		}
	}
}
