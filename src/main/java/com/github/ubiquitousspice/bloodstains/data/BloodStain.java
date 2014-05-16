package com.github.ubiquitousspice.bloodstains.data;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BloodStain
{
    public final int                      dimId;
    public final double                   x, y, z;
    public final String                   username;
    public final PlayerState              baseState;
    public final List<PlayerStateOverlay> overlays;
    
    public BloodStain(String username, PlayerState base, List<PlayerStateOverlay> overlays)
    {
        this.username = username;
        dimId = base.dimension;
        x = base.x;
        y = base.y;
        z = base.z;
        baseState = base;
        this.overlays = overlays;
    }
}