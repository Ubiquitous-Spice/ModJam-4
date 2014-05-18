package com.github.ubiquitousspice.bloodstains.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;

import com.github.ubiquitousspice.bloodstains.StainManager;

public class PacketStainRemover implements PacketBase
{
    @Getter
    private int dimension; 
    
    public PacketStainRemover(int dim)
    {
        dimension = dim;
    }
    
    public PacketStainRemover() {}

    @Override
    public void encode(DataOutput buff) throws IOException
    {
        buff.writeInt(dimension);
    }

    @Override
    public void decode(DataInput buff) throws IOException
    {
        dimension = buff.readInt();
    }

    @Override
    public void actionClient(EntityPlayer player)
    {
        StainManager.removeStains(dimension);
    }

    @Override
    public void actionServer(EntityPlayer player)
    {
        StainManager.removeStains(dimension);
    }

}
