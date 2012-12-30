package net.countercraft.runner.listeners;

//Java imports
import java.util.concurrent.Callable;

//Local Imports
import net.countercraft.runner.Controller;

//Bukkit imports
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
		if(event.isAsynchronous()) {
			Controller.getPluginInstance().getServer().getScheduler().callSyncMethod(Controller.getPluginInstance(), new SendMessageSync(event));
		} else {
			Controller.getXMPPManager().sendAll("[" + event.getPlayer().getDisplayName() + "] : " + event.getMessage());     
		}   
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Controller.getXMPPManager().sendAll(ChatColor.stripColor(event.getJoinMessage()));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Controller.getXMPPManager().sendAll(ChatColor.stripColor(event.getQuitMessage()));
	}  
	
	public class SendMessageSync implements Callable<Object> {

		AsyncPlayerChatEvent event;
		
		public SendMessageSync(AsyncPlayerChatEvent event) {
			this.event = event;
		}
		@Override
		public Object call() throws Exception {
			Controller.getXMPPManager().sendAll("[" + event.getPlayer().getDisplayName() + "] : " + event.getMessage());     
			return null;
		}
		
	}
}
