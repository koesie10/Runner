package net.countercraft.runner.config;

//Java Import
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//Local Imports
import net.countercraft.runner.Controller;

//Snakeyaml Imports
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Config {
	private Map<String, Object> dataChain = new HashMap<String, Object>();

	private Config() {
		initMap();
	}

	public static Config getInstance() {
		return ConfigDefaultHolder.INSTANCE;
	}

	private static class ConfigDefaultHolder {

		private static final Config INSTANCE = new Config();
	}

	public Map<String, Object> getDataChain() {
		return dataChain;
	}

	public void setDataChain(Map<String, Object> dataChain) {
		this.dataChain = dataChain;
	}

	private void initMap() {
		// Username
		String username = "example@gmail.com";
		dataChain.put("username", username);

		// Password
		String password = "YourPassword";
		dataChain.put("password", password);

		// Admin List
		HashMap<String, String> adminList = new HashMap<String, String>();
		adminList.put("admin@gmail.com", "Admin");
		adminList.put("webadmin@countercraft.net", "Webadmin");
		adminList.put("alex@countercraft.net", "alex");
		dataChain.put("admins", adminList);

		// Open Channel Mode
		boolean openChannel = false;
		dataChain.put("openChannel", openChannel);

		// User List
		HashMap<String, String> userList = new HashMap<String, String>();
		userList.put("normalGuy@gmail.com", "Normal guy");
		userList.put("test@googleAppServices.com", "Test");
		dataChain.put("users", userList);

		// Admin Transmissions
		boolean adminInfo = false;
		dataChain.put("admin_info", adminInfo);

		// Prefix
		String chat_prefix = "[ Runner Internet Chat ]";
		dataChain.put("prefix", chat_prefix);
	}

	public void loadConfig() {

		File config = new File(Controller.getDataFolder(), "config.yml");
		boolean exists = Controller.getFileManager().makeIfNotExistFile(config);
		Map informationRoot;

		if (!exists) {
			createConfig(config);
			informationRoot = dataChain;
			System.out
					.println("["
							+ Controller.getName()
							+ "] You can now change the config file and execute /reloadrunner when finished.");
		} else {
			informationRoot = parseFile(config);
		}

		Controller.getSettings().USERNAME = (String) informationRoot
				.get("username");
		Controller.getSettings().PASSWORD = (String) informationRoot
				.get("password");
		Controller.getSettings().OPEN_CHANNEL = (Boolean) informationRoot
				.get("openChannel");
		Controller.getSettings().ADMIN_LIST = (HashMap<String, String>) informationRoot
				.get("admins");
		Controller.getSettings().USER_LIST = (HashMap<String, String>) informationRoot
				.get("users");
		Controller.getSettings().ADMIN_INFO_ENABLED = (Boolean) informationRoot
				.get("admin_info");
		Controller.getSettings().CHAT_PREFIX = (String) informationRoot
				.get("prefix");
		if (Controller.getSettings().CHAT_PREFIX == null || Controller.getSettings().CHAT_PREFIX == "") {
			Controller.getSettings().CHAT_PREFIX = "[ Runner Internet Chat ]";
		}
	}

	private void createConfig(File config) {
		Yaml yaml = new Yaml();
		FileWriter fstream = null;

		try {
			fstream = new FileWriter(config);
		} catch (IOException ex) {
			Logger.getLogger(Config.class.getName())
					.log(Level.SEVERE, null, ex);
		}

		BufferedWriter out = new BufferedWriter(fstream);
		yaml.dump(dataChain, out);

		try {
			fstream.close();
		} catch (IOException ex) {
			Logger.getLogger(Config.class.getName())
					.log(Level.SEVERE, null, ex);
		}
	}

	private Map parseFile(File config) {
		Yaml yaml = new Yaml();
		Reader reader = null;
		try {
			reader = new FileReader(config);
			Map<String, Object> map = new HashMap<String, Object>();
			map = (Map) yaml.load(reader);
			return map;
		} catch (FileNotFoundException fnfe) {
		} finally {

			if (null != reader) {
				try {
					reader.close();
				} catch (IOException ioe) {
				}
			}
		}

		return null;
	}
}
