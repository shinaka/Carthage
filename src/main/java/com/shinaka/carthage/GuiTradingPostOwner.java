package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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


        //Draw internal inventories
        this.drawItemSlot(16, 16, 8, 63);
        this.drawItemSlot(16, 16, 25, 63);
        this.drawItemSlot(16, 16, 42, 63);
        this.drawItemSlot(16, 16, 59, 63);
        this.drawItemSlot(16, 16, 8, 46);
        this.drawItemSlot(16, 16, 25, 46);
        this.drawItemSlot(16, 16, 42, 46);
        this.drawItemSlot(16, 16, 59, 46);

        this.drawItemSlot(16, 16, 101, 63);
        this.drawItemSlot(16, 16, 118, 63);
        this.drawItemSlot(16, 16, 135, 63);
        this.drawItemSlot(16, 16, 152, 63);
        this.drawItemSlot(16, 16, 101, 46);
        this.drawItemSlot(16, 16, 118, 46);
        this.drawItemSlot(16, 16, 135, 46);
        this.drawItemSlot(16, 16, 152, 46);

        //Draw for sale slot
        this.drawItemSlot(16, 16, 33, 16);

        this.drawItemSlot(16, 16, 126, 16);



        FontRenderer fontRenderer = this.mc.fontRenderer;

        fontRenderer.drawString("Stock", getGuiLeft() + 8, getGuiTop() + 35, 4210752);
        fontRenderer.drawString("Received", getGuiLeft() + 101, getGuiTop() + 35, 4210752);

        fontRenderer.drawString("Ledger", getGuiLeft() + 117, getGuiTop() + 5, 4210752);
        fontRenderer.drawString("For Sale", getGuiLeft() + 20, getGuiTop() + 5, 4210752);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {

    }
    protected void drawItemSlot(int sizeX, int sizeY, int left, int top)
    {
        int x1 = getGuiLeft() + left;
        int y1 = getGuiTop() + top;
        int x2 = x1 + sizeX;
        int y2 = y1 + sizeY;
        this.drawGradientRect(x1, y1, x2, y2, 2120506433, 2120506433);
    }

    protected int getGuiLeft()
    {
        ScaledResolution res = new ScaledResolution( this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        return (res.getScaledWidth() - 176) / 2;
    }

    protected int getGuiTop()
    {
        ScaledResolution res = new ScaledResolution( this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        return (res.getScaledHeight() - 166) / 2;
    }
}
