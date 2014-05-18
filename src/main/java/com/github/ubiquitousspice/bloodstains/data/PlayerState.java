package com.github.ubiquitousspice.bloodstains.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.github.ubiquitousspice.bloodstains.client.PlaybackPlayer;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

@EqualsAndHashCode
@ToString
public class PlayerState
{
    // equipment
    private ItemStack   currentHeldItem;
    private ItemStack[] armour;

    // location
    private int         dimension;
    private double      x, y, z;
    private double      motX, motY, motZ;
    
    //
    boolean sneaking;
    
    // rotation
    private float       yawOffset, yaw, pitch, headYaw, cameraYaw, cameraPitch;
    
    // walk
    private int         stepDist;
    private float       walkDistMod, walkDistStepMod;

    // swing
    private int         itemUseCount;

    // hit and hurt data
    private int recentlyHit, hurtTime, hurtResistantTime, fireTime;
    private float health;
    
    // placement data
    private boolean     isOnGround;

    public PlayerState(EntityPlayer player)
    {
        // equipment
        currentHeldItem = player.getHeldItem() == null ? null : player.getHeldItem().copy();
        armour = new ItemStack[player.inventory.armorInventory.length];
        for (int i = 0; i < armour.length; i++)
        {
            armour[i] = player.inventory.armorInventory[i] == null ? null : player.inventory.armorInventory[i].copy();
        }

        // location and motion
        dimension = player.worldObj.provider.dimensionId;
        x = player.posX;
        y = player.posY;
        z = player.posZ;
        motX = player.motionX;
        motY = player.motionY;
        motZ = player.motionZ;
        
        sneaking = player.isSneaking();
        
        // rotations
        yawOffset = player.renderYawOffset;
        yaw = player.rotationYaw;
        pitch = player.rotationPitch;
        headYaw = player.rotationYawHead;
        cameraYaw = player.cameraYaw;
        cameraPitch = player.cameraPitch;
        
        // walking
        stepDist = ObfuscationReflectionHelper.getPrivateValue(Entity.class, player, "nextStepDistance", "field_70150_b");
        walkDistMod = ObfuscationReflectionHelper.getPrivateValue(Entity.class, player, "distanceWalkedModified", "field_70140_Q");
        walkDistStepMod = ObfuscationReflectionHelper.getPrivateValue(Entity.class, player, "distanceWalkedOnStepModified", "field_82151_R");
        
        // item use
        itemUseCount = ObfuscationReflectionHelper.getPrivateValue(EntityPlayer.class, player, "itemInUseCount", "field_71072_f");

        // burning and drowning
        isOnGround = player.onGround;
        hurtTime = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, player, "hurtTime", "field_70737_aN");
        fireTime = ObfuscationReflectionHelper.getPrivateValue(Entity.class, player, "fire", "field_70151_c");
        
        health = player.getHealth();
    }

    private PlayerState() {}
    
    public void applyTo(PlaybackPlayer player)
    {
        player.inventory.setInventorySlotContents(player.inventory.currentItem, currentHeldItem);
        player.inventory.armorInventory = armour;
        
        player.setPositionAndRotation(x, y, z, yaw, pitch);
        
        // location and motion
        player.posX = x;
        player.posY = y;
        player.posZ = z;
        player.motionX = motX;
        player.motionY = motY;
        player.motionZ = motZ;
        
        player.setSneaking(sneaking);

        // rotations
        player.renderYawOffset = yawOffset;
        player.rotationYaw = yaw;
        player.rotationPitch = pitch;
        player.rotationYawHead = headYaw;
        player.cameraYaw = cameraYaw;
        player.cameraPitch = cameraPitch;
        
        // walking
        ObfuscationReflectionHelper.setPrivateValue(Entity.class, player, stepDist, "nextStepDistance", "field_70150_b");
        ObfuscationReflectionHelper.setPrivateValue(Entity.class, player, walkDistMod, "distanceWalkedModified", "field_70140_Q");
        ObfuscationReflectionHelper.setPrivateValue(Entity.class, player, walkDistStepMod,"distanceWalkedOnStepModified", "field_82151_R");
        
        // swinging
        ObfuscationReflectionHelper.setPrivateValue(EntityPlayer.class, player, itemUseCount, "itemInUseCount", "field_71072_f");
        if (itemUseCount > 0)
        {
            //itemInUse
            ObfuscationReflectionHelper.setPrivateValue(EntityPlayer.class, player, currentHeldItem, "itemInUse", "field_71074_e");
        }
        
        player.setHealth(health);
        
        ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, player, hurtTime, "hurtTime", "field_70737_aN");
        ObfuscationReflectionHelper.setPrivateValue(Entity.class, player, fireTime, "fire", "field_70151_c");
    }

    public void writeTo(DataOutput output) throws IOException
    {
        // inventory
        writeItem(output, currentHeldItem);
        output.writeByte(armour.length);
        for (ItemStack stack : armour)
            writeItem(output, stack);

        // fields in order
        output.writeInt(dimension);
        output.writeDouble(x);
        output.writeDouble(y);
        output.writeDouble(z);
        output.writeDouble(motX);
        output.writeDouble(motY);
        output.writeDouble(motZ);
        
        output.writeBoolean(sneaking);
        
        output.writeFloat(yawOffset);
        output.writeFloat(yaw);
        output.writeFloat(pitch);
        output.writeFloat(headYaw);
        output.writeFloat(cameraYaw);
        output.writeFloat(cameraPitch);
        
        // states
        output.writeBoolean(isOnGround);
        output.writeInt(hurtTime);
        output.writeInt(fireTime);
        
        output.writeInt(stepDist);
        output.writeFloat(walkDistMod);
        output.writeFloat(walkDistStepMod);
        
        output.writeInt(itemUseCount);
        
        output.writeFloat(health);
    }

    public static PlayerState readFrom(DataInput input) throws IOException
    {
        PlayerState state = new PlayerState();

        // equipment
        state.currentHeldItem = readItem(input);
        state.armour = new ItemStack[input.readByte()];
        for (int i = 0; i < state.armour.length; i++)
            state.armour[i] = readItem(input);

        // other stuff.
        state.dimension = input.readInt();
        state.x = input.readDouble();
        state.y = input.readDouble();
        state.z = input.readDouble();
        state.motX = input.readDouble();
        state.motY = input.readDouble();
        state.motZ = input.readDouble();
        
        state.sneaking = input.readBoolean();
        
        state.yawOffset = input.readFloat();
        state.yaw = input.readFloat();
        state.pitch =input.readFloat(); 
        state.headYaw = input.readFloat();
        state.cameraYaw =input.readFloat(); 
        state.cameraPitch = input.readFloat();
        
        state.isOnGround = input.readBoolean();
        state.hurtTime = input.readInt();
        state.fireTime = input.readInt();
        
        state.stepDist = input.readInt();
        state.walkDistMod = input.readFloat();
        state.walkDistStepMod = input.readFloat();
        
        state.itemUseCount = input.readInt();
        
        state.health = input.readFloat();
        
        return state;
    }

    private static void writeItem(DataOutput output, ItemStack item) throws IOException
    {
        // write a boolean for null status
        if (item == null)
        {
            output.writeBoolean(false);
            return; // done
        }

        output.writeBoolean(true);
        NBTTagCompound compound = new NBTTagCompound();
        item.writeToNBT(compound);
        CompressedStreamTools.write(compound, output);
    }

    private static ItemStack readItem(DataInput input) throws IOException
    {
        // write a boolean for null status
        boolean isNotNull = input.readBoolean(); 
        if (isNotNull) // true, not null
        {
            NBTTagCompound compound = CompressedStreamTools.read(input);

            return ItemStack.loadItemStackFromNBT(compound);
        }
        
        return null;
    }

	public int getDimension() {return this.dimension;}

	public double getX() {return this.x;}

	public double getY() {return this.y;}

	public double getZ() {return this.z;}
}
