package com.minetexas.simplyspawn;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.bukkit.plugin.java.JavaPlugin;

public class SSLog {

	public static JavaPlugin plugin;
	private static Logger cleanupLogger;
	
	public static void init(JavaPlugin plugin) {
		SSLog.plugin = plugin;
		
		cleanupLogger = Logger.getLogger("cleanUp");
		FileHandler fh;
		
		try {
			fh = new FileHandler("cleanUp.log");
			cleanupLogger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void heading(String title) {
		plugin.getLogger().info("========= "+title+" =========");
	}
	
	public static void info(String message) {
		plugin.getLogger().info(message);
	}
	
	public static void debug(String message) {
		plugin.getLogger().info("[DEBUG] "+message);
	}

	public static void warning(String message) {
		if (message == null) {
			try {
				throw new Exception();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		plugin.getLogger().info("[WARNING] "+message);
	}

	public static void error(String message) {
		plugin.getLogger().severe(message);
	}
	
	public static void adminlog(String name, String message) {
		plugin.getLogger().info("[ADMIN:"+name+"] "+message);
	}
	
	public static void cleanupLog(String message) {
		info(message);
		cleanupLogger.info(message);		
	}
	
	public static void exception(String string, Exception e) {
		e.printStackTrace();		
	}
}