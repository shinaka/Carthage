package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
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

        addSlotToContainer(new Slot(te, 0, 39, 85));
        addSlotToContainer(new Slot(te, 1, 89, 85));
        addSlotToContainer(new Slot(te, 2, 139, 85));
        addSlotToContainer(new Slot(te, 3, 189, 85));

        //Slot 4: Paper Holder
        addSlotToContainer(new Slot(te, 4, 214, 190));

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
                        10 + j * 18, 145 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 10 + i * 18, 200));
        }
    }

    @Override
    public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer player)
    {
        switch(slot)
        {
            case 0:
            case 1:
            case 2:
            case 3:
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
                break;
            case 4:
                if(player.inventory.getItemStack() != null && player.inventory.getItemStack().getItem() == Items.paper)
                {
                    return super.slotClick(slot, par2, par3, player);
                }
                else if(player.inventory.getItemStack() == null)
                {
                    return super.slotClick(slot, par2, par3, player);
                }
                else
                {
                    return null;
                }
            default:
                return super.slotClick(slot, par2, par3, player);
        }

        return null;
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
