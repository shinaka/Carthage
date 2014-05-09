package com.shinaka.carthage.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by James on 5/8/2014.
 */
public class ItemLedger extends Item
{
    public ItemLedger()
    {
        setUnlocalizedName("Ledger");
        setCreativeTab(CreativeTabs.tabMisc);
        setTextureName("carthage:ledger");
    }
}
