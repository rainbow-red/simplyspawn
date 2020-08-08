package com.minetexas.simplyspawn;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.minetexas.simplyspawn.util.SSSettings;

public class TeleportUtils implements Listener {
	
	public static void randomSpawn(Player p) {
		p.teleport(randomLocation(p.getWorld()));
	}
	
	public static void spawn(Player player) {
		if (SSSettings.spawnCommandIsRandom && SSSettings.isValidWorld(player)) {
			randomSpawn(player);
			return;
		}
		
		// fix the position to the center of a block
		Location spawn = player.getWorld().getSpawnLocation();

		int modifier = SSSettings.spawnRadius;
		int x1 = Math.round(modifier * -1);
		int x2 = Math.round(modifier);
		int randomX = TeleportUtils.generateInt(x1, SSSettings.randomSpawnRadius + x2);
		int randomY = TeleportUtils.generateInt(x1, SSSettings.randomSpawnRadius + x2);
		spawn.setX(spawn.getBlockX() + randomX);
		spawn.setY(spawn.getBlockY());
		spawn.setZ(spawn.getBlockZ() + randomY);
		if (!player.isSleeping()) {
			player.teleport(spawn);
		} else {
			player.sendMessage(ChatColor.RED + "Get up you lazy bum!");
		}
	}
	
	public static Location randomLocation(World w) {
		double playerCount = w.getPlayers().size();
		double modifier = playerCount * SSSettings.spawnMultiplier;
		int x1 = (int) Math.round(modifier * -1);
		int x2 = (int) Math.round(modifier);
		int x = generateInt(x1, SSSettings.randomSpawnRadius + x2);
		int z = generateInt(x1, SSSettings.randomSpawnRadius + x2);
		int y = 0;
		
		Location loc = new Location(w, x, y, z);
		Chunk chunk = loc.getChunk();
		
		
		if(!chunk.isLoaded()) {
			chunk.load(true);
		}
		
		loc.setY(w.getHighestBlockYAt(loc));
		
		Block under = w.getBlockAt(loc);
		
		if(under.getType() == Material.LAVA || under.getType() == Material.LEGACY_STATIONARY_LAVA) {
			under.setType(Material.STONE);
			loc.setY(loc.getY() + 1);
		}
		
		return loc;
	}
	
	public static int generateInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}