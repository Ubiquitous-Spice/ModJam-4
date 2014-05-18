package com.github.ubiquitousspice.bloodstains.client;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.DataWatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import com.google.common.collect.Lists;

public class DummyDataWatcher extends DataWatcher
{
    private TIntObjectMap<Object> map = new TIntObjectHashMap<Object>();

    public DummyDataWatcher(PlaybackPlayer entity)
    {
        super(entity);
    }

    @Override
    public float getWatchableObjectFloat(int par1)
    {
        Object obj = map.get(par1);
        if (obj != null) // for health
        {
            return (Float) obj;
        }
        else
        {
            return 0f;
        }
    }

    @Override
    public void addObject(int par1, Object par2Obj)
    {
        map.put(par1, par2Obj);
    }

    @Override
    public void addObjectByDataType(int par1, int par2)
    {
        // nope...
    }

    @Override
    public byte getWatchableObjectByte(int par1)
    {
        Object obj = map.get(par1);
        if (obj != null) // for health
        {
            return (Byte) obj;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public short getWatchableObjectShort(int par1)
    {
        Object obj = map.get(par1);
        if (obj != null) // for health
        {
            return (Short) obj;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int getWatchableObjectInt(int par1)
    {
        Object obj = map.get(par1);
        if (obj != null) // for health
        {
            return (Integer) obj;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public String getWatchableObjectString(int par1)
    {
        return (String) map.get(par1);
    }

    @Override
    public ItemStack getWatchableObjectItemStack(int par1)
    {
        return (ItemStack) map.get(par1);
    }

    @Override
    public void updateObject(int par1, Object par2Obj)
    {
        map.put(par1, par2Obj);
    }

    @Override
    public void setObjectWatched(int par1)
    {
        // uh.. nope?
    }

    @Override
    public boolean hasChanges()
    {
        return false;
    }

    @Override
    public List getChanged()
    {
        // nothing will ever change,.
        return new ArrayList(0);
    }

    @Override
    public void func_151509_a(PacketBuffer p_151509_1_) throws IOException
    {
        // uh.. nope?
    }

    @Override
    public List getAllWatched()
    {
        return Lists.newArrayList(map.keys());
    }

    @Override
    public void updateWatchedObjectsFromList(List par1List)
    {
        // nope/
    }

    @Override
    public boolean getIsBlank()
    {
        return map.isEmpty();
    }

    @Override
    public void func_111144_e()
    {
    }
}
