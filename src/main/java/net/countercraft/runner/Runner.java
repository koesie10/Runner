package net.countercraft.runner;

//Java Imports
import java.io.File;

//Bukkit Imports
import org.bukkit.plugin.java.JavaPlugin;
import org.jivesoftware.smack.XMPPException;

//Vault Imports
import net.countercraft.runner.commands.RunnerCommandExecutor;

public class Runner extends JavaPlugin {
	private static Runner plugin;

	@Override
	public void onLoad() {
		super.onLoad();
		plugin = this;
	}

	public static Runner getInstance() {
		return plugin;
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
		
		System.out.println("[" + this.toString() + "] is now enabled.");

	}

	public String getVersion() {
		return this.getDescription().getVersion();
	}
	
	@Override
	public String toString() {
		return this.getDescription().getName();
	}

}
