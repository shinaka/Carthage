package com.shinaka.carthage.items;

import com.shinaka.carthage.LedgerData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List infoList, boolean par4)
    {
        infoList = getTooltipTextSimple(par1ItemStack, infoList);
    }

    public List getTooltipTextSimple(ItemStack itemStack, List infoList)
    {
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if(tagCompound != null)
        {
            NBTTagList itemList = tagCompound.getTagList("Ledger", 10);
            if(itemList != null)
            {
                for(int i = 0; i < itemList.tagCount(); ++i) {
                    NBTTagCompound slotTag = itemList.getCompoundTagAt(i);
                    if (slotTag != null) {
                        ItemStack stack = ItemStack.loadItemStackFromNBT(slotTag);
                        int cost = slotTag.getByte("cost");
                        infoList.add(stack.getDisplayName() + "(" + Integer.toString(cost) + ")");
                    }
                }
            }
        }
        return infoList;
    }
}
