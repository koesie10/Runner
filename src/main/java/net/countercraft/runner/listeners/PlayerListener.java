package net.countercraft.runner.listeners;

//Local Imports
import net.countercraft.runner.Controller;

//Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener{
	
    public PlayerListener() {
       Controller.getPluginManager().registerEvents(this, Controller.getPluginInstance());
    }   
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Controller.getXMPPManager().sendAll("[" + event.getPlayer().getDisplayName() + "] : " + event.getMessage());        
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Controller.getXMPPManager().sendAll(ChatColor.stripColor(event.getJoinMessage()));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Controller.getXMPPManager().sendAll(ChatColor.stripColor(event.getQuitMessage()));
	}  
}
