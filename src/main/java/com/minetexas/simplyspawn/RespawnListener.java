package com.minetexas.simplyspawn;

//import org.bukkit.attribute.Attribute;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		if (player.getBedSpawnLocation() == null) {
		event.setRespawnLocation(randomSpawn(player));
		}

    }
	
	public Location randomSpawn(Player player) {
		
		Location spawn = player.getWorld().getSpawnLocation();
		Random random = new Random();
		int randomX =(random.nextInt(16)-8);
		int randomY=(random.nextInt(16)-8);
		spawn.setX(spawn.getBlockX() + randomX);
		spawn.setY(spawn.getBlockY());
		spawn.setZ(spawn.getBlockZ() + randomY);
		return spawn;
	}
}
