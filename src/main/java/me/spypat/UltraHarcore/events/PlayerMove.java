package me.spypat.UltraHarcore.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.sk89q.worldedit.extension.factory.ItemFactory;

import me.spypat.UltraHarcore.UltraHardcore;

public class PlayerMove implements Listener{

	@EventHandler
	public void PlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		for(Entity entity : player.getNearbyEntities(5, 5, 5)){
			if(entity instanceof LivingEntity){
				LivingEntity le = (LivingEntity) entity;
				if(!UltraHardcore.instance.hasBossBar(le)){
					if(player.hasLineOfSight(entity)){
						if(!UltraHardcore.instance.hasPlayer(player)){
							if(entity!=null)
								if(!entity.isDead())
									if(le!=null)
										if(!le.isDead())
											UltraHardcore.instance.addBossBar(le, player);
						}else{
							LivingEntity e2 = UltraHardcore.instance.getEntity(player);
							if(e2==null){
								UltraHardcore.instance.removeBossBarPlayer(player);
							}else
							if(e2.isDead()){
								UltraHardcore.instance.removeBossBarPlayer(player);
							}else
							if(le.isDead()){
								UltraHardcore.instance.removeBossBarPlayer(player);
							}else
							if(e2.getLocation().distance(player.getLocation())>le.getLocation().distance(player.getLocation())){
								UltraHardcore.instance.removeBossBar(e2);
								UltraHardcore.instance.addBossBar(le, player);
							}
						}
					}
				}
				
			}
			if(entity.getType().equals(EntityType.SKELETON)){
				LivingEntity le = (LivingEntity) entity;
				if(!le.getScoreboardTags().contains("sword")){
					EntityEquipment ee = le.getEquipment();
					ItemStack isword = new ItemStack(Material.IRON_SWORD);
					ee.setItemInHand(isword);
					le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 1));
					le.addScoreboardTag("sword");
					return;
				}
			}
		}
		for(Entity entity : player.getNearbyEntities(10, 10, 10)){
			if(entity instanceof LivingEntity){
				LivingEntity le = (LivingEntity) entity;
				if(player.getNearbyEntities(5, 5, 5).contains(entity))
					return;
				if(UltraHardcore.instance.hasBossBar(le)){
					UltraHardcore.instance.removeBossBar(le);
				}
				if(entity.getType().equals(EntityType.SKELETON)){
					if(le.getScoreboardTags().contains("sword")){
						EntityEquipment ee = le.getEquipment();
						ItemStack bow = new ItemStack(Material.BOW);
						ee.setItemInHand(bow);
						le.removePotionEffect(PotionEffectType.SPEED);
						le.removeScoreboardTag("sword");
					}
				}
			}
		}
	}
}
