package me.spypat.UltraHarcore.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntitySpawn implements Listener {
	
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event){
		//add zombie speed and stone sword and lether armor
		//skeletons add chain armor
		if(event.getEntityType().equals(EntityType.CREEPER)){
			Creeper creeper = (Creeper)event.getEntity();
			creeper.setPowered(true);
			creeper.setExplosionRadius(2);
		}else{
			if(event.getEntityType().equals(EntityType.WITHER_SKELETON)){
				LivingEntity le = (LivingEntity) event.getEntity();
				EntityEquipment ee = le.getEquipment();
				ItemStack isword = new ItemStack(Material.IRON_SWORD);
				ee.setItemInHand(isword);
			}else{
				if(event.getEntityType().equals(EntityType.ZOMBIE)){
					LivingEntity le = (LivingEntity) event.getEntity();
					EntityEquipment ee = le.getEquipment();
					Random r = new Random();
					int i = r.nextInt(5);
					if(i==1){
						ItemStack ssword = new ItemStack(Material.STONE_SWORD);
						ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
						ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
						ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
						ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
						ee.setItemInHand(ssword);
						ee.setBoots(boot);
						ee.setHelmet(helm);
						ee.setChestplate(chest);
						ee.setLeggings(leg);
						le.setMaxHealth(le.getMaxHealth()+2);
					}
					if(i==2){
						ItemStack ssword = new ItemStack(Material.IRON_SWORD);
						ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
						ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
						ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
						ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
						ee.setItemInHand(ssword);
						ee.setBoots(boot);
						ee.setHelmet(helm);
						ee.setChestplate(chest);
						ee.setLeggings(leg);
						le.setMaxHealth(le.getMaxHealth()+20);
						PotionEffect pe = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1);
						le.addPotionEffect(pe);
					}
					if(i==3){
						ItemStack ssword = new ItemStack(Material.DIAMOND_SWORD);
						ee.setItemInHand(ssword);
						le.setMaxHealth(le.getMaxHealth()+6);
						PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
						le.addPotionEffect(pe);
					}
				}
			}
		}
	}
}
