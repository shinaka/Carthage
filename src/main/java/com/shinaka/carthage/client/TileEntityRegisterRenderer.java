package com.shinaka.carthage.client;

import com.shinaka.carthage.models.ModelRegister;
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
public class TileEntityRegisterRenderer extends TileEntitySpecialRenderer
{
    private final ModelRegister model;
    protected ResourceLocation textures = (new ResourceLocation("carthage:textures/blocks/register.png"));
    public TileEntityRegisterRenderer()
    {
        this.model = new ModelRegister();
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

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render((Entity)null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
