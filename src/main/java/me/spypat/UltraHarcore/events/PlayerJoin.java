package me.spypat.UltraHarcore.events;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.spypat.UltraHarcore.UltraHardcore;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
	  if(!event.getPlayer().hasPlayedBefore()) {
		UltraHardcore.instance.addPlayer(event.getPlayer());
	    Player player = event.getPlayer();
	    player.sendTitle(ChatColor.GREEN+"Welcome To "+ ChatColor.RED+"Ultra Hardcore Survival"+ChatColor.GREEN+"!", "");
	    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 2));
	    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 60, 5));
	    Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
       
        //Our random generator
        Random r = new Random();   

        //Get the type
        int rt = r.nextInt(4) + 1;
        Type type = Type.BALL;       
        if (rt == 1) type = Type.BALL;
        if (rt == 2) type = Type.BALL_LARGE;
        if (rt == 3) type = Type.BURST;
        if (rt == 4) type = Type.CREEPER;
        if (rt == 5) type = Type.STAR;
       
        //Get our random colours   
        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);
       
        //Create our effect with this
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
       
        //Then apply the effect to the meta
        fwm.addEffect(effect);
       
        //Generate some random power and set it
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);
       
        //Then apply this to our rocket
        fw.setFireworkMeta(fwm);           

        //Give Player Beacon
        ItemStack beacon = new ItemStack(Material.BEACON);
        ItemMeta meta = beacon.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE+player.getName()+"'s" + ChatColor.GOLD+" Beacon");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(player.getUniqueId().toString());
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, true);
        beacon.setItemMeta(meta);
        player.getInventory().setItem(9, beacon);
        PotionEffect jump = new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,1,true);
        PotionEffect fire = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,Integer.MAX_VALUE,1,true);
        PotionEffect slow_fall = new PotionEffect(PotionEffectType.SLOW_FALLING,Integer.MAX_VALUE,1,true);
        PotionEffect dolphin_grace = new PotionEffect(PotionEffectType.DOLPHINS_GRACE,Integer.MAX_VALUE,1,true);
        PotionEffect health = new PotionEffect(PotionEffectType.HEALTH_BOOST,Integer.MAX_VALUE,1,true);
        ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
        effects.add(jump);
        effects.add(fire);
        effects.add(slow_fall);
        effects.add(dolphin_grace);
        effects.add(health);
        player.addPotionEffects(effects);
        ItemStack specialPick = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta pickMeta = specialPick.getItemMeta();
        pickMeta.setUnbreakable(true);
        pickMeta.addEnchant(Enchantment.DIG_SPEED, 4, true);
        pickMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Beacon Pickaxe");
        specialPick.setItemMeta(pickMeta);
        player.getInventory().setItem(8, specialPick);
        //Timer for potion effects
        //Inventory click left toggle on off right see details/ other player beacons When beacon enabled, show other beacons enabled how many blocks apart beacons are?
        //Can't drop beacon
        //Die natural beacon placed on ground any1 can mine and take (test if display name contains 's)
	    //Give Starter Kit
        //Iron Tools
        //Water Bottles
        //Food
        //Leather Armor
        ItemStack beef = new ItemStack(Material.COOKED_BEEF);
        beef.setAmount(8);
        ItemStack bottle = new ItemStack(Material.POTION, 1, (byte)0);
        bottle.setAmount(8);
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
        player.getInventory().addItem(new ItemStack(Material.IRON_AXE));
        player.getInventory().addItem(new ItemStack(Material.IRON_SHOVEL));
        player.getInventory().addItem(new ItemStack(beef));
        player.getInventory().addItem(new ItemStack(bottle));
        player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        return;
	  }
	  if(!UltraHardcore.instance.hasBeacon(event.getPlayer())){
		  event.getPlayer().sendMessage(ChatColor.RED+"Please Empty Your Armor and Inventory Slots and Run /fixbeacon to Claim Your Beacon!");
	  }
	}
	
	private Color getColor(int i) {
		Color c = null;
		if(i==1){
		c=Color.AQUA;
		}
		if(i==2){
		c=Color.BLACK;
		}
		if(i==3){
		c=Color.BLUE;
		}
		if(i==4){
		c=Color.FUCHSIA;
		}
		if(i==5){
		c=Color.GRAY;
		}
		if(i==6){
		c=Color.GREEN;
		}
		if(i==7){
		c=Color.LIME;
		}
		if(i==8){
		c=Color.MAROON;
		}
		if(i==9){
		c=Color.NAVY;
		}
		if(i==10){
		c=Color.OLIVE;
		}
		if(i==11){
		c=Color.ORANGE;
		}
		if(i==12){
		c=Color.PURPLE;
		}
		if(i==13){
		c=Color.RED;
		}
		if(i==14){
		c=Color.SILVER;
		}
		if(i==15){
		c=Color.TEAL;
		}
		if(i==16){
		c=Color.WHITE;
		}
		if(i==17){
		c=Color.YELLOW;
		}
		 
		return c;
	}
}
