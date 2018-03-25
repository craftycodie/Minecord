package alex231.minecraft.minecord;


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
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class MinecordPlugin extends JavaPlugin {
	
    public FileConfiguration config = getConfig();

    public JDA jda;
    public MessageChannel chatChannel; 
    public MessageChannel consoleChannel;

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
        // TODO Auto-generated catch block
        // TODO Auto-generated catch block
        // TODO Auto-generated catch block

        chatChannel = jda.getTextChannelsByName(config.getString("DiscordChatChannel"), false).get(0);
        consoleChannel = jda.getTextChannelsByName(config.getString("DiscordConsoleChannel"), false).get(0);

        jda.addEventListener(new DiscordMessageListener());
    }

    private void SetupConsole()
    {
        Logger log = (Logger) LogManager.getRootLogger();
        MinecraftConsoleController newAppender = new MinecraftConsoleController("MinecraftConsoleController", null, null, false);
        newAppender.start();
        log.addAppender(newAppender);
    }

    @Override
    public void onEnable()
    {		
        SetupConfig();
        SetupJDA();
        SetupConsole();

        getServer().getPluginManager().registerEvents(new MinecraftMessageListener(), this);

        chatChannel.sendMessage("**" + "Minecord starting..." + "**").complete();
        consoleChannel.sendMessage("** BEGIN MINECORD CONSOLE OUTPUT **").complete();
        consoleChannel.sendMessage("*Date/Time: " + new Date().toGMTString() + "*").complete();
    }

    @Override
    public void onDisable()
    {
        chatChannel.sendMessage("**" + "Minecord shutting down..." + "**").complete();
        consoleChannel.sendMessage("** END MINECORD CONSOLE OUTPUT **").complete();
        consoleChannel.sendMessage("*Date/Time: " + new Date().toGMTString() + "*").complete();
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
