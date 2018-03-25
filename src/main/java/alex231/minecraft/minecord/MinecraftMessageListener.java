package alex231.minecraft.minecord;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.ess3.api.events.AfkStatusChangeEvent;

public class MinecraftMessageListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
	MinecordPlugin.getInstance().chatChannel.sendMessage("<**" + ChatColor.stripColor(event.getPlayer().getDisplayName()) + "**> `" +  ChatColor.stripColor(event.getMessage()) + "`").complete();
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	if(event.getPlayer().hasPlayedBefore())
            MinecordPlugin.getInstance().chatChannel.sendMessage("**" + ChatColor.stripColor(event.getJoinMessage() + "**")).complete();
    	else
            MinecordPlugin.getInstance().chatChannel.sendMessage("**Welcome " + event.getPlayer().getDisplayName() + " to the server!**").complete();
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
    	MinecordPlugin.getInstance().chatChannel.sendMessage("**" + ChatColor.stripColor(event.getQuitMessage()) + "**").complete();
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
    	MinecordPlugin.getInstance().chatChannel.sendMessage("**" + ChatColor.stripColor(event.getLeaveMessage()) + "**").complete();
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
    	MinecordPlugin.getInstance().chatChannel.sendMessage("**" + ChatColor.stripColor(event.getDeathMessage()) + "**").complete();
    }
    
    @EventHandler
    public void onAfkChange(AfkStatusChangeEvent event)
    {
        String afkMessage = "AFK";
        if(event.getValue())
        	afkMessage = "is now " + afkMessage;
        else
        {
    		//If the player just joined, no longer AFK state is not necessary.
    		if(MinecordPlugin.getInstance().chatChannel.getMessageById(MinecordPlugin.getInstance().chatChannel.getLatestMessageId()).complete().getContentRaw().equals("<" + event.getAffected().getName() + "> joined the game"))
    			return;
    		
    		if(!MinecordPlugin.getInstance().getServer().getPlayer(event.getAffected().getName()).isOnline())
    			return;
        	
        	afkMessage = "is no longer " + afkMessage;
        }
        
        afkMessage = event.getAffected().getName() + " " + afkMessage;
		
        MinecordPlugin.getInstance().chatChannel.sendMessage("*" + afkMessage + "*").complete();
    }
	
}
