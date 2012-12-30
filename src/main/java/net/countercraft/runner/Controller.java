package net.countercraft.runner;

//Java Imports
import java.io.File;

//Local Imports
import net.countercraft.runner.config.Config;
import net.countercraft.runner.managers.EventManager;
import net.countercraft.runner.managers.FileManager;
import net.countercraft.runner.managers.XMPPManager;

//Bukkit Imports
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

//Vault Imports
import net.milkbowl.vault.permission.Permission;

public class Controller {

	public static Runner getPluginInstance() {
		return Runner.getInstance();
	}

	public static Server getPluginServer() {
		return getPluginInstance().getServer();
	}

	public static PluginManager getPluginManager() {
		return getPluginInstance().getServer().getPluginManager();
	}

	public static File getDataFolder() {
		return getPluginInstance().getDataFolder();
	}

	public static FileManager getFileManager() {
		return FileManager.getInstance();
	}

	public static Config getConfig() {
		return Config.getInstance();
	}

	public static EventManager getEventManager() {
		return EventManager.getInstance();
	}

	public static Settings getSettings() {
		return Settings.getInstance();
	}

	public static XMPPManager getXMPPManager() {
		return XMPPManager.getInstance();
	}

	public static void sendCommandAsConsole(String command) {
		getPluginServer().dispatchCommand(getPluginServer().getConsoleSender(),
				command);
	}

	public static boolean sendCommandAsConsoleWithResult(String command) {
		return getPluginServer().dispatchCommand(
				getPluginServer().getConsoleSender(), command);
	}
	
	public static boolean isVaultInstalled() {
		return Runner.isVaultInstalled();
	}
	
	public static Permission getPermission() {
		return Runner.getPermission();
	}
}
