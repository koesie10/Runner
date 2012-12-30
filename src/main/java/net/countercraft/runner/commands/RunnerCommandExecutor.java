package net.countercraft.runner.commands;

import java.io.File;

import net.countercraft.runner.Controller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RunnerCommandExecutor implements CommandExecutor {
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Controller.getXMPPManager().sendAll("Runner will be reloaded");
		File pluginDirectory = Controller.getDataFolder();

		// Config Setup
		Controller.getFileManager().makeIfNotExistFolder(pluginDirectory);

		Controller.getConfig().loadConfig();
		
		Controller.getXMPPManager().connect();
		
		sender.sendMessage(ChatColor.GOLD + "Reloaded Runner");
		return true;
	}
	
}
