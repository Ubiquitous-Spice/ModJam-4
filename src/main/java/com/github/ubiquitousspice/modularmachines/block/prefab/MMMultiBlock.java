package com.github.ubiquitousspice.modularmachines.block.prefab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * @author Royalixor.
 */
public abstract class MMMultiBlock extends Block {

    public MMMultiBlock(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    public MMMultiBlock() {
        this(Material.iron);
    }

    public MMMultiBlock(Material material, float hardness, float resistance) {
        this(material);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }

    public abstract int[] getSubtypes();

    public abstract String getNameForType(int type);

    @Override
    public void getSubBlocks(Item block, CreativeTabs tab, List list) {
        if (getSubtypes() == null || getSubtypes().length == 0) {
            list.add(new ItemStack(this, 1, 0));
        } else {
            for (int type : getSubtypes()) {
                list.add(new ItemStack(this, 1, type));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return null;
    }
}
