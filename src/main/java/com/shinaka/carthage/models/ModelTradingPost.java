package com.shinaka.carthage.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
/**
 * Created by James on 4/28/2014.
 */
public class ModelTradingPost extends ModelBase
{
    ModelRenderer BottomBase;
    ModelRenderer Leg3;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg4;
    ModelRenderer LeftMid;
    ModelRenderer RightMid;
    ModelRenderer BackLeft;
    ModelRenderer TopBase;
    ModelRenderer Platform;
    ModelRenderer BackMid;
    ModelRenderer BackTop;
    ModelRenderer BackRight;
    ModelRenderer TopTop;

    public ModelTradingPost()
    {
        textureWidth = 64;
        textureHeight = 64;

        BottomBase = new ModelRenderer(this, 0, 0);
        BottomBase.addBox(0F, 0F, 0F, 16, 2, 16);
        BottomBase.setRotationPoint(-8F, 20F, -8F);
        BottomBase.setTextureSize(64, 64);
        BottomBase.mirror = true;
        setRotation(BottomBase, 0F, 0F, 0F);
        Leg3 = new ModelRenderer(this, 36, 23);
        Leg3.addBox(0F, 0F, 0F, 2, 2, 2);
        Leg3.setRotationPoint(6F, 22F, -8F);
        Leg3.setTextureSize(64, 64);
        Leg3.mirror = true;
        setRotation(Leg3, 0F, 0F, 0F);
        Leg1 = new ModelRenderer(this, 36, 23);
        Leg1.addBox(0F, 0F, 0F, 2, 2, 2);
        Leg1.setRotationPoint(-8F, 22F, 6F);
        Leg1.setTextureSize(64, 64);
        Leg1.mirror = true;
        setRotation(Leg1, 0F, 0F, 0F);
        Leg2 = new ModelRenderer(this, 36, 23);
        Leg2.addBox(0F, 0F, 0F, 2, 2, 2);
        Leg2.setRotationPoint(-8F, 22F, -8F);
        Leg2.setTextureSize(64, 64);
        Leg2.mirror = true;
        setRotation(Leg2, 0F, 0F, 0F);
        Leg4 = new ModelRenderer(this, 36, 23);
        Leg4.addBox(0F, 0F, 0F, 2, 2, 2);
        Leg4.setRotationPoint(6F, 22F, 6F);
        Leg4.setTextureSize(64, 64);
        Leg4.mirror = true;
        setRotation(Leg4, 0F, 0F, 0F);
        LeftMid = new ModelRenderer(this, 0, 18);
        LeftMid.addBox(0F, 0F, 0F, 2, 3, 16);
        LeftMid.setRotationPoint(-8F, 17F, -8F);
        LeftMid.setTextureSize(64, 64);
        LeftMid.mirror = true;
        setRotation(LeftMid, 0F, 0F, 0F);
        RightMid = new ModelRenderer(this, 0, 18);
        RightMid.addBox(0F, 0F, 0F, 2, 3, 16);
        RightMid.setRotationPoint(6F, 17F, -8F);
        RightMid.setTextureSize(64, 64);
        RightMid.mirror = true;
        setRotation(RightMid, 0F, 0F, 0F);
        BackLeft = new ModelRenderer(this, 0, 37);
        BackLeft.addBox(0F, 0F, 0F, 2, 11, 2);
        BackLeft.setRotationPoint(-8F, 4F, 6F);
        BackLeft.setTextureSize(64, 64);
        BackLeft.mirror = true;
        setRotation(BackLeft, 0F, 0F, 0F);
        TopBase = new ModelRenderer(this, 0, 0);
        TopBase.addBox(0F, 0F, 0F, 16, 2, 16);
        TopBase.setRotationPoint(-8F, 15F, -8F);
        TopBase.setTextureSize(64, 64);
        TopBase.mirror = true;
        setRotation(TopBase, 0F, 0F, 0F);
        Platform = new ModelRenderer(this, 8, 39);
        Platform.addBox(0F, 0F, 0F, 12, 1, 12);
        Platform.setRotationPoint(-6F, 14F, -6F);
        Platform.setTextureSize(64, 64);
        Platform.mirror = true;
        setRotation(Platform, 0F, 0F, 0F);
        BackMid = new ModelRenderer(this, 36, 18);
        BackMid.addBox(0F, 0F, 0F, 12, 3, 2);
        BackMid.setRotationPoint(-6F, 17F, 6F);
        BackMid.setTextureSize(64, 64);
        BackMid.mirror = true;
        setRotation(BackMid, 0F, 0F, 0F);
        BackTop = new ModelRenderer(this, 36, 27);
        BackTop.addBox(0F, 0F, 0F, 12, 11, 1);
        BackTop.setRotationPoint(-6F, 4F, 7F);
        BackTop.setTextureSize(64, 64);
        BackTop.mirror = true;
        setRotation(BackTop, 0F, 0F, 0F);
        BackRight = new ModelRenderer(this, 0, 37);
        BackRight.addBox(0F, 0F, 0F, 2, 11, 2);
        BackRight.setRotationPoint(6F, 4F, 6F);
        BackRight.setTextureSize(64, 64);
        BackRight.mirror = true;
        setRotation(BackRight, 0F, 0F, 0F);
        TopTop = new ModelRenderer(this, 0, 52);
        TopTop.addBox(0F, 0F, 0F, 16, 2, 2);
        TopTop.setRotationPoint(-8F, 2F, 6F);
        TopTop.setTextureSize(64, 64);
        TopTop.mirror = true;
        setRotation(TopTop, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        BottomBase.render(f5);
        Leg3.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg4.render(f5);
        LeftMid.render(f5);
        RightMid.render(f5);
        BackLeft.render(f5);
        TopBase.render(f5);
        Platform.render(f5);
        BackMid.render(f5);
        BackTop.render(f5);
        BackRight.render(f5);
        TopTop.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
