package com.github.ubiquitousspice.bloodstains.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.google.common.collect.ImmutableList;

@EqualsAndHashCode
@ToString
public class BloodStain
{
	public final int dimId;
	public final double x, y, z;
	public final String username;
	public final List<PlayerState> states;

	public BloodStain(PlayerStateContainer container)
	{
		this.username = container.username;
		states = ImmutableList.copyOf(container.states);
		PlayerState firstState = states.get(0);
		dimId = firstState.getDimension();
		x = firstState.getX();
		y = firstState.getY();
		z = firstState.getZ();
	}
	
    private BloodStain(String username, int dim, double x, double y, double z, List<PlayerState> states)
    {
        this.username = username;
        this.dimId = dim;
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.states = states;
    }

    public void writeTo(DataOutput output) throws IOException
    {
        // write ID
        output.writeUTF(username);
        
        // write location
        output.writeInt(dimId);
        output.writeDouble(x);
        output.writeDouble(y);
        output.writeDouble(z);
        
        // states
        output.writeInt(states.size());
        for (PlayerState state : states)
            state.writeTo(output);
    }

    public static BloodStain readFrom(DataInput input) throws IOException
    {
        String username = input.readUTF();
        int dim = input.readInt();
        double x = input.readDouble();
        double y = input.readDouble();
        double z = input.readDouble();
        
        int size = input.readInt();
        ArrayList<PlayerState> states = new ArrayList<PlayerState>(input.readInt());
        for (int i = 0; i < size; i++)
            states.add(PlayerState.readFrom(input));
        
        return new BloodStain(username, dim, x, y, z, states);
    }
}