package com.minetexas.simplyspawn;

//import java.util.ArrayList;

//import org.bukkit.Chunk;
import org.bukkit.Location;
//import org.bukkit.Material;
import org.bukkit.World;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
//import org.bukkit.event.entity.EntityEvent;
//import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
//import org.bukkit.event.world.ChunkLoadEvent;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.material.MaterialData;

import com.minetexas.simplyspawn.RespawnListener;
import com.minetexas.simplyspawn.TeleportUtils;

//import de.tr7zw.nbtapi.NBTEntity;

public class RespawnListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		if (player.getBedSpawnLocation() == null) {
			if (!world.getName().contains("nether") && !world.getName().contains("end")) {
				Location location = TeleportUtils.randomLocation(player.getWorld());
				event.setRespawnLocation(location);
			}
		}
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!e.getPlayer().hasPlayedBefore()) {
			TeleportUtils.randomSpawn(e.getPlayer());
		}
	}

//	@EventHandler(priority = EventPriority.HIGHEST)
//	public void onItemSpawnEvent(ItemSpawnEvent event) {
//		Item item = event.getEntity();
//		ItemStack itemStack = item.getItemStack();
//		if (!itemStack.getType().name().endsWith("SHULKER_BOX")) {
//			return;
//		}
//        NBTEntity nbtent = new NBTEntity(item);
//        int size = nbtent.toString().length();
//        if (size <= 100000) {
//        	return;
//        }
//        SSLog.debug("Spawn Size: " + size);
//        if (size >= 400000) {
//        	SSLog.debug("Canceled item spawn event with size: " + size + " at " + event.getLocation());
//        	event.setCancelled(true);
//        }
//	}
//
//	@EventHandler(priority = EventPriority.HIGHEST)
//	public void onItemLoadEvent(ChunkLoadEvent event) {
//		Chunk chunk = event.getChunk();
//		ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
//
//		for (Entity entity : chunk.getEntities()) {
//			if ((entity instanceof Item == false)) {
//				continue;
//			}
//			Item item = (Item) entity;
//			
//		
//		ItemStack itemStack = item.getItemStack();
//		
//		if (!itemStack.getType().name().endsWith("SHULKER_BOX")) {
//			continue;
//		}
//
//        NBTEntity nbtent = new NBTEntity(item);
//        int size = nbtent.toString().length();
//        if (size <= 100000) {
//        	continue;
//        }
//        SSLog.debug("Load Size: " + size);
//        if (size >= 400000) {
//        	SSLog.debug("Canceling item load event with size: " + size);
//        	itemList.add(itemStack);
//        }
//		}
//		for (ItemStack itemStack : itemList) {
//			itemStack.setType(Material.AIR);
//		}
//	}
}
