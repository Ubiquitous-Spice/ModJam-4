package com.github.ubiquitousspice.bloodstains;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import org.apache.logging.log4j.LogManager;

import com.github.ubiquitousspice.bloodstains.data.BloodStain;
import com.github.ubiquitousspice.bloodstains.data.PlayerState;
import com.github.ubiquitousspice.bloodstains.data.PlayerStateContainer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class StainManager
{
    private static StainManager                       INSTANCE   = new StainManager();
    private final HashMap<UUID, PlayerStateContainer> containers = Maps.newHashMap();
    private final List<BloodStain>                    stains     = Lists.newLinkedList();

    public static void init()
    {
        INSTANCE = new StainManager();
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        FMLCommonHandler.instance().bus().register(INSTANCE);
    }
    
    public static Collection<BloodStain> getStains()
    {
        return INSTANCE.stains;
    }

    @SubscribeEvent
    public void playerTick(final PlayerTickEvent e)
    {
        final PlayerState state = new PlayerState(e.player);

        PlayerStateContainer container = containers.get(e.player.getUniqueID());
        if (container == null)
        {
            container = new PlayerStateContainer(e.player.getUniqueID(), e.player.getDisplayName(), state);
            containers.put(e.player.getUniqueID(), container);
            LogManager.getLogger().trace("Creating container for {}", e.player.getDisplayName());
        }
        else
        {
//            // execute new thread;
//            new Thread() {
//                public void run()
//                {
                    containers.get(e.player.getUniqueID()).addState(state);
//                }
//            }.start();
        }
    }

    @SubscribeEvent
    public void playerDeath(LivingDeathEvent e)
    {
        if (!(e.entity instanceof EntityPlayer))
            return;

        // get container.
        PlayerStateContainer container = containers.get(e.entity.getUniqueID());
        if (container == null)
            return;

        // make stain, and clean.
        BloodStain stain = container.getBloodStain(true);
        stains.add(stain);
        
        LogManager.getLogger().debug("Adding stain for {} at {}, {}, {}", stain.username, stain.x, stain.y, stain.z);
    }
}
