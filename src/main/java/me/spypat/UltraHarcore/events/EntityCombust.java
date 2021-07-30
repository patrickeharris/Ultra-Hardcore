package me.spypat.UltraHarcore.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

import com.sk89q.worldedit.world.entity.EntityTypes;

public class EntityCombust implements Listener {
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event){
		if(!event.getEntityType().equals(EntityType.PLAYER)){
			event.setCancelled(true);
		}else{
			event.setDuration((int) Math.round(event.getDuration()*1.5));
		}
	}
}
