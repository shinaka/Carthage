package com.shinaka.carthage.items;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by James on 4/30/2014.
 */
public class TradingPostItemBlock extends ItemBlock
{
    public TradingPostItemBlock(Block p_i45328_1_) {
        super(p_i45328_1_);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        TileEntityTradingPost tPost = new TileEntityTradingPost();

        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("blockOwner"))
        {
            String owner = stack.getTagCompound().getString("blockOwner");
            if(owner.isEmpty())
                tPost.setBlockOwner(player.getDisplayName());
            else
                tPost.setBlockOwner(owner);
        }
        else
        {
            tPost.setBlockOwner(player.getDisplayName());
        }
        world.setTileEntity(x,y,z, tPost);

        return true;
    }

}
