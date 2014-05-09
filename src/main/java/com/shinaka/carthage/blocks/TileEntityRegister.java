package com.shinaka.carthage.blocks;

import com.shinaka.carthage.Carthage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRegister extends TileEntity implements IInventory
{
    private ItemStack slot1;
    public int slot1cost = 100;

    private ItemStack slot2;
    public int slot2cost = 100;

    private ItemStack slot3;
    public int slot3cost = 100;

    private ItemStack slot4;
    public int slot4cost = 100;

    private ItemStack paperStack;
    private ItemStack ledgerSlot;

    public TileEntityRegister()
    {
        ledgerSlot = new ItemStack(Carthage.itemLedger);
    }

    public boolean CanCreateLedger()
    {
        return paperStack != null && paperStack.stackSize > 0 && ((slot1 != null) || (slot2 != null) || (slot3 != null) || (slot4 != null));
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        NBTTagList tagList = nbt.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < getSizeInventory())
            {
                switch(slot)
                {
                    case 0:
                        slot1 = ItemStack.loadItemStackFromNBT(tag);
                        break;
                    case 1:
                        slot2 = ItemStack.loadItemStackFromNBT(tag);
                        break;
                    case 2:
                        slot3 = ItemStack.loadItemStackFromNBT(tag);
                        break;
                    case 3:
                        slot4 = ItemStack.loadItemStackFromNBT(tag);
                        break;
                    case 4:
                        paperStack = ItemStack.loadItemStackFromNBT(tag);
                        break;
                }
            }
        }
    }

    public NBTTagCompound writeSlotItemToTag(int slot)
    {
        NBTTagCompound tag = new NBTTagCompound();
        ItemStack theSlot = null;
        switch(slot)
        {
            case 0:
                theSlot = slot1;
                break;
            case 1:
                theSlot = slot2;
                break;
            case 2:
                theSlot = slot3;
                break;
            case 3:
                theSlot = slot4;
                break;
            case 4:
                theSlot = paperStack;
                break;
        }

        if(theSlot == null)
            return null;

        tag.setByte("Slot", (byte) slot);
        theSlot.writeToNBT(tag);
        return tag;

    }
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        NBTTagList itemList = new NBTTagList();
        if(slot1 != null)
            itemList.appendTag(writeSlotItemToTag(0));
        if(slot2 != null)
            itemList.appendTag(writeSlotItemToTag(1));
        if(slot3 != null)
            itemList.appendTag(writeSlotItemToTag(2));
        if(slot3 != null)
            itemList.appendTag(writeSlotItemToTag(3));
        if(paperStack != null)
            itemList.appendTag(writeSlotItemToTag(4));

        nbt.setTag("Inventory", itemList);
    }

    @Override
    public int getSizeInventory() {
        return 6;
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
            case 5:
                if(paperStack == null)
                    return null;
                return ledgerSlot;
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
        if(slot == 5)
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("Out Slot");
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
            case 5:
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
