package com.shinaka.carthage.blocks;

import com.shinaka.carthage.Carthage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by James on 5/3/2014.
 */
public class TileEntityRegisterBlock extends BlockContainer
{
    public TileEntityRegisterBlock()
    {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2, float par3, float par4)
    {
        player.openGui(Carthage.INSTANCE, 0, world, x,y,z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityRegister();
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /*
    @Override
    public String getUnlocalizedName()
    {
        return "Register";
    }
    */

    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        this.blockIcon = icon.registerIcon("carthage:Registericon");
    }
}
