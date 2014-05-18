package com.github.ubiquitousspice.bloodstains.network;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;

import java.io.IOException;
import java.util.EnumMap;

import static cpw.mods.fml.relauncher.Side.CLIENT;
import static cpw.mods.fml.relauncher.Side.SERVER;

public class PacketManager extends FMLIndexedMessageToMessageCodec<PacketBase>
{
    private static final PacketManager                     INSTANCE = new PacketManager();
    private static final EnumMap<Side, FMLEmbeddedChannel> channels = Maps.newEnumMap(Side.class);

    public static void init()
    {
        // duplicate init safeguard
        if (!channels.isEmpty())
            return;
        
        // register packets.
        int id = 0;
        INSTANCE.addDiscriminator(id++, PacketStainRemover.class);
        INSTANCE.addDiscriminator(id++, PacketCreateStain.class);

        // register
        channels.putAll(NetworkRegistry.INSTANCE.newChannel("BloodStains", INSTANCE));
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, PacketBase msg, ByteBuf target) throws Exception
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        msg.encode(output);
        target.writeBytes(output.toByteArray());
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, PacketBase msg)
    {
        ByteArrayDataInput input = ByteStreams.newDataInput(source.array());
        
        // the discriminator 
        input.skipBytes(1);
		try
		{
			msg.decode(input);
		}
		catch (IOException e)
		{
			Throwables.propagate(e);
		}

		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
            actionClient(msg);
        }
        else
        {
            actionServer(ctx, msg);
        }
    }
    
    @SideOnly(Side.CLIENT)
    private void actionClient(PacketBase packet)
    {
        packet.actionClient(Minecraft.getMinecraft().thePlayer);
    }
    
    private void actionServer(ChannelHandlerContext ctx, PacketBase packet)
    {
        packet.actionServer(((NetHandlerPlayServer)ctx.channel().attr(NetworkRegistry.NET_HANDLER)).playerEntity);
    }

    // UTIL SENDING METHODS

    public static void sendToServer(PacketBase packet)
    {
        channels.get(CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(CLIENT).writeAndFlush(packet);
    }

    public static void sendToPlayer(PacketBase packet, EntityPlayer player)
    {
        channels.get(SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(SERVER).writeAndFlush(packet);
    }

    public static void sendToAllAround(PacketBase packet, NetworkRegistry.TargetPoint point)
    {
        channels.get(SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        channels.get(SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(SERVER).writeAndFlush(packet);
    }

    public static void sendToDimension(PacketBase packet, int dimension)
    {
        channels.get(SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimension);
        channels.get(SERVER).writeAndFlush(packet);
    }

    public static void sendToAll(PacketBase packet)
    {
        channels.get(SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(SERVER).writeAndFlush(packet);
    }

    public static Packet toMcPacket(PacketBase packet)
    {
        return channels.get(FMLCommonHandler.instance().getEffectiveSide()).generatePacketFrom(packet);
    }
}