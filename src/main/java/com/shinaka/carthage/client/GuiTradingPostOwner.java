package com.shinaka.carthage.client;

import com.shinaka.carthage.Carthage;
import com.shinaka.carthage.ContainerTradingPostOwner;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import com.shinaka.carthage.network.TradingPostPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Jim on 5/11/2014.
 */
public class GuiTradingPostOwner extends CustomGuiContainer
{
    protected GuiTextField costTextField;
    protected TileEntityTradingPost tePost;

    public GuiTradingPostOwner(InventoryPlayer inventory, TileEntityTradingPost te)
    {
        super(new ContainerTradingPostOwner(inventory, te), 176, 166);
        tePost = te;
    }

    @Override
    public void initGui()
    {
        xSize = 180;
        ySize = 256;

        costTextField = new GuiTextField(this.mc.fontRenderer, 43, 63, 26, 12);
        costTextField.setMaxStringLength(3);
        costTextField.setText(Integer.toString(tePost.getItemCost()));
        costTextField.setFocused(true);
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        ResourceLocation bg = new ResourceLocation("carthage", "textures/gui/container/tradingpost_owner.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bg);

        this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, 256, 256);

        //Draw Section seperators
        this.drawColoredRect(74, 1, 4, 34, -1110506433);
        this.drawColoredRect(74, 1, 98, 34, -1110506433);

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
        this.drawItemSlot(16, 16, 16, 16);

        this.drawItemSlot(16, 16, 126, 16);



        FontRenderer fontRenderer = this.mc.fontRenderer;

        fontRenderer.drawString("Stock", getGuiLeft() + 8, getGuiTop() + 37, 4210752);
        fontRenderer.drawString("Received", getGuiLeft() + 101, getGuiTop() + 37, 4210752);

        fontRenderer.drawString("Ledger", getGuiLeft() + 117, getGuiTop() + 5, 4210752);
        fontRenderer.drawString("For Sale", getGuiLeft() + 20, getGuiTop() + 5, 4210752);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {
        costTextField.drawTextBox();
    }

    @Override
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        if ((par1 < 48 || par1 > 57) && par1 != 8 && par1 != 0)
            return;
        int cost = 0;
        if(costTextField.getText().length() > 0)
        {
            cost = Integer.parseInt(costTextField.getText());
        }

        if(cost > 999)
        {
            cost = 999;
            costTextField.setText(Integer.toString(cost));
        }
        else
        {
            costTextField.textboxKeyTyped(par1, par2);
            if(costTextField.getText() == "")
                costTextField.setText("1");
            String txt = costTextField.getText();
            if(txt.length() >= 1)
            {
                if(txt.startsWith("0"))
                {
                    costTextField.setText(txt.substring(1));
                }
            }
        }

        if(costTextField.getText().length() > 0)
            tePost.setItemCost(Integer.parseInt(costTextField.getText()));

        sendServerPacket();
    }

    @SideOnly(Side.CLIENT)
    public void sendServerPacket()
    {
        TradingPostPacket packet = new TradingPostPacket(tePost.xCoord, tePost.yCoord, tePost.zCoord, tePost.getItemCost());
        Carthage.packetPipeline.sendToServer(packet);
    }
}
