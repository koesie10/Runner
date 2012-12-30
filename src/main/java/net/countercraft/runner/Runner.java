package net.countercraft.runner;

//Java Imports
import java.io.File;

//Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

//Vault Imports
import net.milkbowl.vault.permission.Permission;

public class Runner extends JavaPlugin {
	private static Runner plugin;
	private static Permission perms;
	private static boolean vaultInstalled;

	@Override
	public void onLoad() {
		super.onLoad();
		plugin = this;
	}

	public static Runner getInstance() {
		return plugin;
	}

	public static Permission getPermission() {
		return perms;
	}

	public void onDisable() {
		Controller
				.getXMPPManager()
				.sendAll(
						"Server is now shutting down. You will be disconnected from the chat.");
		System.out.println("[" + this.toString() + "] is now disabled!");
	}

	public void onEnable() {

		File pluginDirectory = Controller.getDataFolder();

		// Config Setup
		Controller.getFileManager().makeIfNotExistFolder(pluginDirectory);

		Controller.getConfig().loadConfig();

		Controller.getEventManager().registerEvents();

		Controller.getXMPPManager().connect();

		vaultInstalled = Bukkit.getPluginManager().getPlugin("Vault") != null;
		if (vaultInstalled) {
			setupPermissions();
		} else {
			if (Controller.getSettings().USE_ADMIN_NAMES) {
				System.out
						.println("["
								+ this.toString()
								+ "] You have enabled admin_names, but Vault is not installed. This setting will thus not work");
			}
		}
		System.out.println("[" + this.toString() + "] is Enabled.");

	}

	public String getVersion() {
		return "V 2.1 Release";
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = Bukkit.getServer()
				.getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public static boolean isVaultInstalled() {
		return vaultInstalled;
	}

}
