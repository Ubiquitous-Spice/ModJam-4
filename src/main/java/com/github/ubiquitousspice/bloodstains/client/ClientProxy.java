package com.github.ubiquitousspice.bloodstains.client;

import com.github.ubiquitousspice.bloodstains.CommonProxy;
import com.github.ubiquitousspice.bloodstains.StainManager;
import com.github.ubiquitousspice.bloodstains.data.BloodStain;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.Iterator;
import java.util.LinkedList;

public class ClientProxy extends CommonProxy
{
    protected static KeyBinding STAIN_KEY = new KeyBinding("key.bloodstains.activate", Keyboard.KEY_G, "key.categories.gameplay");
    
    @Override
    public void doStuff()
    {
        MinecraftForge.EVENT_BUS.register(new StainRenderer());
        ClientRegistry.registerKeyBinding(STAIN_KEY);
        
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);

		RenderingRegistry.registerEntityRenderingHandler(PlaybackPlayer.class, new RenderPlayback());
		//EntityRegistry.registerModEntity(PlaybackPlayer.class, "fakePlayer", 0, BloodStains.instance, 0, 0, false);
    }
    
    // key stuff
    private boolean keyState = false;
    
    @SubscribeEvent
    public void keyPress(KeyInputEvent event)
    {
        boolean oldState = keyState; 
        keyState = GameSettings.isKeyDown(STAIN_KEY);
        
        if (!keyState && oldState)
        {
            // KEY UP!
            
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            
            // find nearest stain
            BloodStain nearest = null;
            double distance = 5;
            for (BloodStain stain : StainManager.getStains())
            {
                if (stain.dimId != player.dimension)
                {
                    continue;
                }
                double stainDist = player.getDistance(stain.x, stain.y, stain.z);
                if (distance > stainDist)
                {
                    distance = stainDist;
                    nearest = stain;
                }
            }
            
            if (nearest == null)
            {
                // uh.. error?
                // TODO: spit localized chat message
                return;
            }
            
            // trigger on the found stain.
            PlaybackPlayer playBack = new PlaybackPlayer(nearest);
            playBack.worldObj.spawnEntityInWorld(playBack); // to handle ticking ONLY
            players.add(playBack);
        }
    }
    
    // render stuff
    
    private final LinkedList<PlaybackPlayer> players = new LinkedList<PlaybackPlayer>();
//    private final RenderPlayback renderer = new RenderPlayback();
    
    @SubscribeEvent
    public void render(RenderWorldLastEvent e)
    {
        Iterator<PlaybackPlayer> it = players.iterator();
        while(it.hasNext())
        {
            PlaybackPlayer player = it.next();
            
            if (player.isDead)
            {
                it.remove();
            }
            
            RenderManager.instance.renderEntitySimple(player, e.partialTicks);
        }
    }
}
