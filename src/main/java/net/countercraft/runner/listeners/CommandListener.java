package net.countercraft.runner.listeners;

//Local Imports
import net.countercraft.runner.Controller;

//Bukkit Imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class CommandListener implements Listener {

	public CommandListener() {
		Controller.getPluginManager().registerEvents(this,
				Controller.getPluginInstance());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("basic")){ // If the player typed /basic then do the following...
			// doSomething
			return true;
		} //If this has happened the function will return true. 
	        // If this hasn't happened the a value of false will be returned.
		return false; 
	}

}
