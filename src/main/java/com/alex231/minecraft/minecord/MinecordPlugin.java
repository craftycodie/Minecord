package com.alex231.minecraft.minecord;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import javax.security.auth.login.LoginException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class MinecordPlugin extends JavaPlugin {
	
    public FileConfiguration config = getConfig();

    public JDA jda;
    public MessageChannel chatChannel; 
    public MessageChannel consoleChannel;
    MinecraftConsoleController consoleAppender;

    public static MinecordPlugin getInstance()
    {
        return getPlugin(MinecordPlugin.class);
    }

    private void SetupConfig()
    {
        config.addDefault("DiscordBotToken", "");
        config.addDefault("DiscordChatChannel","minecraft_chat");
        config.addDefault("DiscordConsoleChannel","minecraft_console");
        config.addDefault("NicknamePrefix", "»");
        config.addDefault("RolePrefixes.Everyone", "Discord>");
        config.options().copyDefaults(true);
        saveConfig();
    }

    private void SetupJDA()
    {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(config.getString("DiscordBotToken")).buildBlocking();
        } catch (LoginException | IllegalArgumentException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        chatChannel = jda.getTextChannelsByName(config.getString("DiscordChatChannel"), false).get(0);
        consoleChannel = jda.getTextChannelsByName(config.getString("DiscordConsoleChannel"), false).get(0);

        jda.addEventListener(new DiscordMessageListener());
    }

    private void SetupConsole()
    {
        Logger log = (Logger) LogManager.getRootLogger();
        consoleAppender = new MinecraftConsoleController("MinecraftConsoleController", null, null, false);
        consoleAppender.start();
        log.addAppender(consoleAppender);
    }

    @Override
    public void onEnable()
    {		
        SetupConfig();
        SetupJDA();
        SetupConsole();

        getServer().getPluginManager().registerEvents(new MinecraftMessageListener(), this);

        chatChannel.sendMessage("**" + "MINECORD STARTING" + "**").queue();
        chatChannel.sendMessage("*Date/Time: " + new Date().toGMTString() + "*").queue();
    }

    @Override
    public void onDisable()
    {
        consoleAppender.stop();
        
        chatChannel.sendMessage("**" + "MINECORD SHUTTING DOWN" + "**").queue();
        chatChannel.sendMessage("*Date/Time: " + new Date().toGMTString() + "*").queue();
    }

	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("minecord")) {
            sender.sendMessage("Hello, I'm Minecord Bot.");
            sender.sendMessage(jda.asBot().getInviteUrl());
            return true;
        }
        return false;
    }
        
	
}
