package com.shinaka.carthage.blocks;

import com.shinaka.carthage.Carthage;
import com.shinaka.carthage.LedgerData;
import com.shinaka.carthage.network.LedgerStatusPacket;
import com.shinaka.carthage.network.RegisterLedgerPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import joptsimple.util.KeyValuePair;
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
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 4/28/2014.
 */
public class TileEntityTradingPost extends TileEntity implements IInventory
{
    protected ItemStack[] inventory;
    protected ItemStack[] received;
    protected int itemCost = 100;
    protected ItemStack tradedItem;
    protected String blockOwner;
    protected ItemStack ledgerStack;

    public static final int ledgerSlotIdx = 16;
    public static final int tradedItemSlotIdx = 17;

    protected Boolean bHasLedger = false;
    protected Boolean bHasSaleItem = false;
    protected ArrayList<LedgerData> ledgerData;

    protected Map<String, Integer> balanceSheet = new HashMap<String, Integer>();
    public TileEntityTradingPost()
    {
        inventory = new ItemStack[8];
        received = new ItemStack[8];
    }

    public void setBlockOwner(String owner)
    {
        blockOwner = owner;
    }

    public String getBlockOwner() { return blockOwner; }

    private void ReadBalanceSheet(NBTTagCompound nbt)
    {
        balanceSheet.clear();
        NBTTagList tagList = nbt.getTagList("BalanceSheet", 10);
        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            String username = tag.getString("Username");
            int credits = tag.getInteger("Credits");
            balanceSheet.put(username, credits);
        }
    }

    private void WriteBalanceSheet(NBTTagCompound nbt)
    {
        NBTTagList tagList = new NBTTagList();

        for(String key : balanceSheet.keySet())
        {
            int credits = balanceSheet.get(key);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("Credits", credits);
            tag.setString("Username", key);
            tagList.appendTag(tag);
        }

        nbt.setTag("BalanceSheet", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        //Block Owner
        if(nbt.hasKey("blockOwner"))
            blockOwner = nbt.getString("blockOwner");

        if(nbt.hasKey("itemCost"))
            itemCost = nbt.getInteger("itemCost");

        ReadBalanceSheet(nbt);
        //Deserialize the Inventory
        //And CLEAR THE GOD DAMNED INVENTORY BEFORE YOU DO IT, IDIOT
        inventory = new ItemStack[8];
        received = new ItemStack[8];
        NBTTagList tagList = nbt.getTagList("Inventory", 10);
        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if(slot >= 0 && slot < getSizeInventory())
            {
                if( slot < 8)
                    inventory[i] = ItemStack.loadItemStackFromNBT(tag);
                else if(slot < 16)
                {
                    received[i - inventory.length] = ItemStack.loadItemStackFromNBT(tag);
                }
                else
                {
                    if(slot == ledgerSlotIdx)
                    {
                        ledgerStack = ItemStack.loadItemStackFromNBT(tag);
                        if(ledgerStack != null && ledgerStack.getItem() != null && ledgerStack.getItem() == Carthage.itemLedger)
                            SetHasLedger(true);
                        else
                            SetHasLedger(false);
                    }
                    else if(slot == tradedItemSlotIdx)
                    {
                        tradedItem = ItemStack.loadItemStackFromNBT(tag);
                        SetHasSaleItem(tradedItem != null);
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
        if(itemCost > 0)
            nbt.setInteger("itemCost", itemCost);
        else
            nbt.setInteger("itemCost", 1);

        WriteBalanceSheet(nbt);
        NBTTagList itemList = new NBTTagList();
        for(int i = 0; i < inventory.length; ++i)
        {
            NBTTagCompound tag = new NBTTagCompound();
            ItemStack slot = inventory[i];
            tag.setByte("Slot", (byte) i);
            //I'm a damned idiot. We need to actually send empty slots in the packet and let the client
            //create NULLed item slots, or else client info will always be stale
            if(slot != null)
                slot.writeToNBT(tag);
            itemList.appendTag(tag);
        }

        for(int i = 0; i < received.length; ++i)
        {
            NBTTagCompound tag = new NBTTagCompound();
            ItemStack slot = received[i];
            tag.setByte("Slot", (byte) (i + inventory.length));
            if(slot != null)
                slot.writeToNBT(tag);
            itemList.appendTag(tag);
        }

        NBTTagCompound ledgerTag = new NBTTagCompound();
        ledgerTag.setByte("Slot", (byte) ledgerSlotIdx);
        if(ledgerStack != null)
            ledgerStack.writeToNBT(ledgerTag);
        itemList.appendTag(ledgerTag);

        NBTTagCompound tradedTag = new NBTTagCompound();
        tradedTag.setByte("Slot", (byte) tradedItemSlotIdx);
        if(tradedItem != null)
            tradedItem.writeToNBT(tradedTag);
        itemList.appendTag(tradedTag);

        nbt.setTag("Inventory", itemList);
        super.writeToNBT(nbt);
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
        return inventory.length + received.length + 3;
    }

    @Override
    public ItemStack getStackInSlot(int idx)
    {
        if(idx < 8)
            return inventory[idx];
        else if(idx < 16)
            return received[idx - inventory.length];
        else if(idx == ledgerSlotIdx)
            return ledgerStack;
        else if(idx == tradedItemSlotIdx)
            return tradedItem;
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int quantity)
    {
        ItemStack stack;
        if(slot < 8)
            stack = inventory[slot];
        else if(slot < 16)
            stack =  received[slot - inventory.length];
        else if(slot == ledgerSlotIdx)
            stack = ledgerStack;
        else if(slot == tradedItemSlotIdx)
            stack = tradedItem;
        else
            stack = null;

        if(stack != null)
        {
            ItemStack returnStack = stack.copy();
            returnStack.stackSize = quantity;

            if(stack.stackSize - quantity > 0)
            {
                stack.stackSize = stack.stackSize - quantity;
            }
            else
            {
                if(slot < 8)
                    inventory[slot] = null;
                else if(slot < 16)
                    received[slot - inventory.length] = null;
                else if(slot == ledgerSlotIdx)
                {
                    SetHasLedger(false);
                    LedgerStatusPacket packet = new LedgerStatusPacket(xCoord, yCoord, zCoord, GetHasLedger());
                    Carthage.packetPipeline.sendToAll(packet);
                    ledgerStack = null;
                }
                else
                {
                    SetHasSaleItem(false);
                    tradedItem = null;
                }
            }

            //Because this TE's inventory affects its rendering, we HAVE to mark it as Dirty any time it changes
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return returnStack;
        }
        //Because this TE's inventory affects its rendering, we HAVE to mark it as Dirty any time it changes
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int idx, ItemStack var2)
    {
        if(idx < 8)
            inventory[idx] = var2;
        else if(idx < 16)
            received[idx - inventory.length] = var2;
        else if(idx == ledgerSlotIdx)
        {
            ledgerStack = var2;
            if(var2 == null)
                SetHasLedger(false);
            else
                SetHasLedger(true);
            LedgerStatusPacket packet = new LedgerStatusPacket(xCoord, yCoord, zCoord, GetHasLedger());
            Carthage.packetPipeline.sendToAll(packet);

        }
        else if(idx == tradedItemSlotIdx)
        {
            tradedItem = var2;
            if(var2 == null)
                SetHasSaleItem(false);
            else
                SetHasSaleItem(true);
        }

        //The object is "Dirty" at this point, so we need to let Forge know to force the update
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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

    public boolean IsItemInLedger(ItemStack testItem)
    {
        if(bHasLedger && ledgerData.size() > 0)
        {
            for(LedgerData ledgerItem : ledgerData)
            {
                ItemStack iStack = ledgerItem.GetItemStack();
                if(iStack.getItem() == testItem.getItem() && iStack.getItemDamage() == testItem.getItemDamage())
                    return true;
            }
        }
        return false;
    }

    public int GetItemLedgerCost(ItemStack testItem)
    {
        if(bHasLedger)
        {
            for(LedgerData ledgerItem : ledgerData)
            {
                ItemStack iStack = ledgerItem.GetItemStack();
                if(iStack.getItem() == testItem.getItem() && iStack.getItemDamage() == testItem.getItemDamage())
                {
                    return ledgerItem.GetCost();
                }
            }
        }
        return -1;
    }

    public void SetHasLedger(boolean b)
    {
        bHasLedger = b;
        if(b == true)
            InitLedgerItems();
    }

    public boolean GetHasLedger()
    {
        return bHasLedger;
    }

    public boolean GetHasSaleItem()
    {
        return bHasSaleItem;
    }

    public void SetHasSaleItem(boolean b)
    {
        bHasSaleItem = b;
        if(b == true)
            InitLedgerItems();
    }

    public void RemoveSaleItemsByCount(int count)
    {
        for(int i = 0; i < inventory.length; ++i)
        {
            ItemStack item = inventory[i];
            if(item != null && item.getItem() == tradedItem.getItem())
            {
                if(item.stackSize >= count)
                {
                    int toRemove = item.stackSize - count;
                    item.stackSize = toRemove;
                    if(item.stackSize == 0)
                        inventory[i] = null;
                    return;
                }
                else
                {
                    count = count - item.stackSize;
                    inventory[i] = null;
                }
            }
        }
    }

    public void AddReceivedItem(ItemStack stack)
    {
        //First, do we already have some of this item?
        for(ItemStack item : received)
        {
            if(item != null && item.getItem() == stack.getItem())
            {
                if(item.stackSize < item.getMaxStackSize())
                {
                    int toAdd = item.getMaxStackSize() - item.stackSize;
                    if(stack.stackSize <= toAdd)
                    {
                        item.stackSize = item.stackSize + stack.stackSize;
                        return;
                    }
                    else
                    {
                        item.stackSize = item.getMaxStackSize();
                        stack.stackSize = stack.stackSize - toAdd;
                    }
                }
            }
        }

        if(stack.stackSize > 0)
        {
            //Find an empty slot
            for(int i = 0; i < received.length; ++i)
            {
                if(received[i] == null)
                {
                    received[i] = stack.copy();
                    return;
                }
            }
        }
    }

    public boolean CanBuyItem(ItemStack item)
    {
        int availSlots = 0;
        for(int i = 0; i < received.length; ++i)
        {
            if(received[i] == null)
                return true;
            if(received[i].getItem() == item.getItem())
            {
                int freeSlots = (received[i].getMaxStackSize() - received[i].stackSize);
                if(freeSlots > 0)
                    availSlots = availSlots + freeSlots;
            }
        }
        return item.stackSize <= availSlots;
    }

    public void InitLedgerItems()
    {
        ArrayList<LedgerData> ledgerItems = new ArrayList<LedgerData>();
        if(bHasLedger && ledgerStack != null)
        {
            NBTTagCompound tagCompound = ledgerStack.getTagCompound();
            if(tagCompound != null)
            {
                NBTTagList itemList = tagCompound.getTagList("Ledger", 10);
                if(itemList != null)
                {
                    for(int i = 0; i < itemList.tagCount(); ++i)
                    {
                        NBTTagCompound slotTag = itemList.getCompoundTagAt(i);
                        if(slotTag != null)
                        {
                            int slot = slotTag.getByte("Slot");
                            ItemStack stack = ItemStack.loadItemStackFromNBT(slotTag);
                            int cost = slotTag.getByte("cost");
                            LedgerData ledger = new LedgerData(stack, slot, cost);
                            ledgerItems.add(ledger);
                        }
                    }

                    ledgerData = ledgerItems;
                }
            }
        }
    }

    public int GetCreditsForUser(String username)
    {
        InitUserBalanceSheet(username);
        return balanceSheet.get(username);
    }

    public int AddCreditsForUser(String username, int credits)
    {
        InitUserBalanceSheet(username);
        balanceSheet.put(username, balanceSheet.get(username) + credits);
        return balanceSheet.get(username);
    }

    public boolean HasEnoughCredits(String username, int credits)
    {
        InitUserBalanceSheet(username);
        return balanceSheet.get(username) >= credits;
    }

    public int SubtractCreditsForUser(String username, int credits)
    {
        InitUserBalanceSheet(username);
        if(HasEnoughCredits(username, credits))
        {
            balanceSheet.put(username, balanceSheet.get(username) - credits);
        }
        else
        {
            //Don't allow negative credits; this should never happen anyhow.
            balanceSheet.put(username, 0);
        }
        return balanceSheet.get(username);
    }

    public void InitUserBalanceSheet(String username)
    {
        if(!balanceSheet.containsKey(username))
        {
            balanceSheet.put(username, 0);
        }
    }

    public ArrayList<LedgerData> GetLedgerData()
    {
        return ledgerData;
    }

    public ArrayList<ItemStack> GetLedgerItemsAsStack()
    {
        if(ledgerData == null)
            return null;
        ArrayList<ItemStack> returnList = new ArrayList<ItemStack>();
        for(LedgerData ld : ledgerData)
        {
            if(ld.GetItemStack() != null)
            {
                returnList.add(ld.GetItemStack());
            }
        }

        return returnList;
    }

    public void setItemCost(int _itemCost)
    {
        itemCost = _itemCost;
    }

    public int getItemCost() { return itemCost; }

    public int GetAvailableSaleItemCount()
    {
        int count = -1;
        if(bHasSaleItem)
        {
            count = 0;
            for(ItemStack item : inventory)
            {
                if(item != null && item.getItem() == tradedItem.getItem())
                {
                    count = count + item.stackSize;
                }
            }
        }

        return count;
    }

    public void SendLedgerStatusPacket()
    {

    }
}
