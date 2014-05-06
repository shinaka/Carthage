package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * Created by James on 5/5/2014.
 */
public class GuiRegister extends GuiContainer
{
    protected GuiTextField item1txt;

    public GuiRegister(InventoryPlayer inventory, TileEntityRegister te)
    {
        super(new ContainerRegister(inventory, te));
    }

    @Override
    public void initGui()
    {
        item1txt = new GuiTextField(this.mc.fontRenderer, 2, 2, 103, 12);
        super.initGui();
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {
        FontRenderer fontRenderer = this.mc.fontRenderer;

        //draw text and stuff here
        //the parameters for drawString are: string, x, y, color
        fontRenderer.drawString("Paper", 8, 6, 4210752);
        //draws "Inventory" or your regional equivalent
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                                                   int par3) {
        //draw your Gui here, only thing you need to change is the path
        ResourceLocation bg = new ResourceLocation("carthage", "textures/gui/container/register.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bg);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
