package com.shinaka.carthage.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by James on 4/28/2014.
 */
public class TileEntityTradingPost extends TileEntity implements IInventory
{
    protected ItemStack[] inventory;
    protected ItemStack tradedItem;
    protected String blockOwner;
    protected ItemStack ledgerStack;

    public TileEntityTradingPost()
    {
        inventory = new ItemStack[8];
    }

    public void setBlockOwner(String owner)
    {
        blockOwner = owner;
    }

    public String getBlockOwner() { return blockOwner; }
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        //Block Owner
        if(nbt.hasKey("blockOwner"))
            blockOwner = nbt.getString("blockOwner");

        //Deserialize the Inventory
        NBTTagList tagList = nbt.getTagList("Inventory", 10);
        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if(slot >= 0 && slot < getSizeInventory())
            {
                if( slot < 8)
                    inventory[i] = ItemStack.loadItemStackFromNBT(tag);
                else
                {
                    if(slot == 8)
                    {
                        ledgerStack = ItemStack.loadItemStackFromNBT(tag);
                    }
                    else if(slot == 9)
                    {
                        tradedItem = ItemStack.loadItemStackFromNBT(tag);
                    }
                }
            }
        }
        super.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        if(blockOwner != null && !blockOwner.isEmpty())
            nbt.setString("blockOwner", blockOwner);
        super.writeToNBT(nbt);

        NBTTagList itemList = new NBTTagList();
        for(int i = 0; i < inventory.length; ++i)
        {
            NBTTagCompound tag = new NBTTagCompound();
            ItemStack slot = inventory[i];
            if(slot == null)
                continue;
            tag.setByte("Slot", (byte) i);
            slot.writeToNBT(tag);
            itemList.appendTag(tag);
        }

        if(ledgerStack != null)
        {
            NBTTagCompound ledgerTag = new NBTTagCompound();
            ledgerTag.setByte("Slot", (byte) 8);
            ledgerStack.writeToNBT(ledgerTag);
            itemList.appendTag(ledgerTag);
        }

        if(tradedItem != null)
        {
            NBTTagCompound tradedTag = new NBTTagCompound();
            tradedTag.setByte("Slot", (byte) 9);
            tradedItem.writeToNBT(tradedTag);
            itemList.appendTag(tradedTag);
        }
        nbt.setTag("Inventory", itemList);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tagCompound);
    }
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    @Override
    public int getSizeInventory() {
        return inventory.length + 2;
    }

    @Override
    public ItemStack getStackInSlot(int idx)
    {
        if(idx < 8)
            return inventory[idx];
        else if(idx == 8)
            return ledgerStack;
        else
            return tradedItem;
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
    public void setInventorySlotContents(int var1, ItemStack var2) {

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
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
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
