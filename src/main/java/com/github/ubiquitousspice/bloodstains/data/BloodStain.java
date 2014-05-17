package com.github.ubiquitousspice.bloodstains.data;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BloodStain
{
	public final int dimId;
	public final double x, y, z;
	public final String username;
	public final ImmutableList<PlayerState> states;

	public BloodStain(PlayerStateContainer playerStateContainer)
	{
		this.username = playerStateContainer.username;
		states = ImmutableList.copyOf(playerStateContainer.states);
		PlayerState firstState = states.get(0);
		dimId = firstState.dimension;
		x = firstState.x;
		y = firstState.y;
		z = firstState.z;
	}
}