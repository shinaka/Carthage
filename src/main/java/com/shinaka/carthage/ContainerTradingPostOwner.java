package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by Jim on 5/11/2014.
 */
public class ContainerTradingPostOwner extends Container {
    public ContainerTradingPostOwner(InventoryPlayer inventory, TileEntityTradingPost te)
    {

    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return false;
    }
}
