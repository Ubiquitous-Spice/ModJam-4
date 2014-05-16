package com.github.ubiquitousspice.bloodstains.data;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.minecraft.item.ItemStack;

@EqualsAndHashCode
@ToString
public class PlayerStateOverlay
{
    public final boolean     heldChange, positionChange, dimChange, yawChange, healthChange;

    public final boolean[]   armourChanges;
    public final ItemStack   currentHeldItem;
    public final ItemStack[] armour;

    // location
    public final int         dimension;
    public final double      x, y, z;
    public final float       yaw, headYaw;

    // health and well bieng
    public final int         food;
    public final float       health;

    // TODO: account for drowning, fire death, hit status, and flying/falling

    public PlayerStateOverlay(PlayerState base, PlayerState next)
    {
        // equipped item
        currentHeldItem = (heldChange = !ItemStack.areItemStacksEqual(base.currentHeldItem, next.currentHeldItem)) ? next.currentHeldItem : base.currentHeldItem;

        // armor
        armourChanges = new boolean[base.armour.length];
        armour = new ItemStack[base.armour.length];
        for (int i = 0; i < base.armour.length; i++)
        {
            if (armourChanges[i] = !ItemStack.areItemStacksEqual(base.armour[i], next.armour[i]))
            {
                armour[i] = next.armour[i];
            }
        }

        // location.
        dimension = (dimChange = !(base.dimension == next.dimension)) ? next.dimension : base.dimension;

        if (positionChange = !(base.x == next.x && base.y == next.y && base.z == next.z))
        {
            x = next.x;
            y = next.y;
            z = next.z;
        }
        else
        {
            x = y = z = 0d;
        }

        // yaw
        if (yawChange = !(base.yaw == next.yaw && base.headYaw == next.headYaw))
        {
            yaw = next.yaw;
            headYaw = next.headYaw;
        }
        else
        {
            yaw = headYaw = 0;
        }

        // food /health
        if (healthChange = !(base.health == next.health && base.food == next.food))
        {
            health = next.health;
            food = next.food;
        }
        else
        {
            health = 0;
            food = 0;
        }
    }
}
