package com.shinaka.carthage.client;

import com.shinaka.carthage.ContainerTradingPostUser;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by Jim on 5/11/2014.
 */
public class GuiTradingPostUser extends CustomGuiContainer
{
    protected TileEntityTradingPost tePost;
    protected EntityPlayer player;
    protected ArrayList<ItemStack> ledgerItems = new ArrayList<ItemStack>();

    public GuiTradingPostUser(InventoryPlayer inventory, TileEntityTradingPost te)
    {
        super(new ContainerTradingPostUser(inventory, te), 176, 166);
        tePost = te;
        player = inventory.player;
        ledgerItems = tePost.GetLedgerItemsAsStack();
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
        TextureManager texMgr = Minecraft.getMinecraft().getTextureManager();
        FontRenderer fontRenderer = this.mc.fontRenderer;
        ResourceLocation bg = new ResourceLocation("carthage", "textures/gui/container/tradingpost_owner.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bg);

        this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, 256, 256);

        int itemHeight = 8;
        final int itemSpace = 18;
        if(ledgerItems != null)
        {
            for (ItemStack item : ledgerItems)
            {
                IIcon icon = item.getIconIndex();
                ResourceLocation resourceLocation = texMgr.getResourceLocation(item.getItem().getSpriteNumber());
                Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);

                this.drawTexturedModelRectFromIcon(getGuiLeft() + 135, getGuiTop() + itemHeight, icon, 16, 16);

                itemHeight = itemHeight + itemSpace;
            }

            //We can't draw text until after we've rendered these (or else the textures rendered after get dimmed
            //So, we have to be a little sloppy.
            itemHeight = 8;
            for (ItemStack item : ledgerItems)
            {
                fontRenderer.drawString(Integer.toString(tePost.GetItemLedgerCost(item)), getGuiLeft() + 153, getGuiTop() + itemHeight + 4, 4210752);
                itemHeight = itemHeight + itemSpace;
            }
        }
        /*
        //Draw available item slots
        this.drawItemSlot(16, 16, 135, 8);
        this.drawItemSlot(16, 16, 135, 26);
        this.drawItemSlot(16, 16, 135, 44);
        this.drawItemSlot(16, 16, 135, 62);
        */
        //Draw our input slot
        this.drawItemSlot(16, 16, 12, 17);

        //Out Slot
        this.drawItemSlot(16, 16, 12, 62);

        fontRenderer.drawString("Sell", getGuiLeft() + 11, getGuiTop() + 7, 4210752);
        fontRenderer.drawString("Buy", getGuiLeft() + 11, getGuiTop() + 52, 4210752);

        fontRenderer.drawString("Available Credits", getGuiLeft() + 42, getGuiTop() + 27, 4210752);

        //Player's Credit Count
        fontRenderer.drawString(Integer.toString(tePost.GetCreditsForUser(player.getDisplayName())), getGuiLeft() + 74, getGuiTop() + 42, 4210752);
    }
}
