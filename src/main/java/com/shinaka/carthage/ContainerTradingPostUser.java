package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;


/**
 * Created by Jim on 5/11/2014.
 */
public class ContainerTradingPostUser extends Container
{
    public ContainerTradingPostUser(InventoryPlayer inventory, TileEntityTradingPost te)
    {

    }


    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }
}
