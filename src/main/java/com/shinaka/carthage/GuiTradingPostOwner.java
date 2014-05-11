package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * Created by Jim on 5/11/2014.
 */
public class GuiTradingPostOwner extends GuiContainer
{
    public GuiTradingPostOwner(InventoryPlayer inventory, TileEntityTradingPost te)
    {
        super(new ContainerTradingPostOwner(inventory, te));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

    }
}
