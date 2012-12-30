package net.countercraft.runner;

//Java Imports
import java.io.File;

//Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jivesoftware.smack.XMPPException;

//Vault Imports
import net.countercraft.runner.commands.RunnerCommandExecutor;
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
		try {
			Controller.getXMPPManager().close();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[" + this.toString() + "] is now disabled!");
	}

	public void onEnable() {

		File pluginDirectory = Controller.getDataFolder();

		// Config Setup
		Controller.getFileManager().makeIfNotExistFolder(pluginDirectory);

		Controller.getConfig().loadConfig();

		Controller.getEventManager().registerEvents();
		try {
			Controller.getXMPPManager().connect();
		} catch (Exception e) {
			// Do nothing here
		}
		
		getCommand("reloadrunner").setExecutor(new RunnerCommandExecutor());

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
		System.out.println("[" + this.toString() + "] is now enabled.");

	}

	public String getVersion() {
		return this.getDescription().getVersion();
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
	
	@Override
	public String toString() {
		return this.getDescription().getName();
	}

}
