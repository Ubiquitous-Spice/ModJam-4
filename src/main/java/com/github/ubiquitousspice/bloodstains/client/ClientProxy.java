package com.github.ubiquitousspice.bloodstains.client;

import net.minecraftforge.common.MinecraftForge;

import com.github.ubiquitousspice.bloodstains.CommonProxy;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        MinecraftForge.EVENT_BUS.register(new StainRenderer());
    }
}
