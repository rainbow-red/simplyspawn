package com.minetexas.simplyspawn;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimplySpawn extends JavaPlugin {
	@Override
	public void onEnable() {
		this.getLogger().info("Enabled");
		final PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new RespawnListener(), this);
	}
	@Override
	public void onDisable() {
		this.getLogger().info("Disable");
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
    	// both commands need a player to be the sender to get the location!
    	if (!(sender instanceof Player)) {
    		sender.sendMessage(ChatColor.RED + "This command has to be called by a player!");
    		return true;
    	}
    	Player player = (Player)sender;

    	if (command.getName().equalsIgnoreCase("rs_setspawn")) {
    		if (!player.hasPermission("rssetspawn.setspawn")){
    			player.sendMessage(ChatColor.RED + "You don't have a permission to do that.");
    			return true;
    		}
    		Location spawn = player.getLocation();
    		player.getWorld().setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
			player.sendMessage(ChatColor.RED + "Spawn set");
    	} else if (command.getName().equalsIgnoreCase("rs_spawn")) {
    		if (!player.hasPermission("rssetspawn.spawn")){
    			player.sendMessage(ChatColor.RED + "You don't have a permission to do that.");
    			return true;
    		}
    		this.randomSpawn(player);
    	} else if (command.getName().equalsIgnoreCase("bed")) {
    		if (!player.hasPermission("rssetspawn.bed")){
    			player.sendMessage(ChatColor.RED + "You don't have a permission to do that.");
    			return true;
    		}
    		this.goToBed(player);
    	} else if (command.getName().equalsIgnoreCase("getbed")) {
    		if (!player.hasPermission("rssetspawn.getbed") || args.length == 0) {
    			player.sendMessage(ChatColor.WHITE + "Unknown Command. Don't make me destroy you.");
    			return true;
    		}
    		String str = args[0];
    		Player target = this.getServer().getPlayer(str);
    		if (target != null) {
    			this.getBed(player, target);
    			return true;
    		}
    		OfflinePlayer offlineTarget = this.getServer().getOfflinePlayer(str);
    		if (offlineTarget != null) {
    			this.getBed(player, offlineTarget);
    			return true;
    		}
    			//no bed exists
    			player.sendMessage(ChatColor.RED +"Player '"+ str +"' doesn't exist.");
    		
    	} else
    		return false;  // unknown command, should not happen
    	return true;
    }
	
	public void getBed(Player player, OfflinePlayer target) {
		Location spawn = target.getBedSpawnLocation();
		if(spawn != null) { //check if bed location even exists
			player.sendMessage(ChatColor.RED + "Bed Location xyz: "+spawn.getBlockX()+" "+spawn.getBlockY()+" "+spawn.getBlockZ());
		} else {
			//no bed exists
			player.sendMessage(ChatColor.RED +"Player '"+ target.getName() +"' doesn't have a bed.");
		}
	}
	
	public void goToBed(Player player) {
		Location spawn = player.getBedSpawnLocation();
		if(spawn != null) { //check if bed location even exists
			if (player.hasPermission("rssetspawn.bed")){
				player.sendMessage(ChatColor.RED + "Bed Location xyz: "+spawn.getBlockX()+" "+spawn.getBlockY()+" "+spawn.getBlockZ());
			}

    		if (player.hasPermission("rssetspawn.gotobed")){ 

    			player.teleport(spawn);
    		}
		} else {
			player.sendMessage(ChatColor.RED + "You don't have a bed!");
		}
	}
	
	public void randomSpawn(Player player) {
		// fix the position to the center of a block
		Location spawn = player.getWorld().getSpawnLocation();
		Random random = new Random();
		int randomX =(random.nextInt(16)-8);
		int randomY=(random.nextInt(16)-8);
		spawn.setX(spawn.getBlockX() + randomX);
		spawn.setY(spawn.getBlockY());
		spawn.setZ(spawn.getBlockZ() + randomY);
		if (!player.isSleeping()) {
		player.teleport(spawn);
		} else {
			player.sendMessage(ChatColor.RED + "Get up you lazy bum!");
		}
	}
}
