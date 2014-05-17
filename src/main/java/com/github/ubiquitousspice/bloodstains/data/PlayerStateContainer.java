package com.github.ubiquitousspice.bloodstains.data;

import com.github.ubiquitousspice.bloodstains.util.ConcurrentEvictingQueue;

import java.util.Queue;
import java.util.UUID;

public class PlayerStateContainer
{
	public final UUID uid;
	public final String username;

	final Queue<PlayerState> states;

	public static final int MAX_TICKS = 400;

	public PlayerStateContainer(UUID uid, String username, PlayerState state)
	{
		this.uid = uid;
		this.username = username;

		states = ConcurrentEvictingQueue.create(MAX_TICKS);
		states.add(state);
	}

	public void addState(PlayerState newState)
	{
		states.add(newState);
	}

	public BloodStain getBloodStain()
	{
		// TODO: search for a suitable base location for the bloodstain?
		// CUt off dimensional travel... maybe.. unless mystcraft
		// maybe some special handling for falling to death or drowning.

		BloodStain stain = new BloodStain(this);

		states.clear();

		return stain;
	}
}
