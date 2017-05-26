package flicker.minecraft.minecord;

import org.bukkit.Bukkit;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DiscordMessageListener extends ListenerAdapter
{	
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	if(event.getAuthor().getId().equals(Main.singleton.jda.getSelfUser().getId()))
    		return;
    	
    	if(event.getTextChannel().getName().equals(Main.singleton.config.getString("DiscordGuildChannel")))
    	{
    		Bukkit.broadcastMessage("§9<" + event.getMember().getEffectiveName() + "> " + event.getMessage().getContent());
    	}
    }
}