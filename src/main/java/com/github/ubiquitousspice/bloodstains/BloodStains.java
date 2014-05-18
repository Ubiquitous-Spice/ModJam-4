package com.github.ubiquitousspice.bloodstains;

import com.github.ubiquitousspice.bloodstains.network.PacketManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.classloading.FMLForgePlugin;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;

@Mod(modid = BloodStains.MODID, name = "BloodStains", version = BloodStains.VERSION)
public class BloodStains
{

    @Mod.Instance(BloodStains.MODID)
    public static BloodStains  instance;

    public static final String MODID   = "bloodstains";
    public static final String VERSION = "@VERSION@";

	public static boolean OUR_SERVER = false;
	public static String OUR_SERVER_IP = "http://localhost:8080";

    @SidedProxy(
            clientSide = "com.github.ubiquitousspice.bloodstains.client.ClientProxy",
            serverSide = "com.github.ubiquitousspice.bloodstains.CommonProxy")
    public static CommonProxy  proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // logging stuff.
        if (!FMLForgePlugin.RUNTIME_DEOBF) // not runtime deobf = dev env
        {
            String packageName = this.getClass().getPackage().getName();
            Logger baseLogger = (Logger) LogManager.getLogger(packageName);
            ConsoleAppender appender = ConsoleAppender.createAppender(null, null, Target.SYSTEM_OUT.toString(), "console", "true", "false");
            baseLogger.addAppender(appender);
            baseLogger.setLevel(Level.DEBUG);
            appender.start();

            // testing levels..
            for (Level l : Level.values())
            {
                baseLogger.log(l, "TESTING {} on level {}", baseLogger.getName(), l);
                LogManager.getLogger().log(l, "TESTING {} on level {}", this.getClass().getName(), l);
            }
        }
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        StainManager.init();
        PacketManager.init();
        proxy.doStuff();
    }
}
