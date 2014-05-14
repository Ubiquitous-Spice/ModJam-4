package com.github.ubiquitousspice.modularmachines;

import com.github.ubiquitousspice.modularmachines.core.proxy.CommonProxy;
import com.github.ubiquitousspice.modularmachines.util.ModProps;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Royalixor.
 */

@Mod(modid = ModProps.ID, name = ModProps.NAME, version = ModProps.VERSION)
public class ModularMachines {

    @Mod.Instance(ModProps.ID)
    public static ModularMachines instance;

    @SidedProxy(clientSide = ModProps.CLIENT_PROXY, serverSide = ModProps.COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void modsLoaded(FMLPostInitializationEvent event) {

    }
}
