/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.countercraft.runner.listeners;

import net.countercraft.runner.Controller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener{
   

    public WeatherListener() {
         Controller.getPluginManager().registerEvents(this, Controller.getPluginInstance());
    }
    
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        if(Controller.getSettings().ADMIN_INFO_ENABLED){
            if(event.toWeatherState()){
                Controller.getXMPPManager().sendToAdmins("It is now raining in World " + event.getWorld().getName());
            }else{
                Controller.getXMPPManager().sendToAdmins("It has stopped raining in World " + event.getWorld().getName());
            }
        }
    }
    
}
