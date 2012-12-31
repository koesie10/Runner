package net.countercraft.runner;

//Java Imports
import java.util.HashMap;

//Bukkit Imports
import org.bukkit.World;

public class Settings {
	public boolean OPEN_CHANNEL;
	public String USERNAME;
	public String PASSWORD;
	public HashMap<String, String> ADMIN_LIST;
	public HashMap<String, String> USER_LIST;
	private HashMap<String, String> ADMIN_WORLD_LIST;
	public boolean ADMIN_INFO_ENABLED;

	private Settings() {
	}

	public static Settings getInstance() {
		return SettingsHolder.INSTANCE;
	}

	public boolean isAdmin(String name) {
		return ADMIN_LIST.containsKey(name);
	}

	public World getWorldForAdmin(String name) {
		String worldName = ADMIN_WORLD_LIST.get(name);

		if (worldName != null) {
			return Controller.getPluginInstance().getServer().getWorld(name);
		} else {
			return Controller.getPluginInstance().getServer().getWorlds()
					.get(0);
		}
	}

	public boolean isUser(String name) {
		if (isAdmin(name)) {
			return true;
		} else {
			return USER_LIST.containsKey(name);
		}
	}

	private static class SettingsHolder {

		private static final Settings INSTANCE = new Settings();
	}

	// Command Syntax
	public String WEATHER_SYNTAX_EXAMPLE = "!weather [storm/sun] {world name}";
	public String TIME_SYNTAX_EXAMPLE = "!time [1000/sun/midnight/noon/12500t/2342] {world name}";

}
