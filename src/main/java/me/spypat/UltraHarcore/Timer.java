package me.spypat.UltraHarcore;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Timer {
	//refresh potion effects, add to 15 to random event
	static int randomTime = 0;
	public void createTimer(){
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(UltraHardcore.instance, new Runnable() {
			 
			  public void run() {
				  randomTime++;
		    	  if(randomTime==15){
		    		  Random r = new Random();
		    		  int num = r.nextInt(16);
		    		  if(num==0){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==1){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==2){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==3){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==4){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==5){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==6){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==7){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==8){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==9){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==10){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==11){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==12){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  Zombie z = Bukkit.getServer().getWorld("world").spawn(player.getLocation(), Zombie.class);
					    	  z.setCustomName(ChatColor.RED+"Ultra Zombie");
					    	  EntityEquipment ee = z.getEquipment();
								ItemStack dsword = new ItemStack(Material.DIAMOND_SWORD);
								ee.setItemInHand(dsword);
								ItemStack ihelmet = new ItemStack(Material.IRON_HELMET);
								ItemStack ichestplate = new ItemStack(Material.IRON_CHESTPLATE);
								ItemStack ileggings = new ItemStack(Material.IRON_LEGGINGS);
								ItemStack iboots = new ItemStack(Material.IRON_BOOTS);
								ee.setBoots(iboots);
								ee.setChestplate(ichestplate);
								ee.setHelmet(ihelmet);
								ee.setLeggings(ileggings);
						    	player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==13){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  Blaze b = Bukkit.getServer().getWorld("world").spawn(player.getLocation(), Blaze.class);
					    	  b.setCustomName(ChatColor.RED+"Ultra Blaze");
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==14){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  if(num==15){
					      for(Player player : Bukkit.getServer().getOnlinePlayers()){
					    	  SkeletonHorse s = Bukkit.getServer().getWorld("world").spawn(player.getLocation(), SkeletonHorse.class);
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
					      }
		    		  }
		    		  randomTime=0;
		    	  }
			      
			  }
			}, 4800);
	}
}
