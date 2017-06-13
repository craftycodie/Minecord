package flicker.minecraft.minecord;

import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

@Plugin(name = "MinecraftConsoleController", category = "Core", elementType = "appender", printObject = true)
public class MinecraftConsoleController extends AbstractAppender {

	public boolean enabled;
	
    protected MinecraftConsoleController(String name, Filter filter,
            Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }
	
	@PluginFactory
	public static MinecraftConsoleController createAppender(
	            @PluginAttribute("name") String name,
	            @PluginElement("Layout") Layout<? extends Serializable> layout,
	            @PluginElement("Filter") final Filter filter,
	            @PluginAttribute("otherAttribute") String otherAttribute) {
	        if (name == null) {
	            LOGGER.error("No name provided for MyCustomAppenderImpl");
	            return null;
	        }
	        if (layout == null) {
	            layout = PatternLayout.createLayout("[%d{HH:mm:ss} %level]: %msg", null, null, null, null);
	        }
	        return new MinecraftConsoleController(name, filter, layout, true);
	    }

	@Override
	public void append(LogEvent logEvent) {
		if(enabled)
			MinecordPlugin.getInstance().consoleChannel.sendMessage(logEvent.toString()).complete();
	}
	
	public static void SendCommand(String command)
	{
		ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
		Bukkit.getServer().dispatchCommand(sender, command);
	}
}
