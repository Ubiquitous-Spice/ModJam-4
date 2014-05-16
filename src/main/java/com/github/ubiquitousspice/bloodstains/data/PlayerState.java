package com.github.ubiquitousspice.bloodstains.data;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@EqualsAndHashCode
@ToString
public class PlayerState
{
    // identification
    public final UUID        uid;
    public final String      username;

    // equipment
    public final ItemStack   currentHeldItem;
    public final ItemStack[] armour;

    // location
    public final double      x, y, z;
    public final float       yaw, headYaw;

    // health and well bieng
    public final int         food;
    public final float       health;

    public PlayerState(EntityPlayer player)
    {
        uid = player.getPersistentID();
        username = player.getDisplayName();

        // equoment
        currentHeldItem = player.getHeldItem() == null ? null : player.getHeldItem().copy();
        armour = new ItemStack[player.inventory.armorInventory.length];
        for (int i = 0; i < armour.length; i++)
        {
            armour[i] = player.inventory.armorInventory[i] == null ? null : player.inventory.armorInventory[i].copy();
        }

        // location
        x = player.posX;
        y = player.posY;
        z = player.posZ;
        yaw = player.rotationYaw;
        headYaw = player.rotationYawHead;

        // food and stuff
        food = player.getFoodStats().getFoodLevel();
        health = player.getHealth();
    }

    public PlayerState(PlayerState base, PlayerStateOverlay overlay)
    {
        // ID
        uid = base.uid;
        username = base.username;

        // equppped
        currentHeldItem = overlay.heldChange ? overlay.currentHeldItem : base.currentHeldItem;
        
        // armour
        armour = new ItemStack[base.armour.length];
        for (int i = 0; i < base.armour.length; i++)
        {
            armour[i] = overlay.armourChanges[i] ? overlay.armour[i] : base.armour[i];
        }
        
        // location
        x = overlay.positionChange ? overlay.x : base.x;
        y = overlay.positionChange ? overlay.y : base.y;
        z = overlay.positionChange ? overlay.z : base.z;
        
        // yaw
        yaw = overlay.yawChange ? overlay.yaw : base.yaw;
        headYaw = overlay.yawChange ? overlay.headYaw : base.headYaw;
        
        // food and stuff
        food = overlay.healthChange ? overlay.food : base.food;
        health = overlay.healthChange ? overlay.health : base.health;
    }
}
