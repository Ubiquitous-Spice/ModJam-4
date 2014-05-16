package com.github.ubiquitousspice.bloodstains;

import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;

import com.github.ubiquitousspice.bloodstains.client.PlayerTracker;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BloodStains.MODID, name = "BloodStains", version = BloodStains.VERSION)
public class BloodStains
{

    @Mod.Instance(BloodStains.MODID)
    public static BloodStains  instance;

    public static final String MODID   = "bloodstains";
    public static final String VERSION = "@VERSION@";

    @SidedProxy(
            clientSide = "com.github.ubiquitousspice.bloodstains.client.ClientProxy",
            serverSide = "com.github.ubiquitousspice.bloosstains.CommonProxy")
    public static CommonProxy  proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // logging stuff.
        if (!FMLForgePlugin.RUNTIME_DEOBF) // not runtime deobf = dev env
        {
            String packageName = this.getClass().getPackage().getName();
            Logger baseLogger = (Logger) LogManager.getLogger(packageName);
            baseLogger.setLevel(Level.TRACE);
            ConsoleAppender appender = ConsoleAppender.createAppender(null, null, Target.SYSTEM_OUT.toString(), "console", "true", "false"); 
            baseLogger.addAppender(appender);
            appender.start();

            // testing levels..
            for (Level l : Level.values())
            {
                baseLogger.log(l, "TESTING {} on level {}", baseLogger.getName(), l);
                LogManager.getLogger().log(l, "TESTING {} on level {}", this.getClass().getName(), l);
            }
        }

        // blocks and stuff.

    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        // not needed yet.

        // register tracker
        PlayerTracker tracker = new PlayerTracker();
        MinecraftForge.EVENT_BUS.register(tracker);
        FMLCommonHandler.instance().bus().register(tracker);
    }
}
