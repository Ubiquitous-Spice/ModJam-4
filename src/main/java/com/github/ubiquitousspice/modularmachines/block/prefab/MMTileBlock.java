package com.github.ubiquitousspice.modularmachines.block.prefab;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Royalixor.
 */
public abstract class MMTileBlock extends MMBaiscBlock implements ITileEntityProvider {

    public MMTileBlock(Material material) {
        super(material);
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    public boolean onBlockEventReceived(World world, int x, int y, int z, int eventID, int meta) {
        super.onBlockEventReceived(world, x, y, z, eventID, meta);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity != null ? tileEntity.receiveClientEvent(eventID, meta) : false;
    }

}
