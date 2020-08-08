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
}
