/*
    UltraHardcore Server Plugin for Minecraft
 */

package me.spypat.UltraHarcore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import me.spypat.UltraHarcore.events.BlockBreak;
import me.spypat.UltraHarcore.events.EntityCombust;
import me.spypat.UltraHarcore.events.EntityDamage;
import me.spypat.UltraHarcore.events.EntityDamageByEntity;
import me.spypat.UltraHarcore.events.EntityDeath;
import me.spypat.UltraHarcore.events.EntityShootBow;
import me.spypat.UltraHarcore.events.EntitySpawn;
import me.spypat.UltraHarcore.events.InventoryClick;
import me.spypat.UltraHarcore.events.PlayerDropItem;
import me.spypat.UltraHarcore.events.PlayerJoin;
import me.spypat.UltraHarcore.events.PlayerMove;
import me.spypat.UltraHarcore.events.PlayerQuit;

public class UltraHardcore extends JavaPlugin
{
	//for convenience, a reference to the instance of this plugin
	public static UltraHardcore instance;
	
	//for logging to the console and log file
	private static Logger log;
	
	public static Timer timerInstance = new Timer();
	
	int randomTime = 0;
    

    ConcurrentHashMap<UUID, Boolean> softMuteMap = new ConcurrentHashMap<UUID, Boolean>();
    HashMap<String, ArrayList<String>> tp = new HashMap<String, ArrayList<String>>();
	
	
	private HashMap<UUID, BossBar> bossbars = new HashMap<UUID, BossBar>();
	private HashMap<UUID, UUID> players = new HashMap<UUID, UUID>();
	private HashMap<UUID, Location> beacons = new HashMap<UUID, Location>();
	private HashMap<String, UUID> beaconNames = new HashMap<String, UUID>();
	private HashMap<UUID, List<UUID>> collectedBeacons = new HashMap<UUID, List<UUID>>();
	private List<UUID> player = new ArrayList<UUID>();
	FileConfiguration config = getConfig();
	
	
	//initializes well...   everything
	public void onEnable()
	{ 		
	    instance = this;
		log = instance.getLogger();
		
		
		//register for events
		PluginManager pluginManager = this.getServer().getPluginManager();
		
		//player events
		Listener entityCombust = new EntityCombust();
		pluginManager.registerEvents(entityCombust, this);
		Listener entitySpawn = new EntitySpawn();
		pluginManager.registerEvents(entitySpawn, this);
		Listener inventoryClick = new InventoryClick();
		pluginManager.registerEvents(inventoryClick, this);
		Listener playerJoin = new PlayerJoin();
		pluginManager.registerEvents(playerJoin, this);
		Listener playerMove = new PlayerMove();
		pluginManager.registerEvents(playerMove, this);
		Listener entityDamage = new EntityDamage();
		pluginManager.registerEvents(entityDamage, this);
		Listener entityDeath = new EntityDeath();
		pluginManager.registerEvents(entityDeath, this);
		Listener playerDropItem = new PlayerDropItem();
		pluginManager.registerEvents(playerDropItem, this);
		Listener blockBreak = new BlockBreak();
		pluginManager.registerEvents(blockBreak, this);
		Listener playerQuit = new PlayerQuit();
		pluginManager.registerEvents(playerQuit, this);
		Listener entityShootBow = new EntityShootBow();
		pluginManager.registerEvents(entityShootBow, this);
		Listener entityDamageByEntity = new EntityDamageByEntity();
		pluginManager.registerEvents(entityDamageByEntity, this);
		
		if(config.contains("beaconsLoc")){
			for(Object s : config.getConfigurationSection("beaconsLoc").getKeys(false)){
				String string = s.toString();
				String[] strings = string.split(",");
				String uuid = strings[0];
				String x = strings[1];
				String y = strings[2];
				String z = strings[3];
				Location loc = new Location(Bukkit.getServer().getWorld("world"), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
				beacons.put(UUID.fromString(uuid), loc);
				Block b = Bukkit.getServer().getWorld("world").getBlockAt(loc);
				b.setMetadata("owner", new FixedMetadataValue(UltraHardcore.instance, UUID.fromString(uuid)));
			}
		}
		if(config.contains("beaconsName")){
			for(Object s : config.getConfigurationSection("beaconsName").getKeys(false)){
				String string1 = s.toString();
				String[] strings = string1.split(",");
				String string = strings[0];
				String string2 = strings[1];
				beaconNames.put(string, UUID.fromString(string2));
				Block b = Bukkit.getServer().getWorld("world").getBlockAt(beacons.get(UUID.fromString(string2)));
				b.setMetadata("ownerName", new FixedMetadataValue(UltraHardcore.instance, string));
			}
		}
		if(config.contains("beacons")){
			for(Object s : config.getConfigurationSection("beacons").getKeys(false)){
				String string = s.toString();
				String string1 = s.toString();
				String[] strings = string1.split(",");
				List<UUID> uuids = new ArrayList<UUID>();
				uuids.add(UUID.fromString(strings[1]));
				if(collectedBeacons.containsKey(UUID.fromString(strings[0]))){
					List<UUID> uuids2 = collectedBeacons.get(UUID.fromString(strings[0]));
					uuids2.add(UUID.fromString(strings[1]));
					collectedBeacons.replace(UUID.fromString(strings[0]), uuids2);
				}else{
					collectedBeacons.put(UUID.fromString(strings[0]), uuids);
				}
			}
		}
		if(config.contains("players")){
			for(Object s : config.getConfigurationSection("players").getKeys(false)){
				String string = s.toString();
				player.add(UUID.fromString(string));
			}
		}
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			 
			  public void run() {
				  randomTime++;
		    	  if(randomTime==15){
				      for(Player player : Bukkit.getServer().getOnlinePlayers()){
		    		  Random r = new Random();
		    		  int num = r.nextInt(27);
		    		  if(num==0){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==1){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==2){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==3){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==4){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==5){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==6){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==7){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==8){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==9){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==10){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==11){
					    	  player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 1));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==12){
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
		    		  if(num==13){
					    	  Blaze b = Bukkit.getServer().getWorld("world").spawn(player.getLocation(), Blaze.class);
					    	  b.setCustomName(ChatColor.RED+"Ultra Blaze");
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==14){
					    	  player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==15){
					    	  SkeletonHorse s = Bukkit.getServer().getWorld("world").spawn(player.getLocation(), SkeletonHorse.class);
					    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
		    		  }
		    		  if(num==0){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==16){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==17){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==18){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==19){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==20){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==21){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==22){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==23){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==24){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==25){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
	    		  if(num==26){
				    	  player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 0));
				    	  player.sendMessage(ChatColor.BLACK+"A Random Event Has Started!");
	    		  }
				      }
		    		  randomTime=0;
		    	  }
			      
			  }
			}, 0L, 4800L);
	}
	


    //handles slash commands
	@SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		
		Player player = null;
		if (sender instanceof Player)
		{
			player = (Player) sender;
		}
		
		//claim
        if(cmd.getName().equalsIgnoreCase("fixbeacon") && player != null)
        {
        	if(!this.player.contains(player.getUniqueId())){
            		if(isInventoryEmpty(player)){
            			this.player.add(player.getUniqueId());
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
                    	return true;
            		}
                    player.sendMessage(ChatColor.RED+"You Do Not Have An Empty Inventory And Armor Slots!");
        	}
            return true;
        }
        return true;
	}
	
	
	public void onDisable()
	{ 
		//save data for any online players
		@SuppressWarnings("unchecked")
        Collection<Player> players = (Collection<Player>)this.getServer().getOnlinePlayers();
		for(Player player : players)
		{
			UUID playerID = player.getUniqueId();
		}
		
		if(!player.isEmpty()){
			for(UUID uuid : player){
				config.set("players."+uuid.toString(),true);
			}
		}
		if(!collectedBeacons.isEmpty()){
			for(UUID uuid : collectedBeacons.keySet()){
				for(UUID uuid2 : collectedBeacons.get(uuid)){
					config.set("beacons."+uuid.toString()+","+uuid2.toString(),true);
				}
			}
		}
		if(!beaconNames.isEmpty()){
			for(String name : beaconNames.keySet()){
				config.set("beaconsName."+name+","+beaconNames.get(name).toString(), true);
			}
		}
		if(!beacons.isEmpty()){
			for(UUID uuid : beacons.keySet()){
				Location loc = beacons.get(uuid);
				config.set("beaconsLoc."+uuid+","+String.valueOf(loc.getBlockX())+","+String.valueOf(loc.getBlockY())+","+String.valueOf(loc.getBlockZ()), true);
			}
		}
		config.set("players."+"2b27b413-5bc2-499e-ba07-d553b560a020", true);
		config.set("beacons."+"2b27b413-5bc2-499e-ba07-d553b560a020"+","+"2b27b413-5bc2-499e-ba07-d553b560a020", true);
		config.set("beaconsName."+"test"+","+"2b27b413-5bc2-499e-ba07-d553b560a020", true);
		config.set("beaconsLoc."+"2b27b413-5bc2-499e-ba07-d553b560a020"+","+String.valueOf(0)+","+String.valueOf(0)+","+String.valueOf(1), true);
		saveConfig();
		//dump any remaining unwritten log entries
	}
	
	
	static boolean isInventoryEmpty(Player player)
	{
	    PlayerInventory inventory = player.getInventory();
        ItemStack [] armorStacks = inventory.getArmorContents();
        
        //check armor slots, stop if any items are found
        for(int i = 0; i < armorStacks.length; i++)
        {
            if(!(armorStacks[i] == null || armorStacks[i].getType() == Material.AIR)) return false;
        }
        
        //check other slots, stop if any items are found
        ItemStack [] generalStacks = inventory.getContents();
        for(int i = 0; i < generalStacks.length; i++)
        {
            if(!(generalStacks[i] == null || generalStacks[i].getType() == Material.AIR)) return false;
        }
        
	    return true;
    }

	
	
	private static Block getTargetNonAirBlock(Player player, int maxDistance) throws IllegalStateException
    {
        BlockIterator iterator = new BlockIterator(player.getLocation(), player.getEyeHeight(), maxDistance);
        Block result = player.getLocation().getBlock().getRelative(BlockFace.UP);
        while (iterator.hasNext())
        {
            result = iterator.next();
            if(result.getType() != Material.AIR) return result;
        }
        
        return result;
    }

//add ip ban
    



    public ItemStack getItemInHand(Player player, EquipmentSlot hand)
    {
        if(hand == EquipmentSlot.OFF_HAND) return player.getInventory().getItemInOffHand();
        return player.getInventory().getItemInMainHand();
    }
    
    public void addBossBar(LivingEntity entity, Player player){
		BossBar bs = Bukkit.getServer().createBossBar(entity.getType().toString(), BarColor.RED, BarStyle.SOLID);
		bs.setProgress(entity.getHealth()/entity.getMaxHealth());
		bs.addPlayer(player);
		bossbars.put(entity.getUniqueId(), bs);
		players.put(player.getUniqueId(), entity.getUniqueId());
    }
    public boolean hasBossBar(LivingEntity entity){
    	if(bossbars.containsKey(entity.getUniqueId()))
    		return true;
    	return false;
    }
    public void removeBossBar(LivingEntity entity){
    	BossBar bs = bossbars.get(entity.getUniqueId());
    	for(Player player : bs.getPlayers()){
    		bs.hide();
    		players.remove(player.getUniqueId());
    	}
    	bossbars.remove(entity.getUniqueId());
    }
    public void removeBossBarPlayer(Player player){
    	if(players.containsKey(player.getUniqueId())){
    		BossBar bs = bossbars.get(players.get(player.getUniqueId()));
    		bs.hide();
		    bossbars.remove(players.get(player.getUniqueId()));
    		players.remove(player.getUniqueId());
    	}
    }
    public void updateBossBar(Player player, LivingEntity entity, Double damage){
    	if(players.containsKey(player.getUniqueId())){
    		BossBar bs = bossbars.get(players.get(player.getUniqueId()));
    		if((entity.getHealth()-damage)>0){
    			BossBar bs2 = Bukkit.getServer().createBossBar(entity.getType().toString(), BarColor.RED, BarStyle.SOLID);
    			bs2.setProgress((entity.getHealth()-damage)/entity.getMaxHealth());
    			for(Player p: bs.getPlayers()){
    				bs2.addPlayer(p);
    			}
    			removeBossBar(entity);
    			bossbars.put(entity.getUniqueId(), bs2);
    			players.put(player.getUniqueId(), entity.getUniqueId());
    		}else{
        		bs.hide();
    		    bossbars.remove(players.get(player.getUniqueId()));
        		players.remove(player.getUniqueId());
        		return;
    		}
    	}
    }
    public BossBar getBossBar(LivingEntity entity){
    	return bossbars.get(entity.getUniqueId());
    }
    public boolean hasPlayer(Player player){
    	for(BossBar bs : bossbars.values()){
    		for(Player player2: bs.getPlayers()){
    			if(player.getUniqueId()==player2.getUniqueId())
    				return true;
    		}
    	}
    	return false;
    }
    public LivingEntity getEntity(Player player){
    	for(Entity entity:player.getNearbyEntities(15, 15, 15)){
    		if(entity instanceof LivingEntity){
    			if(entity.getUniqueId()==players.get(player.getUniqueId())){
    				LivingEntity le = (LivingEntity)entity;
    				return le;
    			}
    		}
    	}
    	return null;
    }
    public void addBeacon(Player player, Location loc){
    	Bukkit.broadcastMessage(ChatColor.DARK_AQUA+player.getDisplayName()+"'s"+ChatColor.GREEN+" Beacon Has Dropped At "+loc.getBlockX()+" "+loc.getBlockZ()+" !");
    	beacons.put(player.getUniqueId(), loc);
    	beaconNames.put(player.getDisplayName(), player.getUniqueId());
    }
    public void mineBeacon(Player player, String player2, String uuid){
    	Bukkit.broadcastMessage(ChatColor.DARK_AQUA+player.getDisplayName()+ChatColor.GREEN+" Has Mined "+ChatColor.DARK_AQUA+player2+"'s"+ChatColor.GREEN+" Beacon!");
    	beacons.remove(uuid);
    	beaconNames.remove(player2);
    	if(collectedBeacons.containsKey(player.getUniqueId())){
    		collectedBeacons.get(player.getUniqueId()).add(UUID.fromString(uuid));
    	}else{
    		List<UUID> uuids = new ArrayList<UUID>();
    		uuids.add(UUID.fromString(uuid));
    		collectedBeacons.put(player.getUniqueId(), uuids);
    	}
    	//add beacon to beacon's inventory
    	//disable eneable save and load beacons
    }
    public boolean hasBeacons(Player player){
    	if(collectedBeacons.containsKey(player.getUniqueId()))
    		return true;
    	return false;
    }
    public List<UUID> getBeacons(Player player){
    	return collectedBeacons.get(player.getUniqueId());
    }
    public boolean isBeacon(Player player, Location loc){
    	if(beacons.containsKey(player.getUniqueId())){
    		if(beacons.get(player.getUniqueId()).equals(loc)){
    			return true;
    		}
    	}
    	return false;
    }
    public void addPlayer(Player p){
    	player.add(p.getUniqueId());
    }
    public boolean hasBeacon(Player p){
    	if(player.contains(p.getUniqueId()))
    		return true;
    	return false;
    }
    public static UltraHardcore getInstance(){
    	return instance;
    }
	
}
