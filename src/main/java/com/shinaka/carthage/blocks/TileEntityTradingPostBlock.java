package com.shinaka.carthage.blocks;

import com.shinaka.carthage.Carthage;
import com.shinaka.carthage.items.TradingPostItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by James on 4/28/2014.
 */
public class TileEntityTradingPostBlock extends BlockContainer
{
    public TileEntityTradingPostBlock()
    {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setHardness(1.5f).setResistance(10.0f);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        TradingPostItemBlock newItem = new TradingPostItemBlock(Carthage.tradingPostBlock);
        float f = 0.7F;
        double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        ItemStack itemStack;// = new ItemStack(block);
        TileEntity t = world.getTileEntity(x,y,z);



        if (t instanceof TileEntityTradingPost) {
            TileEntityTradingPost tPost = (TileEntityTradingPost ) t;
            String name = tPost.getBlockOwner();
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            itemStack = new ItemStack(block, 1, 0);
            if (!itemStack.hasTagCompound()) {
                itemStack.setTagCompound(new NBTTagCompound());
            }
            if(name != null && !name.isEmpty())
                itemStack.getTagCompound().setString("blockOwner", name);

            //Now we need to empty out the inventory
            //The Trading post technically has an 18 size inventory, but the last
            //item is the traded item, and just a clone.
            for(int i = 0; i < 17; ++i)
            {
                ItemStack item = tPost.getStackInSlot(i);
                if(item != null)
                {
                    items.add(item);
                }
            }
            for(ItemStack stackItem : items)
            {
                EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, stackItem);
                world.spawnEntityInWorld(entityitem);
            }
        }

    }
    /*
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {

    }*/
    @Override
    public TileEntity createNewTileEntity(World world, int iVar)
    {
        return new TileEntityTradingPost();
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

    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        this.blockIcon = icon.registerIcon("carthage:tradingposticon");
    }

    @Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
    {
        return;
    }
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        //return null;

        TileEntity t = world.getTileEntity(x,y,z);

        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        //Get the TileEntity and persist the blockOwner into the item NBT
        if (t instanceof TileEntityTradingPost) {
            TileEntityTradingPost tPost = (TileEntityTradingPost ) t;
            String name = tPost.getBlockOwner();

            ItemStack stack = new ItemStack(world.getBlock(x, y, z), 1, metadata);
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }
            stack.getTagCompound().setString("blockOwner", name);
            items.add(stack);

            //Now we need to empty out the inventory
            //The Trading post technically has an 18 size inventory, but the last
            //item is the traded item, and just a clone.
            for(int i = 0; i < 17; ++i)
            {
                ItemStack item = tPost.getStackInSlot(i);
                if(item != null)
                {
                    items.add(item);
                }
            }
        }

        return items;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2, float par3, float par4)
    {
        player.openGui(Carthage.INSTANCE, GetGuiType((TileEntityTradingPost)world.getTileEntity(x,y,z), player), world, x,y,z);
        return true;
    }

    int GetGuiType(TileEntityTradingPost te, EntityPlayer player)
    {
        if(te.getBlockOwner().equalsIgnoreCase(player.getDisplayName()))
            return 1;
        return 2;
    }
    @Override
    public String getUnlocalizedName()
    {
        return "Trading Post";
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntityTradingPost tpost = (TileEntityTradingPost)world.getTileEntity(x, y, z);
        if(tpost != null)
        {
            if(tpost.getBlockOwner().equals(player.getDisplayName()))
            {
                return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
            }
        }
        return -1.0f;
    }
}
