package com.github.ubiquitousspice.bloodstains.data;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

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
		List<PlayerState> statesList = new LinkedList<PlayerState>();
		for (PlayerState p : playerStateContainer.states)
		{
			if (p != null)
			{
				statesList.add(p);
			}

		}
		states = ImmutableList.copyOf(statesList);
		PlayerState firstState = states.get(0);
		dimId = firstState.dimension;
		x = firstState.x;
		y = firstState.y;
		z = firstState.z;
	}
}