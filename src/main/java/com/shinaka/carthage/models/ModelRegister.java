package com.shinaka.carthage.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRegister extends ModelBase
{
  //fields
    ModelRenderer TapeHolder;
    ModelRenderer RegisterBottom;
    ModelRenderer RegisterTop;
    ModelRenderer RegisterArm;
    ModelRenderer RegisterMid;
    ModelRenderer RegisterArmBase;
  
  public ModelRegister()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      TapeHolder = new ModelRenderer(this, 45, 0);
      TapeHolder.addBox(0F, 0F, 0F, 2, 4, 6);
      TapeHolder.setRotationPoint(-5F, 17F, -1F);
      TapeHolder.setTextureSize(64, 32);
      TapeHolder.mirror = true;
      setRotation(TapeHolder, 0F, 0F, 0F);
      RegisterBottom = new ModelRenderer(this, 0, 0);
      RegisterBottom.addBox(0F, 0F, 0F, 10, 3, 12);
      RegisterBottom.setRotationPoint(-5F, 21F, -5F);
      RegisterBottom.setTextureSize(64, 32);
      RegisterBottom.mirror = true;
      setRotation(RegisterBottom, 0F, 0F, 0F);
      RegisterTop = new ModelRenderer(this, 31, 16);
      RegisterTop.addBox(0F, 0F, 0F, 8, 2, 2);
      RegisterTop.setRotationPoint(-3F, 14F, 1F);
      RegisterTop.setTextureSize(64, 32);
      RegisterTop.mirror = true;
      setRotation(RegisterTop, 0F, 0F, 0F);
      RegisterArm = new ModelRenderer(this, 52, 13);
      RegisterArm.addBox(0F, 0F, 0F, 1, 5, 1);
      RegisterArm.setRotationPoint(6F, 18F, 2F);
      RegisterArm.setTextureSize(64, 32);
      RegisterArm.mirror = true;
      setRotation(RegisterArm, -0.7435722F, 0.0174533F, 0F);
      RegisterMid = new ModelRenderer(this, 0, 16);
      RegisterMid.addBox(0F, 0F, 0F, 8, 5, 7);
      RegisterMid.setRotationPoint(-3F, 16F, 0F);
      RegisterMid.setTextureSize(64, 32);
      RegisterMid.mirror = true;
      setRotation(RegisterMid, 0F, 0F, 0F);
      RegisterArmBase = new ModelRenderer(this, 31, 21);
      RegisterArmBase.addBox(0F, 0F, 0F, 1, 3, 3);
      RegisterArmBase.setRotationPoint(5F, 17F, 1F);
      RegisterArmBase.setTextureSize(64, 32);
      RegisterArmBase.mirror = true;
      setRotation(RegisterArmBase, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    TapeHolder.render(f5);
    RegisterBottom.render(f5);
    RegisterTop.render(f5);
    RegisterArm.render(f5);
    RegisterMid.render(f5);
    RegisterArmBase.render(f5);
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
