package com.github.ubiquitousspice.bloodstains.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

public interface PacketBase
{
    public void encode(DataOutput buff) throws IOException;

    public void decode(DataInput buff) throws IOException;

    public void actionClient(EntityPlayer player);

    public void actionServer(EntityPlayer player);
}
