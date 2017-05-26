package flicker.minecraft.minecord;


import javax.security.auth.login.LoginException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Main extends JavaPlugin {

	public static Main singleton;
	
	public FileConfiguration config = getConfig();
	
	public JDA jda;
	
	private void SetupConfig()
	{
		config.addDefault("DiscordBotToken", "");
		config.addDefault("DiscordGuildChannel","minecraft_chat");
		//config.add
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	private void SetupJDA()
	{
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(config.getString("DiscordBotToken")).buildBlocking();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RateLimitedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jda.addEventListener(new DiscordMessageListener());
	}
	
	@Override
	public void onEnable()
	{		
		singleton = this;
		
		SetupConfig();
		SetupJDA();
		
	    getServer().getPluginManager().registerEvents(new MinecraftMessageListener(), this);
	}
	
	@Override
	public void onDisable()
	{
		singleton = null;
	}

	
    @Override
    public boolean onCommand(CommandSender sender,
            Command command,
            String label,
            String[] args) {
        if (command.getName().equalsIgnoreCase("minecord")) {
            sender.sendMessage("Hello, I'm Minecord Bot.");
            sender.sendMessage("Token = " + config.getString("DiscordBotToken"));
            sender.sendMessage(jda.asBot().getInviteUrl());
            return true;
        }
		return false;
    }
        
	
}
