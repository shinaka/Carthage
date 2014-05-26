package com.shinaka.carthage.client;

import com.shinaka.carthage.LedgerData;
import com.shinaka.carthage.blocks.TileEntityTradingPost;
import com.shinaka.carthage.models.ModelTradingPost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by James on 4/28/2014.
 */
public class TileEntityTradingPostRenderer extends TileEntitySpecialRenderer
{
    private final ModelTradingPost model;
    protected ResourceLocation textures = (new ResourceLocation("carthage:textures/blocks/tradingpost.png"));
    public TileEntityTradingPostRenderer()
    {
        this.model = new ModelTradingPost();
    }

    private void adjustRotatePivotViaMeta(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        GL11.glPushMatrix();
        GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        //GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render((Entity)null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        //GL11.glPopMatrix();

        //Draw our Buying items, if we have any
        TileEntityTradingPost tePost = (TileEntityTradingPost) var1;
        if(tePost.GetHasLedger() == false)
            return;
        ArrayList<LedgerData> itemList = tePost.GetLedgerData();

        TextureManager texMgr = Minecraft.getMinecraft().getTextureManager();
        Tessellator tessellator = Tessellator.instance;

        //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glPushMatrix();
        GL11.glTranslated(0.03f, 0.50f, 0.375f );
        if(itemList.size() > 0 && itemList.size() <= 4) {
            for (int i = 0; i < itemList.size(); ++i)
            {
                if (itemList.get(i) == null)
                    continue;
                GL11.glPushMatrix();
                //Item item = itemList.get(i).GetItem();
                ItemStack itemStack = itemList.get(i).GetItemStack();
                ResourceLocation resourceLocation = texMgr.getResourceLocation(itemStack.getItem().getSpriteNumber());

                Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);

                Vector3f vOffset = new Vector3f();

                if (i == 0) {
                    vOffset.set(0.5f, 0.5f);
                } else if (i == 1) {
                    vOffset.set(0.5f, 0.15f);
                } else if (i == 2) {
                    vOffset.set(0.15f, 0.5f);
                } else {
                    vOffset.set(0.15f, 0.15f);
                }

                GL11.glTranslated(x + vOffset.getX(), y + vOffset.getY(), z + 0.5f);
                //GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

                IIcon icon = itemStack.getIconIndex();
                renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
                GL11.glPopMatrix();
            }
        }
        //Draw For Sale Item
        if(tePost.GetHasSaleItem())
        {
            ItemStack stack = tePost.getStackInSlot(TileEntityTradingPost.tradedItemSlotIdx);
            if(stack != null)
            {
                GL11.glPushMatrix();
                GL11.glTranslated(x + 0.23f,y + 0.125f,z - 0.125f);
                GL11.glScaled(1.6f,1.0f,1.6f);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                IIcon icon = stack.getIconIndex();
                ResourceLocation resourceLocation = texMgr.getResourceLocation(stack.getItem().getSpriteNumber());
                Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
                renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(),
                icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();
    }

    public void renderItemIn2D (Tessellator par0Tessellator, float par1, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        GL11.glScaled(0.3f,0.3f,1.0f);
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double) par1, (double) par4);
        par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double) par3, (double) par4);
        par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double) par3, (double) par2);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double) par1, (double) par2);
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, (double) (0.0F - par7), (double) par1, (double) par2);
        par0Tessellator.addVertexWithUV(1.0D, 1.0D, (double) (0.0F - par7), (double) par3, (double) par2);
        par0Tessellator.addVertexWithUV(1.0D, 0.0D, (double) (0.0F - par7), (double) par3, (double) par4);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, (double) (0.0F - par7), (double) par1, (double) par4);
        par0Tessellator.draw();
        float f5 = 0.5F * (par1 - par3) / (float) par5;
        float f6 = 0.5F * (par4 - par2) / (float) par6;
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        int k;
        float f7;
        float f8;

        for (k = 0; k < par5; ++k)
        {
            f7 = (float) k / (float) par5;
            f8 = par1 + (par3 - par1) * f7 - f5;
            par0Tessellator.addVertexWithUV((double) f7, 0.0D, (double) (0.0F - par7), (double) f8, (double) par4);
            par0Tessellator.addVertexWithUV((double) f7, 0.0D, 0.0D, (double) f8, (double) par4);
            par0Tessellator.addVertexWithUV((double) f7, 1.0D, 0.0D, (double) f8, (double) par2);
            par0Tessellator.addVertexWithUV((double) f7, 1.0D, (double) (0.0F - par7), (double) f8, (double) par2);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);
        float f9;

        for (k = 0; k < par5; ++k)
        {
            f7 = (float) k / (float) par5;
            f8 = par1 + (par3 - par1) * f7 - f5;
            f9 = f7 + 1.0F / (float) par5;
            par0Tessellator.addVertexWithUV((double) f9, 1.0D, (double) (0.0F - par7), (double) f8, (double) par2);
            par0Tessellator.addVertexWithUV((double) f9, 1.0D, 0.0D, (double) f8, (double) par2);
            par0Tessellator.addVertexWithUV((double) f9, 0.0D, 0.0D, (double) f8, (double) par4);
            par0Tessellator.addVertexWithUV((double) f9, 0.0D, (double) (0.0F - par7), (double) f8, (double) par4);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (k = 0; k < par6; ++k)
        {
            f7 = (float) k / (float) par6;
            f8 = par4 + (par2 - par4) * f7 - f6;
            f9 = f7 + 1.0F / (float) par6;
            par0Tessellator.addVertexWithUV(0.0D, (double) f9, 0.0D, (double) par1, (double) f8);
            par0Tessellator.addVertexWithUV(1.0D, (double) f9, 0.0D, (double) par3, (double) f8);
            par0Tessellator.addVertexWithUV(1.0D, (double) f9, (double) (0.0F - par7), (double) par3, (double) f8);
            par0Tessellator.addVertexWithUV(0.0D, (double) f9, (double) (0.0F - par7), (double) par1, (double) f8);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for (k = 0; k < par6; ++k)
        {
            f7 = (float) k / (float) par6;
            f8 = par4 + (par2 - par4) * f7 - f6;
            par0Tessellator.addVertexWithUV(1.0D, (double) f7, 0.0D, (double) par3, (double) f8);
            par0Tessellator.addVertexWithUV(0.0D, (double) f7, 0.0D, (double) par1, (double) f8);
            par0Tessellator.addVertexWithUV(0.0D, (double) f7, (double) (0.0F - par7), (double) par1, (double) f8);
            par0Tessellator.addVertexWithUV(1.0D, (double) f7, (double) (0.0F - par7), (double) par3, (double) f8);
        }

        par0Tessellator.draw();
    }
}
