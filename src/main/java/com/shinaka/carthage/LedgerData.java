package com.shinaka.carthage;

import net.minecraft.item.Item;

/**
 * Created by James on 5/18/2014.
 */
public class LedgerData
{
    protected Item item;
    protected int slot;
    protected int cost;

    public LedgerData(Item _item, int _slot, int _cost)
    {
        item = _item;
        slot = _slot;
        cost = _cost;
    }

    public Item GetItem() { return item; }
    public int GetSlot() { return slot; }
    public int GetCost() { return cost; }

    public void SetItem(Item _item) { item = _item; }
    public void SetSlot (int _slot) { slot = _slot; }
    public void SetCost (int _cost) { cost = _cost; }
}
