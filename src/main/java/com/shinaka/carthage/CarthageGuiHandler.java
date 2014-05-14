package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by James on 5/5/2014.
 */
public class CarthageGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te != null && te instanceof TileEntityRegister)
        {
            return new ContainerRegister(player.inventory, (TileEntityRegister)te);
        }
        else if(te != null && te instanceof TileEntityTradingPost)
        {
            TileEntityTradingPost teTpost = (TileEntityTradingPost)te;
            if(IsTradingPostOwner(player, teTpost))
                return new ContainerTradingPostOwner(player.inventory, teTpost);
            else
                return new ContainerTradingPostUser(player.inventory, teTpost);
        }
        return null;
    }

    public boolean IsTradingPostOwner(EntityPlayer player, TileEntityTradingPost tpost)
    {
        return tpost.getBlockOwner() == player.getDisplayName();
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x,y,z);
        if(ID == 0)
        {
            if(te != null && te instanceof TileEntityRegister)
            {
                return new GuiRegister(player.inventory, (TileEntityRegister)te);
            }
        }
        else if(ID == 1)
        {
            TileEntityTradingPost teTpost = (TileEntityTradingPost) te;
            return new GuiTradingPostOwner(player.inventory, teTpost);
        }
        else if(ID == 2)
        {
            TileEntityTradingPost teTpost = (TileEntityTradingPost) te;
            return new GuiTradingPostUser(player.inventory, teTpost);
        }
        return null;
    }


}
