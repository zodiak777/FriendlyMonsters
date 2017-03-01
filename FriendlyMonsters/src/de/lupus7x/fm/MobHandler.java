package de.lupus7x.fm;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MobHandler implements Listener{
	FM plugin;
	
	public MobHandler(FM plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerRightclickMonster(PlayerInteractEntityEvent e){
		ItemStack stack = e.getPlayer().getInventory().getItemInHand();
		
		if(e.getRightClicked().equals(EntityType.ZOMBIE)){
			
		}
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		for(int i = 0; i < this.plugin.mobList.length; i++){
			if(e.getEntityType().equals(this.plugin.typeList[i])){
				if(this.plugin.config.getBoolean("Mobs.friendly." + this.plugin.mobList[i])){
					e.setCancelled(true);
				} else if (!this.plugin.config.getBoolean("Mobs.friendly." + this.plugin.mobList[i])) {
					e.setCancelled(false);
				}
			}
		}
	}
	
	@EventHandler
	public void onMobBurning(EntityCombustEvent e) {
		if(!this.plugin.config.getBoolean("Mobs.burn.zombie") && e.getEntityType().equals(EntityType.ZOMBIE)) {
			e.setCancelled(true);
		} else if(this.plugin.config.getBoolean("Mobs.burn.zombie") && e.getEntityType().equals(EntityType.ZOMBIE)) {
			e.setCancelled(false);
		}
		if(!this.plugin.config.getBoolean("Mobs.burn.skeleton") && e.getEntityType().equals(EntityType.SKELETON)) {
			e.setCancelled(true);
		} else if(this.plugin.config.getBoolean("Mobs.burn.skeleton") && e.getEntityType().equals(EntityType.SKELETON)) {
			e.setCancelled(false);
		}
	}
	
	@EventHandler
	public void CreeperPrimeExplosion(ExplosionPrimeEvent e) {
		if(!this.plugin.config.getBoolean("Mobs.creeper_explosion") && e.getEntityType() == EntityType.CREEPER) {
			e.setCancelled(true);
		} else if(this.plugin.config.getBoolean("Mobs.creeper_explosion") && e.getEntityType() == EntityType.CREEPER) {
			e.setCancelled(false);
		}
	}
	
	@EventHandler
	public void CreeperExplosion(EntityExplodeEvent e) {
		if(!this.plugin.config.getBoolean("Mobs.creeper_explosion") && e.getEntityType() == EntityType.CREEPER) {
			e.setCancelled(true);
		} else if(this.plugin.config.getBoolean("Mobs.creeper_explosion") && e.getEntityType() == EntityType.CREEPER) {
			e.setCancelled(false);
		}
	}
}
