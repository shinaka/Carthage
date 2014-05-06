package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 5/3/2014.
 */
public class ContainerRegister extends Container
{
    protected TileEntityRegister teRegister;

    public ContainerRegister(InventoryPlayer inventoryPlayer, TileEntityRegister te)
    {
        teRegister = te;
        //Slot 0: Paper Holder
        addSlotToContainer(new Slot(te, 0, 62, 62));
        bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return teRegister.isUseableByPlayer(player);
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer player)
    {
        if(slot == 0 && player.inventory.getItemStack() != null)
        {
            ItemStack mouseItem = player.inventory.getItemStack();
            ItemStack clonedItem = mouseItem.copy();
            clonedItem.stackSize = 1;
            this.getSlot(0).putStack(clonedItem);
            player.inventory.closeInventory();
        }
        else if(slot == 0)
        {
            this.getSlot(0).putStack(null);
            player.inventory.closeInventory();
        }
        else
        {
            return super.slotClick(slot, par2, par3, player);
        }

        return null;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
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
