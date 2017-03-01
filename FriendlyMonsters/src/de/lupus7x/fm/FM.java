package de.lupus7x.fm;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class FM extends JavaPlugin {
	public FileConfiguration config = getConfig();
	
	public String[] mobList = {
			"blaze", "cave_spider", "creeper", "ender_dragon", "enderman", "endermite", "ghast", "giant", "guardian", 
			"magma_cube", "ocelot", "pig_zombie", "silverfish", "skeleton", "slime", "spier", "iron_golem", "witch", 
			"wolf", "wither", "wither_skull", "zombie"
	};
	public EntityType[] typeList = {
			EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, 
			EntityType.ENDERMITE, EntityType.GHAST, EntityType.GIANT, EntityType.GUARDIAN, EntityType.MAGMA_CUBE, 
			EntityType.OCELOT, EntityType.PIG_ZOMBIE, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, 
			EntityType.SPIDER, EntityType.IRON_GOLEM, EntityType.WITCH, EntityType.WOLF, EntityType.WITHER, 
			EntityType.WITHER_SKULL, EntityType.ZOMBIE
	};
	
	@Override
	public void onEnable() {
		new MobHandler(this);
		initConfig();
	}
	
	@Override
	public void onDisable() {
	}
	
	private void initConfig() {
		this.config.options().copyDefaults(true);
		this.saveConfig();
	}
	
	public List<String> toArrayList(String[] a){
		List<String> l = new ArrayList<>();
		for(int i = 0; i < a.length; i++){
			l.add(a[i]);
		}
		return l;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		if(sender.hasPermission("fm.admin")) {
			if(cmd.getName().equalsIgnoreCase("fm") && args.length > 0) {
				if (args[0].equalsIgnoreCase("unfriendly")) {
					try {
						if(args[1] != null){
							String List = "&4";
							for (int i = 1; i < mobList.length; i++){
								if(toArrayList(args).contains(mobList[i])){
									if(config.getBoolean("Mobs.friendly." + mobList[i])){
										List = List + " " + mobList[i];
										config.set("Mobs.friendly." + mobList[i], Boolean.valueOf(false));
										saveConfig();
									}
								}
							}
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + List + config.getString("Messages.friendly_disable")));
							return true;
						}
					} catch (ArrayIndexOutOfBoundsException e){
						String list = "";
						for(int i = 0; i < mobList.length; i++){
							list = list + " " + mobList[i];
						}
					}
				}
				if (args[0].equalsIgnoreCase("friendly")) {
					try{
						if(args[1] != null){
							String List = "&2";
							for (int i = 1; i < mobList.length; i++){
								if(toArrayList(args).contains(mobList[i])){
									if(!config.getBoolean("Mobs.friendly." + mobList[i])){
										List = List + " " + mobList[i];
										config.set("Mobs.friendly." + mobList[i], Boolean.valueOf(true));
										saveConfig();
									}
								}
							}
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + List + config.getString("Messages.friendly_enable")));
							return true;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						String list = "&2";
						for(int i = 0; i < mobList.length; i++){
							list = list + " " + mobList[i];
						}
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + config.getString("Messages.friendly_usage") + list));
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("explosion")) {
					if (!config.getBoolean("Mobs.creeper_explosion")) {
						config.set("Mobs.creeper_explosion", Boolean.valueOf(true));
						saveConfig();
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + config.getString("Messages.explosion_enable")));
					} else if (config.getBoolean("Mobs.creeper_explosion")) {
						config.set("Mobs.creeper_explosion", Boolean.valueOf(false));
						saveConfig();
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + config.getString("Messages.explosion_disable")));
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("burn")) {
					try {
						if (args[1].equalsIgnoreCase("zombie") || args[1].equalsIgnoreCase("skeleton")) {
							if (!config.getBoolean("Mobs.burn."+args[1])) {
								config.set("Mobs.burn."+args[1], Boolean.valueOf(true));
								saveConfig();
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + ChatColor.YELLOW + args[1] + config.getString("Messages.burn_enable")));
								return true;
							} else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + ChatColor.YELLOW + args[1] + config.getString("Messages.burn_already_disable")));
								return true;
							}
						}
					} catch (ArrayIndexOutOfBoundsException e){
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + ChatColor.YELLOW + args[1] + config.getString("Messages.burn_usage")));
							return true;
					}
				}
				if (args[0].equalsIgnoreCase("unburn")) {
					try {
						if (args[1].equalsIgnoreCase("zombie") || args[1].equalsIgnoreCase("skeleton")) {
							if (config.getBoolean("Mobs.burn."+args[1])) {
								config.set("Mobs.burn."+args[1], Boolean.valueOf(false));
								saveConfig();
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + ChatColor.YELLOW + args[1] + config.getString("Messages.burn_disable")));
								return true;
							} else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + ChatColor.YELLOW + args[1] + config.getString("Messages.burn_already_disable")));
								return true;
							}
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix") + config.getString("Messages.unburn_usage")));
						return true;
					}
				}
				player.performCommand("fm");
				return true;
			} else {
				if(cmd.getName().equalsIgnoreCase("fm")) {
					player.sendMessage(ChatColor.DARK_RED + "[FM]" + ChatColor.YELLOW + ": /fm friendly | Enable friendly Monsters");
					player.sendMessage(ChatColor.DARK_RED + "[FM]" + ChatColor.YELLOW + ": /fm unfriendly | Disable friendly Monsters");
					player.sendMessage(ChatColor.DARK_RED + "[FM]" + ChatColor.YELLOW + ": /fm explosion | Disable / Enable Creeper explosion");
					player.sendMessage(ChatColor.DARK_RED + "[FM]" + ChatColor.YELLOW + ": /fm burn | Enable Zombie and Skeleton burn in sun");
					player.sendMessage(ChatColor.DARK_RED + "[FM]" + ChatColor.YELLOW + ": /fm unburn | Disable Zombie and Skeleton burn in sun");
					return true;
				}
			}
		}
		return false;
	}
}
