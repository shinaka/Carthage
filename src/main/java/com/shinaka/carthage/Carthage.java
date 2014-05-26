package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import com.shinaka.carthage.blocks.TileEntityRegisterBlock;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import com.shinaka.carthage.blocks.TileEntityTradingPostBlock;
import com.shinaka.carthage.client.TileEntityTradingPostRenderer;
import com.shinaka.carthage.items.ItemLedger;
import com.shinaka.carthage.items.TradingPostItemBlock;
import com.shinaka.carthage.network.PacketPipeline;
import com.shinaka.carthage.network.RegisterLedgerPacket;
import com.shinaka.carthage.network.TradingPostPacket;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;

@Mod(modid = Carthage.MODID, version = Carthage.VERSION)
public class Carthage {
    public static final String MODID = "carthage";
    public static final String VERSION = "1.0";
    public static final PacketPipeline packetPipeline = new PacketPipeline();
    public static Carthage INSTANCE;
    public static Block tradingPostBlock;
    public static Block registerBlock;
    public static Item itemLedger;

    @SidedProxy(clientSide = "com.shinaka.carthage.client.ClientProxy", serverSide = "com.shinaka.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Carthage.INSTANCE = this;
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new CarthageGuiHandler());
        proxy.registerRenderers();
        tradingPostBlock = new TileEntityTradingPostBlock();
        GameRegistry.registerTileEntity(TileEntityTradingPost.class, "tileEntityTradingPost");
        GameRegistry.registerBlock(tradingPostBlock, TradingPostItemBlock.class, "Trading Post");

        registerBlock = new TileEntityRegisterBlock();
        GameRegistry.registerTileEntity(TileEntityRegister.class, "tileEntityRegister");
        GameRegistry.registerBlock(registerBlock, "Register");

        itemLedger = new ItemLedger();
        GameRegistry.registerItem(itemLedger, "Ledger");
        proxy.registerRecipes();
        packetPipeline.initialise();
        packetPipeline.registerPacket(RegisterLedgerPacket.class);
        packetPipeline.registerPacket(TradingPostPacket.class);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        packetPipeline.postInitialise();
    }
}