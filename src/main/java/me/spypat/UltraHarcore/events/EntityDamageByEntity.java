package me.spypat.UltraHarcore.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.spypat.UltraHarcore.UltraHardcore;

public class EntityDamageByEntity implements Listener{

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
			if(event.getDamager() instanceof Arrow && event.getEntity() instanceof Player){
				Arrow a = (Arrow) event.getDamager();
				Player p = (Player) event.getEntity();
				if(a.hasMetadata("effect")){
					List<MetadataValue> values = a.getMetadata("effect");
				    for(MetadataValue val : values)
				    {
				        if(val.getOwningPlugin().getName().equals(UltraHardcore.getInstance().getName()))
				        {
				           if(val.asString().equals("d")){
								PotionEffect pe = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0);
								p.addPotionEffect(pe);
				           }
				           if(val.asString().equals("w")){
								PotionEffect pe = new PotionEffect(PotionEffectType.WEAKNESS, 100, 0);
								p.addPotionEffect(pe);
				           }
				           if(val.asString().equals("s")){
								PotionEffect pe = new PotionEffect(PotionEffectType.SLOW, 100, 0);
								p.addPotionEffect(pe);
				           }
				           if(val.asString().equals("p")){
								PotionEffect pe = new PotionEffect(PotionEffectType.POISON, 100, 0);
								p.addPotionEffect(pe);
				           }
				           if(val.asString().equals("d2")){
								PotionEffect pe = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 1);
								p.addPotionEffect(pe);
				           }
				           if(val.asString().equals("w2")){
								PotionEffect pe = new PotionEffect(PotionEffectType.WEAKNESS, 100, 1);
								p.addPotionEffect(pe);
				           }
				           if(val.asString().equals("s2")){
								PotionEffect pe = new PotionEffect(PotionEffectType.SLOW, 100, 1);
								p.addPotionEffect(pe);
				           }
				           if(val.asString().equals("p2")){
								PotionEffect pe = new PotionEffect(PotionEffectType.POISON, 100, 1);
								p.addPotionEffect(pe);
				           }
				        }
				    }

				}
			}
	}
}
