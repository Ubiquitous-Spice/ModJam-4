package com.github.ubiquitousspice.bloodstains.data;

import java.util.LinkedList;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;

import com.google.common.collect.Lists;

public class PlayerStateContainer
{
    public final UUID                            uid;
    public final String                          username;

    private PlayerState                          oldest, newest;
    private final LinkedList<PlayerStateOverlay> overlays  = Lists.newLinkedList();
    private int                                  size      = 0;

    public static final int                      MAX_TICKS = 400;

    public PlayerStateContainer(PlayerState state)
    {
        uid = state.uid;
        username = state.username;
        oldest = newest = state;
    }

    public void addState(PlayerState newState)
    {
        PlayerStateOverlay overlay = new PlayerStateOverlay(newest, newState);
        newest = newState;
        overlays.push(overlay);
        size++;

        LogManager.getLogger().trace("Adding state for player '{}', now there are {} states", username, size);

        // check for size
        if (size > MAX_TICKS)
        {
            size = MAX_TICKS;
            overlay = overlays.pop();
            oldest = new PlayerState(oldest, overlay);

            LogManager.getLogger().trace("Too many states! Updating base!");
        }
    }

    // TODO: add util methods for playback
}
