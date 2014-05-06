package com.shinaka.carthage;

import com.shinaka.carthage.blocks.TileEntityRegister;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
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
    protected GuiTextField item2txt;
    protected GuiTextField item3txt;
    protected GuiTextField item4txt;

    public GuiRegister(InventoryPlayer inventory, TileEntityRegister te)
    {
        super(new ContainerRegister(inventory, te));

    }

    @Override
    public void initGui()
    {
        item1txt = new GuiTextField(this.mc.fontRenderer, -5, 70, 24, 12);
        item1txt.setMaxStringLength(32767);

        item2txt = new GuiTextField(this.mc.fontRenderer, 45, 70, 24, 12);
        item2txt.setMaxStringLength(32767);

        item3txt = new GuiTextField(this.mc.fontRenderer, 95, 70, 24, 12);
        item3txt.setMaxStringLength(32767);

        item4txt = new GuiTextField(this.mc.fontRenderer, 145, 70, 24, 12);
        item4txt.setMaxStringLength(32767);
        super.initGui();
    }

    @Override
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }

        if(item1txt.isFocused())
            item1txt.textboxKeyTyped(par1, par2);
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {
        FontRenderer fontRenderer = this.mc.fontRenderer;

        fontRenderer.drawString("Paper", 172, 170, 4210752);

        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 256 - 96 + 2, 4210752);
        item1txt.drawTextBox();
        item2txt.drawTextBox();
        item3txt.drawTextBox();
        item4txt.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                                                   int par3) {
        //draw your Gui here, only thing you need to change is the path
        ResourceLocation bg = new ResourceLocation("carthage", "textures/gui/container/register.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bg);

        this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, 256, 256);
    }

    protected int getGuiLeft()
    {
        ScaledResolution res = new ScaledResolution( this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        return (res.getScaledWidth() - 256) / 2;
    }

    protected int getGuiTop()
    {
        ScaledResolution res = new ScaledResolution( this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        return (res.getScaledHeight() - 128) / 2;
    }
}
