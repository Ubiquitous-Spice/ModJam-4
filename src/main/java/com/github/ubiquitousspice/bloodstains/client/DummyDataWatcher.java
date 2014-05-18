package com.github.ubiquitousspice.bloodstains.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.DataWatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class DummyDataWatcher extends DataWatcher
{
    PlaybackPlayer e;
  
    public DummyDataWatcher(PlaybackPlayer entity)
    {
        super(entity);
        e = entity;
    }

    @Override
    public float getWatchableObjectFloat(int par1)
    {
        if (par1 == 6) // for health
        {
            return e.getMaxHealth();
        }
        return 0f;
    }

    @Override public void addObject(int par1, Object par2Obj) { }
    @Override public void addObjectByDataType(int par1, int par2) { }
    @Override public byte getWatchableObjectByte(int par1) { return 0; }
    @Override public short getWatchableObjectShort(int par1) { return 0; }
    @Override public int getWatchableObjectInt(int par1) { return 0; }
    @Override public String getWatchableObjectString(int par1) { return ""; }
    @Override public ItemStack getWatchableObjectItemStack(int par1) { return null; }
    @Override public void updateObject(int par1, Object par2Obj) { }
    @Override public void setObjectWatched(int par1) { }
    @Override public boolean hasChanges() {return false; }
    @Override public List getChanged()  { return new ArrayList(0); }
    @Override public void func_151509_a(PacketBuffer p_151509_1_) throws IOException {}
    @Override public List getAllWatched() { return new ArrayList(0); }
    @Override public void updateWatchedObjectsFromList(List par1List) { }
    @Override public boolean getIsBlank() { return true; }
    @Override public void func_111144_e() {}
}
