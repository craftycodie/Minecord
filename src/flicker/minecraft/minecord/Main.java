package flicker.minecraft.minecord;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main extends JavaPlugin {

	FileConfiguration config = getConfig();
	
	@Override
	public void onEnable()
	{
		config.addDefault("DiscordBotToken", "");
		config.options().copyDefaults(true);
		saveConfig();
		
		//JDABuilder jda = new JDABuilder(AccountType.BOT).setToken()
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
    @Override
    public boolean onCommand(CommandSender sender,
            Command command,
            String label,
            String[] args) {
        if (command.getName().equalsIgnoreCase("minecord")) {
            sender.sendMessage("Hello, I'm Minecord Bot.");
            return true;
        }
		return false;
    }
        
	
}
