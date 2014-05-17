package com.github.ubiquitousspice.bloodstains.data;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@EqualsAndHashCode
@ToString
public class PlayerState
{
	// equipment
	public final ItemStack currentHeldItem;
	public final ItemStack[] armour;

	// location
	public final int dimension;
	public final double x, y, z;
	public final float yaw, headYaw;

	// health and well bieng
	public final int food;
	public final float health;

	// TODO: account for drowning, fire death, hit status, and flying/falling

	public PlayerState(EntityPlayer player)
	{
		// equoment
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
		yaw = player.rotationYaw;
		headYaw = player.rotationYawHead;

		// food and stuff
		food = player.getFoodStats().getFoodLevel();
		health = player.getHealth();
	}
}
