package com.github.ubiquitousspice.bloodstains;

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
        // blocks and items..
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        // not needed yet.
    }
}
