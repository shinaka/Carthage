package com.shinaka.carthage.client;

import com.shinaka.carthage.Carthage;
import com.shinaka.carthage.ContainerRegister;
import com.shinaka.carthage.blocks.TileEntityRegister;
import com.shinaka.carthage.network.RegisterLedgerPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
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
    protected TileEntityRegister teRegister;

    public GuiRegister(InventoryPlayer inventory, TileEntityRegister te)
    {
        super(new ContainerRegister(inventory, te));
        teRegister = te;
    }

    @Override
    public void initGui()
    {
        xSize = 256;
        ySize = 256;
        //Create our Text Fields
        //We also need to create a button behind each text field to set focus

        item1txt = new GuiTextField(this.mc.fontRenderer, 30, 105, 32, 12);
        item1txt.setMaxStringLength(32767);
        item1txt.setText(Integer.toString(teRegister.slot1cost));
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, getGuiLeft() + 30, getGuiTop() + 40, 32, 12, ""));

        item2txt = new GuiTextField(this.mc.fontRenderer, 80, 105, 32, 12);
        item2txt.setMaxStringLength(32767);
        item2txt.setText(Integer.toString(teRegister.slot2cost));
        this.buttonList.add(new GuiButton(1, getGuiLeft() + 80, getGuiTop() + 40, 32, 12, ""));

        item3txt = new GuiTextField(this.mc.fontRenderer, 130, 105, 32, 12);
        item3txt.setMaxStringLength(32767);
        item3txt.setText(Integer.toString(teRegister.slot3cost));
        this.buttonList.add(new GuiButton(2, getGuiLeft() + 130, getGuiTop() + 40, 32, 12, ""));

        item4txt = new GuiTextField(this.mc.fontRenderer, 180, 105, 32, 12);
        item4txt.setMaxStringLength(32767);
        item4txt.setText(Integer.toString(teRegister.slot4cost));
        this.buttonList.add(new GuiButton(3, getGuiLeft() + 180, getGuiTop() + 40, 32, 12, ""));

        super.initGui();
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        switch(button.id)
        {
            case 0:
                item1txt.setEnabled(true);
                item1txt.setFocused(true);
                item2txt.setFocused(false);
                item3txt.setFocused(false);
                item4txt.setFocused(false);
                break;
            case 1:
                item2txt.setEnabled(true);
                item2txt.setFocused(true);
                item1txt.setFocused(false);
                item3txt.setFocused(false);
                item4txt.setFocused(false);
                break;
            case 2:
                item3txt.setEnabled(true);
                item3txt.setFocused(true);
                item1txt.setFocused(false);
                item2txt.setFocused(false);
                item4txt.setFocused(false);
                break;
            case 3:
                item4txt.setEnabled(true);
                item4txt.setFocused(true);
                item1txt.setFocused(false);
                item2txt.setFocused(false);
                item3txt.setFocused(false);
                break;
        }
    }

    @Override
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
        if ((par1 < 48 || par1 > 57) && par1 != 8 && par1 != 0)
            return;

        GuiTextField textField = null;
        if (item1txt.isFocused())
        {
            textField = item1txt;
        }
        if (item2txt.isFocused())
        {
            textField = item2txt;
        }
        if (item3txt.isFocused())
        {
            textField = item3txt;
        }
        if(item4txt.isFocused())
        {
            textField = item4txt;
        }

        int cost = 0;
        if(textField.getText().length() > 0)
        {
            cost = Integer.parseInt(textField.getText());
        }

        if(cost > 999)
        {
            cost = 999;
            textField.setText(Integer.toString(cost));
        }
        else
        {
            textField.textboxKeyTyped(par1, par2);
            if(textField.getText() == "")
                textField.setText("0");
            String txt = textField.getText();
            if(txt.length() > 1)
            {
                if(txt.startsWith("0"))
                {
                    textField.setText(txt.substring(1));
                }
            }
        }
        if(item1txt.getText().length() > 0)
            teRegister.slot1cost = Integer.parseInt(item1txt.getText());
        if(item2txt.getText().length() > 0)
            teRegister.slot2cost = Integer.parseInt(item2txt.getText());
        if(item3txt.getText().length() > 0)
            teRegister.slot3cost = Integer.parseInt(item3txt.getText());
        if(item4txt.getText().length() > 0)
            teRegister.slot4cost = Integer.parseInt(item4txt.getText());
        sendServerPacket();
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {
        FontRenderer fontRenderer = this.mc.fontRenderer;

        fontRenderer.drawString("Paper", 208, 210, 4210752);
        fontRenderer.drawString("Item 1", 32, 75, 4210752);
        fontRenderer.drawString("Item 2", 82, 75, 4210752);
        fontRenderer.drawString("Item 3", 132, 75, 4210752);
        fontRenderer.drawString("Item 4", 182, 75, 4210752);


        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 10, 133, 4210752);
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

    @SideOnly(Side.CLIENT)
    public void sendServerPacket()
    {
        RegisterLedgerPacket packet = new RegisterLedgerPacket(teRegister.xCoord, teRegister.yCoord, teRegister.zCoord, item1txt.getText(), item2txt.getText(), item3txt.getText(), item4txt.getText());
        Carthage.packetPipeline.sendToServer(packet);
    }
}
