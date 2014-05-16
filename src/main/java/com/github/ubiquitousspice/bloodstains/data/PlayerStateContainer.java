package com.github.ubiquitousspice.bloodstains.data;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;

public class PlayerStateContainer
{
    public final UUID                            uid;
    public final String                          username;

    private PlayerState                          oldest, newest;
    private final LinkedList<PlayerStateOverlay> overlays  = Lists.newLinkedList();
    private int                                  size      = 0;

    public static final int                      MAX_TICKS = 400;

    public PlayerStateContainer(UUID uid, String username, PlayerState state)
    {
        this.uid = uid;
        this.username = username;
        oldest = newest = state;
    }

    public void addState(PlayerState newState)
    {
        PlayerStateOverlay overlay = new PlayerStateOverlay(newest, newState);
        newest = newState;
        overlays.push(overlay);
        size++;

        // check for size
        if (size > MAX_TICKS)
        {
            size = MAX_TICKS;
            overlay = overlays.peekLast();
            
            if (overlay != null)
                overlays.removeLast();
            
            if (overlay == null)
            {
                throw new IllegalArgumentException("Overlay is null!!!  Size="+size+"  and overlays="+overlays);
            }
            
            oldest = new PlayerState(oldest, overlay);
        }
    }

    @SuppressWarnings("unchecked")
    public BloodStain getBloodStain(boolean clean)
    {
        // TODO: search for a suitable base location for the bloodstain?
        // CUt off dimensional travel... maybe.. unless mystcraft
        // maybe some special handling for falling to death or drowning.
        
        BloodStain stain = new BloodStain(username, oldest, (List<PlayerStateOverlay>) overlays.clone());
        
        if (clean)
        {
            overlays.clear();
            oldest = newest;
            size   = 0;
        }
        
        return stain;
    }
}
