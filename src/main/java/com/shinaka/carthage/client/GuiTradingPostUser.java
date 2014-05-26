package com.shinaka.carthage.client;

import com.shinaka.carthage.ContainerTradingPostUser;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Jim on 5/11/2014.
 */
public class GuiTradingPostUser extends CustomGuiContainer
{
    protected TileEntityTradingPost tePost;
    protected EntityPlayer player;

    public GuiTradingPostUser(InventoryPlayer inventory, TileEntityTradingPost te)
    {
        super(new ContainerTradingPostUser(inventory, te), 176, 166);
        tePost = te;
        player = inventory.player;
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

        //Draw our input slot
        this.drawItemSlot(16, 16, 12, 17);

        //Draw available item slots
        this.drawItemSlot(16, 16, 135, 8);
        this.drawItemSlot(16, 16, 135, 26);
        this.drawItemSlot(16, 16, 135, 44);
        this.drawItemSlot(16, 16, 135, 62);

        //Out Slot
        this.drawItemSlot(16, 16, 12, 62);

        FontRenderer fontRenderer = this.mc.fontRenderer;

        fontRenderer.drawString("Sell", getGuiLeft() + 11, getGuiTop() + 7, 4210752);
        fontRenderer.drawString("Buy", getGuiLeft() + 11, getGuiTop() + 52, 4210752);

        fontRenderer.drawString("Available Credits", getGuiLeft() + 42, getGuiTop() + 27, 4210752);

        //Player's Credit Count
        fontRenderer.drawString("100", getGuiLeft() + 74, getGuiTop() + 42, 4210752);

        //Credit Text
        fontRenderer.drawString("100", getGuiLeft() + 153, getGuiTop() + 12, 4210752);
        fontRenderer.drawString("100", getGuiLeft() + 153, getGuiTop() + 30, 4210752);
        fontRenderer.drawString("100", getGuiLeft() + 153, getGuiTop() + 48, 4210752);
        fontRenderer.drawString("100", getGuiLeft() + 153, getGuiTop() + 66, 4210752);

    }
}
