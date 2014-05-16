package com.github.ubiquitousspice.bloodstains.client;

import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;

import com.github.ubiquitousspice.bloodstains.data.PlayerState;
import com.github.ubiquitousspice.bloodstains.data.PlayerStateContainer;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerTracker
{
    private final HashMap<UUID, PlayerStateContainer> containers = Maps.newHashMap();

    @SubscribeEvent
    public void playerTick(PlayerTickEvent e)
    {
        final PlayerState state = new PlayerState(e.player);

        PlayerStateContainer container = containers.get(e.player.getUniqueID());
        if (container == null)
        {
            container = new PlayerStateContainer(state);
            containers.put(e.player.getUniqueID(), container);
            LogManager.getLogger().trace("Creating container for {}", state.username);
        }
        else
        {
            // execute new thread;
            new Thread() {
                public void run()
                {
                    LogManager.getLogger().trace("Creating container for {}", state.username);
                    containers.get(state.uid).addState(state);
                }
            }.start();
        }
    }
}
