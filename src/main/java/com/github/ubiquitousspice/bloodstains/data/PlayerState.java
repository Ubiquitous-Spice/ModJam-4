package com.github.ubiquitousspice.bloodstains.data;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

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
    private float       yaw, headYaw;

    private boolean     isBurning, isOnGround;
    
    private int recentlyHit, hurtTime, hurtResistantTime;

    public PlayerState(EntityPlayer player)
    {
        // equipment
        currentHeldItem = player.getHeldItem() == null ? null : player.getHeldItem().copy();
        armour = new ItemStack[player.inventory.armorInventory.length];
        for (int i = 0; i < armour.length; i++)
        {
            armour[i] = player.inventory.armorInventory[i] == null ? null : player.inventory.armorInventory[i].copy();
        }

        // location
        dimension = player.worldObj.provider.dimensionId;
        x = player.posX;
        y = player.posY;
        z = player.posZ;
        motX = player.motionX;
        motY = player.motionY;
        motZ = player.motionZ;
        yaw = player.rotationYaw;
        headYaw = player.rotationYawHead;

        // burning and drowning
        isBurning = player.isBurning();
        isOnGround = player.onGround;
        hurtTime = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, player, "hurtTime", "field_70737_aN");
    }

    private PlayerState() {}
    
    public void applyTo(EntityPlayer player)
    {
        player.inventory.setInventorySlotContents(player.inventory.currentItem, currentHeldItem);
        player.inventory.armorInventory = armour;
        player.posX = x;
        player.posY = y;
        player.posZ = z;
        player.motionX = motX;
        player.motionY = motY;
        player.motionZ = motZ;
        
        player.rotationYaw = yaw;
        player.rotationYawHead = headYaw;
        
        if (isBurning())
            player.setFire(1000);
        else
            player.extinguish();
        
        ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, player, hurtTime, "hurtTime", "field_70737_aN");
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
        output.writeFloat(yaw);
        output.writeFloat(headYaw);
        
        // states
        output.writeBoolean(isBurning);
        output.writeBoolean(isOnGround);
        output.writeInt(hurtTime);
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
        state.yaw = input.readFloat();
        state.headYaw = input.readFloat();
        
        state.isBurning = input.readBoolean();
        state.isOnGround = input.readBoolean();
        state.hurtTime = input.readInt();
        
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

	public ItemStack getCurrentHeldItem() {return this.currentHeldItem;}

	public ItemStack[] getArmour() {return this.armour;}

	public int getDimension() {return this.dimension;}

	public double getX() {return this.x;}

	public double getY() {return this.y;}

	public double getZ() {return this.z;}

	public double getMotX() {return this.motX;}

	public double getMotY() {return this.motY;}

	public double getMotZ() {return this.motZ;}

	public float getYaw() {return this.yaw;}

	public float getHeadYaw() {return this.headYaw;}

	public boolean isBurning() {return this.isBurning;}

	public boolean isOnGround() {return this.isOnGround;}

	public int getRecentlyHit() {return this.recentlyHit;}

	public int getHurtTime() {return this.hurtTime;}

	public int getHurtResistantTime() {return this.hurtResistantTime;}
}
