package com.github.ubiquitousspice.bloodstains.client;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import com.github.ubiquitousspice.bloodstains.CommonProxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class ClientProxy extends CommonProxy
{
    protected static KeyBinding STAIN_KEY = new KeyBinding("key.bloodstains.activate", Keyboard.KEY_G, "key.categories.gameplay");
    
    @Override
    public void doStuff()
    {
        MinecraftForge.EVENT_BUS.register(new StainRenderer());
        ClientRegistry.registerKeyBinding(STAIN_KEY);
        FMLCommonHandler.instance().bus().register(this);
    }
    
    // key stuff
    private boolean keyState = false;
    
    @SubscribeEvent
    public void keyPress(KeyInputEvent event)
    {
        boolean newState = GameSettings.isKeyDown(STAIN_KEY);
        
        if (newState && !keyState)
        {
            // KEY UP!
            // TODO: TRIGGER IT!
        }
    }
}
