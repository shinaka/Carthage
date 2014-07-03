package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

/**
 * Created by James on 5/3/2014.
 */
public class ContainerRegister extends Container
{
    protected TileEntityRegister teRegister;

    public ContainerRegister(InventoryPlayer inventoryPlayer, TileEntityRegister te)
    {
        teRegister = te;

        addSlotToContainer(new Slot(te, TileEntityRegister.slot1idx, 39, 85));
        addSlotToContainer(new Slot(te, TileEntityRegister.slot2idx, 89, 85));
        addSlotToContainer(new Slot(te, TileEntityRegister.slot3idx, 139, 85));
        addSlotToContainer(new Slot(te, TileEntityRegister.slot4idx, 189, 85));

        //Slot 4: Paper Holder
        addSlotToContainer(new Slot(te, 4, 214, 190));

        //Slot 5: Ledger Holder
        addSlotToContainer(new Slot(te, 5, 214, 152));

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
            case TileEntityRegister.slot1idx:
            case TileEntityRegister.slot2idx:
            case TileEntityRegister.slot3idx:
            case TileEntityRegister.slot4idx:
                if(player.inventory.getItemStack() != null && IsItemStackUnused(player.inventory.getItemStack()))
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
            case 5:
                if(player.inventory.getItemStack() == null)
                {
                    ItemStack ledger = CreateLedger();
                    if(ledger != null)
                    {
                        player.inventory.setItemStack(ledger);
                    }
                }
                break;
            default:
                return super.slotClick(slot, par2, par3, player);
        }

        return null;
    }

    private boolean IsItemStackUnused(ItemStack testItem)
    {
        //Item testItem = getSlot(slot).getStack().getItem();
        ArrayList<ItemStack> itemList = AddSlotsToArrayList();
        for(int i = 0; i < itemList.size(); ++i)
        {
            if(itemList.get(i) != null && itemList.get(i).getItem() == testItem.getItem())
            {
                ItemStack stack = itemList.get(i);
                if(stack.getItemDamage() == testItem.getItemDamage())
                    return false;
            }
        }

        return true;
    }

    private ArrayList<ItemStack> AddSlotsToArrayList()
    {
        ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
        for(int slot = 0; slot < 4; slot++)
        {
            if (getSlot(slot).getStack() != null && getSlot(slot).getStack().getItem() != null)
                itemList.add(getSlot(slot).getStack());
        }
        return itemList;
    }

    public ItemStack CreateLedger()
    {
        if(teRegister.CanCreateLedger())
        {
            ItemStack ledger = new ItemStack(Carthage.itemLedger);
            NBTTagCompound tagCompound = new NBTTagCompound();
            NBTTagList itemList = new NBTTagList();
            //Slot 0
            ItemStack slot0Stack = ((Slot)this.inventorySlots.get(0)).getStack();
            if(slot0Stack != null)
            {
                NBTTagCompound slotTag = new NBTTagCompound();
                slotTag.setByte("Slot", (byte) 0);
                slotTag.setByte("cost", (byte) teRegister.slot1cost);
                slot0Stack.writeToNBT(slotTag);
                itemList.appendTag(slotTag);
            }

            //Slot 1
            ItemStack slot1Stack = ((Slot)this.inventorySlots.get(1)).getStack();
            if(slot1Stack != null)
            {
                NBTTagCompound slotTag = new NBTTagCompound();
                slotTag.setByte("Slot", (byte) 1);
                slotTag.setByte("cost", (byte) teRegister.slot2cost);
                slot1Stack.writeToNBT(slotTag);
                itemList.appendTag(slotTag);
            }

            //Slot 2
            ItemStack slot2Stack = ((Slot)this.inventorySlots.get(2)).getStack();
            if(slot2Stack != null)
            {
                NBTTagCompound slotTag = new NBTTagCompound();
                slotTag.setByte("Slot", (byte) 2);
                slotTag.setByte("cost", (byte) teRegister.slot3cost);
                slot2Stack.writeToNBT(slotTag);
                itemList.appendTag(slotTag);
            }

            //Slot 3
            ItemStack slot3Stack = ((Slot)this.inventorySlots.get(3)).getStack();
            if(slot3Stack != null)
            {
                NBTTagCompound slotTag = new NBTTagCompound();
                slotTag.setByte("Slot", (byte) 3);
                slotTag.setByte("cost", (byte) teRegister.slot4cost);
                slot3Stack.writeToNBT(slotTag);
                itemList.appendTag(slotTag);
            }

            tagCompound.setTag("Ledger", itemList);
            ledger.stackTagCompound = tagCompound;

            //Reduce the Paper Stack
            ((Slot)this.inventorySlots.get(4)).decrStackSize(1);

            return ledger;
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
