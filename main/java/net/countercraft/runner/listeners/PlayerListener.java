
package net.countercraft.runner.listeners;

//Local Imports
import net.countercraft.runner.Controller;

//Bukkit Imports
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener{

    
    public PlayerListener() {
       Controller.getPluginManager().registerEvents(this, Controller.getPluginInstance());
    }   
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Controller.getXMPPManager().sendAll("[" + event.getPlayer().getDisplayName() + "] : " + event.getMessage());
        
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Controller.getXMPPManager().sendAll(event.getPlayer().getDisplayName() + "joined the game");
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Controller.getXMPPManager().sendAll(event.getPlayer().getDisplayName() + "left the game");
	}

    
    
                                  
    
}
