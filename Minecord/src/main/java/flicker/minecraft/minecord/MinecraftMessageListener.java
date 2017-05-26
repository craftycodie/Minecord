package flicker.minecraft.minecord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.dv8tion.jda.core.entities.MessageChannel;

public class MinecraftMessageListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
		MessageChannel channel = Main.singleton.jda.getTextChannelsByName(Main.singleton.config.getString("DiscordGuildChannel"), false).get(0);
		channel.sendMessage("<" + ChatColor.stripColor(event.getPlayer().getDisplayName()) + "> " + ChatColor.stripColor(event.getMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
		MessageChannel channel = Main.singleton.jda.getTextChannelsByName(Main.singleton.config.getString("DiscordGuildChannel"), false).get(0);
		channel.sendMessage(ChatColor.stripColor(event.getJoinMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
		MessageChannel channel = Main.singleton.jda.getTextChannelsByName(Main.singleton.config.getString("DiscordGuildChannel"), false).get(0);
		channel.sendMessage(ChatColor.stripColor(event.getQuitMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
		MessageChannel channel = Main.singleton.jda.getTextChannelsByName(Main.singleton.config.getString("DiscordGuildChannel"), false).get(0);
		channel.sendMessage(ChatColor.stripColor(event.getLeaveMessage())).complete();
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
		MessageChannel channel = Main.singleton.jda.getTextChannelsByName(Main.singleton.config.getString("DiscordGuildChannel"), false).get(0);
		channel.sendMessage(ChatColor.stripColor(event.getDeathMessage())).complete();
    }
	
}
