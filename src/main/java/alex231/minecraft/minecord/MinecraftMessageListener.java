package alex231.minecraft.minecord;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.ess3.api.IEssentials;
import com.earth2me.essentials.User;
import java.util.ArrayList;
import java.util.Arrays;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MinecraftMessageListener implements Listener {

    IEssentials ess = (IEssentials)Bukkit.getPluginManager().getPlugin("Essentials");
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        User user = new User(event.getPlayer(), ess);
	MinecordPlugin.getInstance().chatChannel.sendMessage("<" + user.getGroup() + ">**" + ChatColor.stripColor(event.getPlayer().getDisplayName()) + "**> " +  ChatColor.stripColor(event.getMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	if(event.getPlayer().hasPlayedBefore())
            MinecordPlugin.getInstance().chatChannel.sendMessage("*" + ChatColor.stripColor(event.getJoinMessage() + "*")).complete();
    	else
            MinecordPlugin.getInstance().chatChannel.sendMessage("**Welcome " + event.getPlayer().getDisplayName() + " to the server!**").complete();
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
    	MinecordPlugin.getInstance().chatChannel.sendMessage("*" + ChatColor.stripColor(event.getQuitMessage()) + "*").complete();
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
    	MinecordPlugin.getInstance().chatChannel.sendMessage("*" + ChatColor.stripColor(event.getLeaveMessage()) + "*").complete();
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
    	MinecordPlugin.getInstance().chatChannel.sendMessage("*" + ChatColor.stripColor(event.getDeathMessage()) + "*").complete();
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
    
    @EventHandler
    public void PlayerCommand(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        
        String commandLabel = event.getMessage().split(" ")[0].replace("/", "");
        ArrayList<String> commandArguments = new ArrayList<>(Arrays.asList(event.getMessage().split(" ")));
        commandArguments.remove(0);
        if(commandArguments.size() < 1)
            return;
        
        String message = String.join(" ", commandArguments);
        String discordMessage = "";
        
        switch(commandLabel)
        {
            case "me":
            case "minecraft:me":
            case "action":
                discordMessage = "> **" +  ChatColor.stripColor(p.getDisplayName()) + "** " + ChatColor.stripColor(message) + " <";
                break;
            case "say":
            case "minecraft:say":
                discordMessage = "**[" +  ChatColor.stripColor(p.getDisplayName()) + "] " + ChatColor.stripColor(message) + "**";
                break;
            case "shout":
            case "broadcast":
                discordMessage = "**[Broadcast] " + ChatColor.stripColor(message) + "**";
                break;
            default:
                return;
        }
        
        if(discordMessage.equals(""))
            return;
        
        MinecordPlugin.getInstance().chatChannel.sendMessage(discordMessage).complete();
        
    }
	
}
