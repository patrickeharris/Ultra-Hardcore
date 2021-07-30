package me.spypat.UltraHarcore.events;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.spypat.UltraHarcore.UltraHardcore;


public class InventoryClick implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getCurrentItem()==null){
			return;
		}
		if(event.getCurrentItem().getType()==null){
			return;
		}
		if(event.getCurrentItem().getType().equals(Material.STONE_PICKAXE)){
			ItemStack is = event.getCurrentItem();
    		if(is.hasItemMeta()){
    			if(is.getItemMeta().hasDisplayName()){
    				if(is.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+ "Beacon Pickaxe")){
    					event.setCancelled(true);
    				}
    			}
    		}
		}
		if(event
				.getCurrentItem()
				.getType()
				.equals(Material.BEACON)){
			if(event.getCurrentItem().hasItemMeta()){
				if(event.getCurrentItem().getItemMeta().hasDisplayName()){
						if(event.getCurrentItem().getItemMeta().getDisplayName().contains("'s")){
								if(event.isLeftClick()){
										if(event.getWhoClicked().hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)&&event.getWhoClicked().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)){
												event.getWhoClicked().removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
												event.getWhoClicked().removePotionEffect(PotionEffectType.HEALTH_BOOST);
												event.getWhoClicked().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
												event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW_FALLING);
												event.getWhoClicked().removePotionEffect(PotionEffectType.JUMP);
												for(ItemStack is : event.getWhoClicked().getInventory().getStorageContents()){
													if(is!=null){
														if(is.hasItemMeta()){
															if(is.getItemMeta().hasDisplayName()){
																if(is.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+ "Beacon Pickaxe")){
																	event.getWhoClicked().getInventory().removeItem(is);
																}
															}
														}
													}
												}
										}else{
											if(event.getWhoClicked().getInventory().getItem(8)!=null){
												event.getWhoClicked().sendMessage(ChatColor.RED+"Please Clear Your Far Right Hotbar Inventory Slot!");
												event.setCancelled(true);
												return;
											}else{
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
												event.getWhoClicked().addPotionEffects(effects);
												ItemStack specialPick = new ItemStack(Material.STONE_PICKAXE);
												ItemMeta pickMeta = specialPick.getItemMeta();
												pickMeta.setUnbreakable(true);
												pickMeta.addEnchant(Enchantment.DIG_SPEED, 4, true);
												pickMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Beacon Pickaxe");
												specialPick.setItemMeta(pickMeta);
												//add check don't overwrite items
												event.getWhoClicked().getInventory().setItem(8, specialPick);
											}
										}
								}
								if(event.isRightClick()){
									Inventory heads = Bukkit.createInventory(null, 9, "Collected Beacons");
									if(UltraHardcore.instance.hasBeacons((Player)event.getWhoClicked())){
										for(UUID uuid : UltraHardcore.instance.getBeacons((Player)event.getWhoClicked())){
											ItemStack beacon = new ItemStack(Material.BEACON);
									        ItemMeta meta = beacon.getItemMeta();
									        String name = null;
									        for(Player p2 : Bukkit.getOnlinePlayers()){
									        	if(p2.getUniqueId().equals(uuid)){
									        		name = p2.getName();
									        	}
									        }
									        if(name==null){
									        	name = Bukkit.getServer().getOfflinePlayer(uuid).getName();
									        }
									        meta.setDisplayName(ChatColor.BLUE+name+"'s" + ChatColor.GOLD+" Beacon");
									        ArrayList<String> lore = new ArrayList<String>();
									        lore.add(uuid.toString());
									        meta.setLore(lore);
									        meta.addEnchant(Enchantment.LUCK, 1, true);
									        beacon.setItemMeta(meta);
									        heads.addItem(beacon);
										}
									}
									event.getWhoClicked().openInventory(heads);
								}
								event.setCancelled(true);
						}
				}
			}
		}
	}
}
