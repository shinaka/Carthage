package com.shinaka.carthage;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 5/18/2014.
 */
public class LedgerData
{
    protected ItemStack itemStack;
    protected int slot;
    protected int cost;

    public LedgerData(ItemStack _item, int _slot, int _cost)
    {
        itemStack = _item;
        slot = _slot;
        cost = _cost;
    }

    public Item GetItem() { return itemStack.getItem(); }
    public ItemStack GetItemStack() { return itemStack; }
    public int GetSlot() { return slot; }
    public int GetCost() { return cost; }

    public void SetItem(ItemStack _item) { itemStack = _item; }
    public void SetSlot (int _slot) { slot = _slot; }
    public void SetCost (int _cost) { cost = _cost; }
}
