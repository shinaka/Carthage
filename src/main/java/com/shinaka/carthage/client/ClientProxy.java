package com.shinaka.carthage.client;

import com.shinaka.carthage.CommonProxy;
import com.shinaka.carthage.blocks.TileEntityRegister;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import cpw.mods.fml.client.registry.ClientRegistry;

/**
 * Created by James on 4/29/2014.
 */
public class ClientProxy extends CommonProxy
{
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTradingPost.class, new TileEntityTradingPostRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRegister.class, new TileEntityRegisterRenderer());
    }
}
