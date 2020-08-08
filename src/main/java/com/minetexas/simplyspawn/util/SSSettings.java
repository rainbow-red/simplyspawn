package com.minetexas.simplyspawn.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.minetexas.simplyspawn.SSLog;
import com.minetexas.simplyspawn.SimplySpawn;
import com.minetexas.simplyspawn.exception.InvalidConfiguration;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SSSettings {

	public static SimplySpawn plugin;
	public static final String GO_TO_BED = "rssetspawn.gotobed";
	public static final String CHECK_BED = "rssetspawn.bed";
	public static final String GET_AND_GO_TO_BED = "rssetspawn.getandgotobed";
	public static final String GET_BED = "rssetspawn.getbed";
	public static final String GO_TO_SPAWN = "rssetspawn.spawn";
	public static final String GO_TO_RANDOM_SPAWN = "rssetspawn.rspawn";
	public static final String SET_SPAWN = "rssetspawn.setspawn";
	public static final String RELOAD = "rssetspawn.reload";
	
	public static int spawnRadius = 500;
	public static int randomSpawnRadius = 16;
	public static List<String> enabledWorlds;
	public static boolean spawnCommandIsRandom = false;
	public static int spawnMultiplier = 1;
		
	public static FileConfiguration spawnConfig; /* config.yml */
	
	public static void init(SimplySpawn plugin) throws IOException, InvalidConfigurationException, InvalidConfiguration {
		SSSettings.plugin = plugin;

		loadConfigFiles();
	}
	
	public static void reloadConfigFile() throws IOException, InvalidConfigurationException, InvalidConfiguration
	{
		loadConfigFiles();
	}
	
	private static void loadConfigFiles() throws IOException, InvalidConfigurationException {
		spawnConfig = loadConfig("config.yml");
		enabledWorlds = spawnConfig.getStringList("enabledWorlds");
		spawnCommandIsRandom = spawnConfig.getBoolean("spawnCommandIsRandom");
		spawnMultiplier = Math.max(0, spawnConfig.getInt("spawnMultiplier"));
		spawnRadius = Math.max(0, spawnConfig.getInt("spawnRadius"));
		randomSpawnRadius = Math.max(0, spawnConfig.getInt("randomSpawnRadius"));
	}
	
	public static FileConfiguration loadConfig(String filepath) throws IOException, InvalidConfigurationException {

		File file = new File(plugin.getDataFolder().getPath()+"/"+filepath);
		if (!file.exists()) {
			SSLog.warning("Configuration file: '"+filepath+"' was missing. Streaming to disk from Jar.");
			streamResourceToDisk("/"+filepath);
		}
		
		SSLog.info("Loading Configuration file: "+filepath);
		// read the config.yml into memory
		YamlConfiguration cfg = new YamlConfiguration(); 
		cfg.load(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		return cfg;
	}
	
	public static void streamResourceToDisk(String filepath) throws IOException {
		URL inputUrl = plugin.getClass().getResource(filepath);
		InputStream input = plugin.getResource(filepath);
		Path dest = Paths.get(plugin.getDataFolder().getPath() + filepath);
		Files.copy(input, dest, REPLACE_EXISTING, COPY_ATTRIBUTES);
	}
	
	public static boolean isValidWorld(Player player) {
		for (String world : SSSettings.enabledWorlds) {
			if (player.getWorld().getName().equalsIgnoreCase(world)) {
				return true;
			}
		}
		return false;
	}
}
