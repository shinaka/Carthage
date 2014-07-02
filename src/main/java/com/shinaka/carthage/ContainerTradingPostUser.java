package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;


/**
 * Created by Jim on 5/11/2014.
 */
public class ContainerTradingPostUser extends Container
{
    protected TileEntityTradingPost tePost;
    protected EntityPlayer player;

    public ContainerTradingPostUser(InventoryPlayer inventoryPlayer, TileEntityTradingPost te)
    {
        tePost = te;
        player = inventoryPlayer.player;

        //Sell Slot
        addSlotToContainer(new Slot(tePost, 18, 14, 62));

        //Buy Slot
        addSlotToContainer(new Slot(tePost, TileEntityTradingPost.tradedItemSlotIdx, 14, 107));

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

    @Override
    public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer player)
    {
        if(slot == 0)
        {
            if(player.inventory.getItemStack() != null)
            {
                ItemStack mouseItem = player.inventory.getItemStack();
                if(tePost.IsItemInLedger(mouseItem) && tePost.CanBuyItem(mouseItem))
                {
                    int cost = tePost.GetItemLedgerCost(mouseItem) * mouseItem.stackSize;
                    tePost.AddCreditsForUser(player.getDisplayName(), cost);
                    tePost.AddReceivedItem(mouseItem);
                    player.inventory.setItemStack(null);
                    return null;
                }
                return null;
            }
        }
        else if(slot == 1)
        {
            if(player.inventory.getItemStack() == null && tePost.GetHasSaleItem())
            {
                //Can the player afford at least one?
                if(tePost.HasEnoughCredits(player.getDisplayName(), tePost.getItemCost()))
                {
                    int availCredits = tePost.GetCreditsForUser(player.getDisplayName());
                    int itemCost = tePost.getItemCost();

                    //We know how many we want to buy now, but how many CAN we?
                    int totalToPurchase = availCredits / itemCost;
                    int totalAvail = tePost.GetAvailableSaleItemCount();
                    if(totalAvail < totalToPurchase)
                        totalToPurchase = totalAvail;

                    int totalCost = totalToPurchase * itemCost;
                    ItemStack stack = tePost.getStackInSlot(tePost.tradedItemSlotIdx).copy();
                    stack.stackSize = totalToPurchase;

                    tePost.SubtractCreditsForUser(player.getDisplayName(), totalCost);
                    tePost.RemoveSaleItemsByCount(totalToPurchase);
                    player.inventory.addItemStackToInventory(stack.copy());
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        return super.slotClick(slot, par2, par3, player);
    }
}
