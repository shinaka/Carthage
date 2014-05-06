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
    private ItemStack paperStackIn;
    private ItemStack paperStackOut;
    private ItemStack[] registerSlots;

    public TileEntityRegister()
    {

    }
    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if(slot == 0)
            return paperStackIn;
        return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        if(slot == 0)
            paperStackIn = stack;
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
    public int getInventoryStackLimit() {
        return 1;
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
