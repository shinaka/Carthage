package com.shinaka.carthage.client;

import com.shinaka.carthage.models.ModelTradingPost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by James on 4/28/2014.
 */
public class TileEntityTradingPostRenderer extends TileEntitySpecialRenderer
{
    private final ModelTradingPost model;

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
        ResourceLocation textures = (new ResourceLocation("carthage:textures/blocks/tradingpost.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render((Entity)null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        Tessellator tessellator = Tessellator.instance;
        ResourceLocation testIcon = (new ResourceLocation("textures/items/emerald.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(testIcon);
        GL11.glTranslated(x, y+0.7f, z + 0.5f);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0, 0, 0, 0, 0);
        tessellator.addVertexWithUV(0, 1, 0, 0, 1);
        tessellator.addVertexWithUV(1, 1, 0, 1, 1);
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);

        tessellator.addVertexWithUV(0, 0, 0, 0, 0);//switch 2nd and 4th or 1th and 3rd to see both planes OR disable face culling <- again google that if you dont know what it means.make sure to re-enable face culling after you're done drawing
        tessellator.addVertexWithUV(1, 0, 0, 1, 0);
        tessellator.addVertexWithUV(1, 1, 0, 1, 1);
        tessellator.addVertexWithUV(0, 1, 0, 0, 1);

        tessellator.draw();
        GL11.glPopMatrix();
        /*
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        ResourceLocation testIcon = (new ResourceLocation("carthage:textures/blocks/tradingpost.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(testIcon);
        Block block = Blocks.planks;
        float f = 0.0625f;
        float f1 = 0.75f;
        float f2 = f1 / 2.0f;
        GL11.glPushMatrix();*/

    }
}
