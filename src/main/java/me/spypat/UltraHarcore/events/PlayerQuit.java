package me.spypat.UltraHarcore.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PlayerQuit implements Listener{

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
			for(ItemStack is : event.getPlayer().getInventory().getStorageContents()){
				if(is!=null){
					if(is.hasItemMeta()){
						if(is.getItemMeta().hasDisplayName()){
							if(is.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+ "Beacon Pickaxe")){
								event.getPlayer().getInventory().removeItem(is);
							}
						}
					}
				}
			}
	}
}
