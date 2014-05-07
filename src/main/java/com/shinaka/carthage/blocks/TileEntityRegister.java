package com.shinaka.carthage.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by James on 5/3/2014.
 */
public class TileEntityRegister extends TileEntity implements IInventory
{
    private ItemStack slot1;
    private ItemStack slot2;
    private ItemStack slot3;
    private ItemStack slot4;
    private ItemStack paperStack;
    private ItemStack[] registerSlots;

    public TileEntityRegister()
    {

    }
    @Override
    public int getSizeInventory() {
        return 5;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        switch(slot)
        {
            case 0:
                return slot1;
            case 1:
                return slot2;
            case 2:
                return slot3;
            case 3:
                return slot4;
            case 4:
                return paperStack;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int quantity)
    {
        if(slot == 4 && paperStack != null)
        {
            ItemStack stack = paperStack.copy();
            stack.stackSize = quantity;

            if(paperStack.stackSize - quantity > 0)
            {
                paperStack.stackSize = paperStack.stackSize - quantity;
            }
            else
            {
                paperStack = null;
            }
            return stack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        switch(slot)
        {
            case 0:
                slot1 = stack;
                break;
            case 1:
                slot2 = stack;
                break;
            case 2:
                slot3 = stack;
                break;
            case 3:
                slot4 = stack;
                break;
            case 4:
                paperStack = stack;
                break;
        }
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return false;
    }
}
