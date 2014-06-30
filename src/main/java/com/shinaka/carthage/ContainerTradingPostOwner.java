package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Jim on 5/11/2014.
 */
public class ContainerTradingPostOwner extends Container {
    TileEntityTradingPost te;

    public ContainerTradingPostOwner(InventoryPlayer inventoryPlayer, TileEntityTradingPost te)
    {
        this.te = te;

        //Stock Row 1
        addSlotToContainer(new Slot(te, 0, 10, 91));
        addSlotToContainer(new Slot(te, 1, 27, 91));
        addSlotToContainer(new Slot(te, 2, 44, 91));
        addSlotToContainer(new Slot(te, 3, 61, 91));
        //Stock Row 2
        addSlotToContainer(new Slot(te, 4, 10, 108));
        addSlotToContainer(new Slot(te, 5, 27, 108));
        addSlotToContainer(new Slot(te, 6, 44, 108));
        addSlotToContainer(new Slot(te, 7, 61, 108));

        //Received Row 1
        addSlotToContainer(new Slot(te, 8, 103, 91));
        addSlotToContainer(new Slot(te, 9, 120, 91));
        addSlotToContainer(new Slot(te, 10, 137, 91));
        addSlotToContainer(new Slot(te, 11, 154, 91));
        //Received Row 2
        addSlotToContainer(new Slot(te, 12, 103, 108));
        addSlotToContainer(new Slot(te, 13, 120, 108));
        addSlotToContainer(new Slot(te, 14, 137, 108));
        addSlotToContainer(new Slot(te, 15, 154, 108));

        //Ledger
        addSlotToContainer(new Slot(te, TileEntityTradingPost.ledgerSlotIdx, 128, 61));

        //For Sale
        addSlotToContainer(new Slot(te, TileEntityTradingPost.tradedItemSlotIdx, 18, 61));

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
    public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer player)
    {
        switch(slot)
        {
            case 16:
            {
                if(player.inventory.getItemStack() != null &&
                        player.inventory.getItemStack().getItem() == Carthage.itemLedger)
                {
                    //te.SetHasLedger(true);
                    return super.slotClick(slot, par2, par3, player);
                }
                else if(player.inventory.getItemStack() == null)
                {
                    //te.SetHasLedger(false);
                    //this.getSlot(TileEntityTradingPost.ledgerSlotIdx)
                    return super.slotClick(slot, par2, par3, player);
                }
                else
                {
                    return null;
                }

            }
            case 17:
            {
                if(player.inventory.getItemStack() != null)
                {
                    ItemStack mouseItem = player.inventory.getItemStack();
                    ItemStack clonedItem = mouseItem.copy();
                    clonedItem.stackSize = 1;
                    this.getSlot(slot).putStack(clonedItem);
                    player.inventory.closeInventory();
                    return clonedItem;
                }
                else
                {
                    this.getSlot(slot).putStack(null);
                    player.inventory.closeInventory();
                }
            }
            break;

            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                if(player.inventory.getItemStack() != null)
                    return null;
        }
        return super.slotClick(slot, par2, par3, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < 9) {
                if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                    return null;
                }
            }
            //places it into the tileEntity is possible since its in the player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
}
