package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityTradingPostBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by James on 4/29/2014.
 */
public class CommonProxy
{
    public void registerRenderers()
    {

    }

    public void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(Carthage.tradingPostBlock), new Object[]{
                "SSS","TWT","WWW",
                'S', Blocks.wooden_slab,
                'T', Items.stick,
                'W', Blocks.planks} );
        GameRegistry.addRecipe(new ItemStack(Carthage.registerBlock), new Object[]{
                "IGI","III","IPS",
                'I', Items.iron_ingot,
                'G', Blocks.glass,
                'P', Blocks.planks,
                'S', Items.stick});
    }
}
