package me.spypat.UltraHarcore.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.spypat.UltraHarcore.UltraHardcore;

public class EntityShootBow implements Listener {
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event){
		UltraHardcore plugin = UltraHardcore.getInstance();
		if(event.getEntityType().equals(EntityType.SKELETON)){
			Random r = new Random();
			int i = r.nextInt(19);
			Arrow a = (Arrow) event.getProjectile();
			if(i==1){
				Entity tnt = Bukkit.getWorld("world").spawn(a.getLocation(), TNTPrimed.class);
				((TNTPrimed)tnt).setFuseTicks(100);
				tnt.setVelocity(a.getVelocity());
				a.remove();
			}
			if(i==3){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "d"));
			}
			if(i==5){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "p"));
			}
			if(i==9){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "s"));
			}
			if(i==17){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "w"));
			}
			if(i==7){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "p2"));
			}
			if(i==11){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "w2"));
			}
			if(i==13){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "s2"));
			}
			if(i==15){
				a.setMetadata("effect", new FixedMetadataValue(plugin, "d2"));
			}
		}
	}
}
