package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
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
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x,y,z);
        if(te != null && te instanceof TileEntityRegister)
        {
            return new GuiRegister(player.inventory, (TileEntityRegister)te);
        }
        return null;
    }


}
