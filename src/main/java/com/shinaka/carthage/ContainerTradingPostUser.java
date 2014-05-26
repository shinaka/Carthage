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
    TileEntityTradingPost tePost;


    public ContainerTradingPostUser(InventoryPlayer inventoryPlayer, TileEntityTradingPost te)
    {
        tePost = te;
        
        bindPlayerInventory(inventoryPlayer);
    }


    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        10 + j * 18, 129 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 10 + i * 18, 187));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }
}
