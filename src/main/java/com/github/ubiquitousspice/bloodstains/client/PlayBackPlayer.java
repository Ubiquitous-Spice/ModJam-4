package com.github.ubiquitousspice.bloodstains.client;

import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import com.github.ubiquitousspice.bloodstains.data.BloodStain;
import com.github.ubiquitousspice.bloodstains.data.PlayerState;
import com.mojang.authlib.GameProfile;

public class PlayBackPlayer extends EntityOtherPlayerMP
{
    private final LinkedList<PlayerState> states;
    
    public PlayBackPlayer(BloodStain stain)
    {
        super(Minecraft.getMinecraft().theWorld, new GameProfile(stain.uid.toString(), stain.username));
        this.posX = stain.x;
        this.posY = stain.y;
        this.posZ = stain.z;
        
        // convert states to a list of things that can eb safely popped off...
        states = new LinkedList<PlayerState>();
        states.addAll(stain.states);
    }
    
    @Override
    public void onUpdate()
    {
        states.pop().applyTo(this);
    }
    
    // stolen from forge fakePlayer + some edits
    @Override protected void entityInit() {}
    @Override public boolean attackEntityFrom(DamageSource source, float par2)  { return false;  }
    @Override public void addChatMessage(IChatComponent chat) {}
    @Override public void addChatComponentMessage(IChatComponent chat){}
    @Override public void addStat(StatBase par1StatBase, int par2){}
    @Override public void openGui(Object mod, int modGuiId, World world, int x, int y, int z){}
    @Override public boolean isEntityInvulnerable(){ return true; }
    @Override public boolean canAttackPlayer(EntityPlayer player){ return false; }
    @Override public boolean hitByEntity(Entity par1Entity) { return false; }
    @Override public void onDeath(DamageSource source){ return; }
    @Override public void travelToDimension(int dim){ return; }
    @Override public boolean canCommandSenderUseCommand(int var1, String var2) { return false; }
}
