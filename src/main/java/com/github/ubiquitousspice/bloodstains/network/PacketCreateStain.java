package com.github.ubiquitousspice.bloodstains.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;

import com.github.ubiquitousspice.bloodstains.StainManager;
import com.github.ubiquitousspice.bloodstains.data.BloodStain;

public class PacketCreateStain implements PacketBase
{
    @Getter
    private BloodStain stain;
    
    public PacketCreateStain(BloodStain stain)
    {
        this.stain = stain;
    }
    
    public PacketCreateStain() {}

    @Override
    public void encode(DataOutput buff) throws IOException
    {
        stain.writeTo(buff);
    }

    @Override
    public void decode(DataInput buff) throws IOException
    {
        stain = BloodStain.readFrom(buff);
    }

    @Override
    public void actionClient(EntityPlayer player)
    {
        StainManager.addStain(stain);
    }

    @Override
    public void actionServer(EntityPlayer player)
    {
        StainManager.addStain(stain);
    }
}
