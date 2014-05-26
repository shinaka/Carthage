package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Jim on 5/11/2014.
 */
public class GuiTradingPostUser extends CustomGuiContainer
{
    protected TileEntityTradingPost tePost;

    public GuiTradingPostUser(InventoryPlayer inventory, TileEntityTradingPost te)
    {
        super(new ContainerTradingPostUser(inventory, te), 176, 166);
        tePost = te;
    }

    @Override
    public void initGui()
    {
        xSize = 180;
        ySize = 256;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        ResourceLocation bg = new ResourceLocation("carthage", "textures/gui/container/tradingpost_owner.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bg);

        this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, 256, 256);
    }
}
