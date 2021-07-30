package me.spypat.UltraHarcore.events;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.spypat.UltraHarcore.UltraHardcore;

public class EntityDamage implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof LivingEntity){
			LivingEntity le = (LivingEntity) event.getEntity();
			if(le==null){
				UltraHardcore.instance.removeBossBar(le);
			}
			if(le.isDead()){
				UltraHardcore.instance.removeBossBar(le);
			}else{
				if(UltraHardcore.instance.hasBossBar(le)){
					BossBar bs = UltraHardcore.instance.getBossBar(le);
					for(Player player: bs.getPlayers()){
						UltraHardcore.instance.updateBossBar(player, le, event.getFinalDamage());
					}
				}
			}
		}
		if(event.getCause().equals(DamageCause.FALL)){
			event.setDamage(Math.round(event.getDamage()*1.5));
		}
	}
}
