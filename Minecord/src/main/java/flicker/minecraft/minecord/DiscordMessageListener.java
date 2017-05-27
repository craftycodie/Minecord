package flicker.minecraft.minecord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DiscordMessageListener extends ListenerAdapter
{	
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	if(event.getAuthor().getId().equals(Main.singleton.jda.getSelfUser().getId()))
    		return;
    	
    	if(event.isFromType(ChannelType.PRIVATE))
    	{
    		if(event.getMessage().getContent().equals("/list"))
    		{
	    		String returnMessage = "Online Players: ";
	    		
	    		for(Player player : Bukkit.getServer().getOnlinePlayers())
	    		{
	    			returnMessage = returnMessage + player.getPlayer().getName() + " ";
	    		}
	    		event.getChannel().sendMessage(returnMessage).complete();
    		}
    	}
    	else if(event.getTextChannel().getName().equals(Main.singleton.config.getString("DiscordGuildChannel")))
    	{
    		if(event.getMessage().getContent().equals("/list"))
    		{
	    		String returnMessage = "Online Players: ";
	    		
	    		for(Player player : Bukkit.getServer().getOnlinePlayers())
	    		{
	    			returnMessage = returnMessage + player.getPlayer().getName() + " ";
	    		}
	    		event.getChannel().deleteMessageById(event.getMessageIdLong()).complete();
	    		event.getAuthor().openPrivateChannel().complete().sendMessage(returnMessage).complete();
    		}
    		
    		String prefix = Main.singleton.config.getString("RolePrefixes.Everyone");
    		
    		for(Role role : event.getMember().getRoles())
    		{    		
    			if(Main.singleton.config.getString("RolePrefixes." + role.getName()) != null)
    			{
					prefix = Main.singleton.config.getString("RolePrefixes." + role.getName());
					break;
    			}
    		}
    		
    		event.getChannel().deleteMessageById(event.getMessageIdLong()).complete();
    		
    		if(event.getMessage().getContent().length() > 256)
    		{
    			event.getAuthor().openPrivateChannel().complete().sendMessage("The message you just tried to send it over 256 characters! You sent:").complete();
    			event.getAuthor().openPrivateChannel().complete().sendMessage(event.getMessage().getContent()).complete();
    		}
    		else
    		{
    			String nicknamePrefix = "";
    			if(event.getMember().getNickname() != null)
    				nicknamePrefix = "*";
    			
        		Bukkit.broadcastMessage("§9<" + prefix + nicknamePrefix + event.getMember().getEffectiveName() + "> " + event.getMessage().getContent());
    			event.getChannel().sendMessage("<" + ChatColor.stripColor(event.getMember().getEffectiveName()) + "> " + event.getMessage().getContent()).complete();
    		}
    	}
    }
}