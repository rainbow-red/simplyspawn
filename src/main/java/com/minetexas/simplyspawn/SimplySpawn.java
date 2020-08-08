package com.minetexas.simplyspawn;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.minetexas.simplyspawn.RespawnListener;
import com.minetexas.simplyspawn.TeleportUtils;
import com.minetexas.simplyspawn.exception.InvalidConfiguration;
import com.minetexas.simplyspawn.util.SSSettings;


public class SimplySpawn extends JavaPlugin {
	
	@Override
	public void onEnable() {
		SSLog.init(this);
		SSLog.info("onEnable has been invoked!");
		final PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new RespawnListener(), this);
		try {
			SSSettings.init(this);
		} catch (IOException | InvalidConfigurationException | InvalidConfiguration e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		SSLog.info("onDisable has been invoked!");
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
    		if (!player.hasPermission(SSSettings.SET_SPAWN)){
    			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
    			return true;
    		}
    		Location spawn = player.getLocation();
    		player.getWorld().setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
			player.sendMessage(ChatColor.RED + "Spawn set");
    	} else if (command.getName().equalsIgnoreCase("rs_spawn")) {
    		if (!player.hasPermission(SSSettings.GO_TO_SPAWN)){
    			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
    			return true;
    		}
    		TeleportUtils.spawn(player);
    	} else if (command.getName().equalsIgnoreCase("rs_rspawn")) {
    		if (!player.hasPermission(SSSettings.GO_TO_RANDOM_SPAWN) || !SSSettings.isValidWorld(player) ){
    			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
    			return true;
    		}
			TeleportUtils.randomSpawn(player);
    	} else if (command.getName().equalsIgnoreCase("bed")) {
    		if (!player.hasPermission(SSSettings.CHECK_BED)){
    			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
    			return true;
    		}
    		this.goToBed(player);
    	} else if (command.getName().equalsIgnoreCase("getbed")) {
    		if (!player.hasPermission(SSSettings.GET_BED) || args.length == 0) {
    			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
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
    		
    	} else if (command.getName().equalsIgnoreCase("ssreload")) {
    		if (!player.hasPermission(SSSettings.RELOAD)){
    			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
    			return true;
    		}
    		try {
				SSSettings.reloadConfigFile();
			} catch (IOException | InvalidConfigurationException | InvalidConfiguration e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}  else
    		return false;  // unknown command, should not happen
    	return true;
    }
	
	public void getBed(Player player, OfflinePlayer target) {
		Location spawn = target.getBedSpawnLocation();
		if(spawn != null) { //check if bed location even exists
			player.sendMessage(ChatColor.RED + "Bed Location xyz: "+spawn.getBlockX()+" "+spawn.getBlockY()+" "+spawn.getBlockZ());
			if (player.hasPermission(SSSettings.GET_AND_GO_TO_BED)){ 
    			player.teleport(spawn);
    		}
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
}
