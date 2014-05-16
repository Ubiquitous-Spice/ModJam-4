package com.github.ubiquitousspice.bloodstains.data;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Lists;

import java.util.UUID;

public class PlayerStateContainer
{
	public final UUID uid;
	public final String username;

	private PlayerState oldest, newest;
	private final EvictingQueue<PlayerStateOverlay> overlays = EvictingQueue.create(MAX_TICKS);

	public static final int MAX_TICKS = 400;

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
		overlays.add(overlay);
	}

	@SuppressWarnings("unchecked")
	public BloodStain getBloodStain(boolean clean)
	{
		// TODO: search for a suitable base location for the bloodstain?
		// CUt off dimensional travel... maybe.. unless mystcraft
		// maybe some special handling for falling to death or drowning.

		BloodStain stain = new BloodStain(username, oldest, Lists.newLinkedList(overlays));

		if (clean)
		{
			overlays.clear();
			oldest = newest;
		}

		return stain;
	}
}
