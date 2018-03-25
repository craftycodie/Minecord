package alex231.minecraft.minecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public ArrayList<String> console = new ArrayList<>();
    Thread discordSendThread;
    
    Runnable consoleSender = new Runnable()
    {
        public void run()
        {
            while(enabled)
            {
                if(console.size() > 0)
                {
                    ArrayList<String> messages = new ArrayList<>();
                    String message = "";
                    for(String line : console)
                    {
                        if((message + line).length() + 2 > 2000)
                        {
                            messages.add("`" + message + "`");
                            message = "";
                        }
                        
                        message = message + line + System.getProperty("line.separator");
                    }

                    if(!"".equals(message))
                        messages.add("`" + message + "`");
                    
                    for(String msg : messages)
                        MinecordPlugin.getInstance().consoleChannel.sendMessage(msg).complete();

                    console = new ArrayList<>();
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MinecraftConsoleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    
    public boolean enabled;
	
    protected MinecraftConsoleController(String name, Filter filter, Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void start()
    {
        enabled = true;

        discordSendThread = new Thread(consoleSender);
        discordSendThread.start();
        
        super.start();
    }
    
    @Override
    public void stop()
    {
        enabled = false;
        discordSendThread.stop();
        super.stop();
    }
    
    @PluginFactory
    public static MinecraftConsoleController createAppender
    (
        @PluginAttribute("name") String name,
        @PluginElement("Layout") Layout<? extends Serializable> layout,
        @PluginElement("Filter") final Filter filter,
        @PluginAttribute("otherAttribute") String otherAttribute
    ) 
    {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createLayout("[%d{HH:mm:ss} %level]: %msg", null, null, null, null, false, false, null, null);
        }
        return new MinecraftConsoleController(name, filter, layout, true);
    }

    @Override
    public void append(LogEvent logEvent) 
    {
        if(enabled)
            console.add(logEvent.getMessage().getFormattedMessage());
    }

    public static void SendCommand(String command)
    {
        ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
        Bukkit.getServer().dispatchCommand(sender, command);
    }
}
