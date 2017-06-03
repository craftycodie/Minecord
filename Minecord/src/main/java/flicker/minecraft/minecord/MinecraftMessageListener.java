package flicker.minecraft.minecord;

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
		Main.singleton.guildChannel.sendMessage("<" + ChatColor.stripColor(event.getPlayer().getDisplayName()) + "> " + ChatColor.stripColor(event.getMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	if(event.getPlayer().hasPlayedBefore())
    		Main.singleton.guildChannel.sendMessage(ChatColor.stripColor(event.getJoinMessage())).complete();
    	else
    		Main.singleton.guildChannel.sendMessage("Welcome **" + event.getPlayer().getDisplayName() + "** to the server!").complete();
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
		Main.singleton.guildChannel.sendMessage(ChatColor.stripColor(event.getQuitMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
    	Main.singleton.guildChannel.sendMessage(ChatColor.stripColor(event.getLeaveMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
		Main.singleton.guildChannel.sendMessage(ChatColor.stripColor(event.getDeathMessage())).complete();
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
    		if(Main.singleton.guildChannel.getMessageById(Main.singleton.guildChannel.getLatestMessageId()).complete().getContent().equals("<" + event.getAffected().getName() + "> joined the game"))
    			return;
    		
    		if(!Main.singleton.getServer().getPlayer(event.getAffected().getName()).isOnline())
    			return;
        	
        	afkMessage = "is no longer " + afkMessage;
        }
        
        afkMessage = event.getAffected().getName() + " " + afkMessage;
		
        Main.singleton.guildChannel.sendMessage(afkMessage).complete();
		//channel.sendMessage(event.getValue())
    }
	
}
