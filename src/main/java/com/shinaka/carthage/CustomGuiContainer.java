package com.shinaka.carthage;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * Created by James on 5/25/2014.
 */
public abstract class CustomGuiContainer extends GuiContainer
{
    private int _width;
    private int _height;

    public CustomGuiContainer(Container par1Container, int width, int height)
    {
        super(par1Container);
        _width = width;
        _height = height;
    }

    protected int getGuiLeft()
    {
        ScaledResolution res = new ScaledResolution( this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        return (res.getScaledWidth() - _width) / 2;
    }

    protected int getGuiTop()
    {
        ScaledResolution res = new ScaledResolution( this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        return (res.getScaledHeight() - _height) / 2;
    }

    protected void drawItemSlot(int sizeX, int sizeY, int left, int top)
    {
        int x1 = getGuiLeft() + left;
        int y1 = getGuiTop() + top;
        int x2 = x1 + sizeX;
        int y2 = y1 + sizeY;
        this.drawGradientRect(x1, y1, x2, y2, 2120506433, 2120506433);
    }

    protected void drawColoredRect(int sizeX, int sizeY, int left, int top, int color)
    {
        int x1 = getGuiLeft() + left;
        int y1 = getGuiTop() + top;
        int x2 = x1 + sizeX;
        int y2 = y1 + sizeY;
        this.drawGradientRect(x1, y1, x2, y2, color, color);
    }
}
