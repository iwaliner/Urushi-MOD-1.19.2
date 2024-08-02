package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.block.MobCageBlock;
import com.iwaliner.urushi.block.PlateBlock;
import com.iwaliner.urushi.blockentity.MobCageBlockEntity;
import com.iwaliner.urushi.blockentity.PlateBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MobCageRenderer implements BlockEntityRenderer<MobCageBlockEntity> {

    public MobCageRenderer(BlockEntityRendererProvider.Context p_173602_) {
    }

    public void render(MobCageBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        Direction direction = blockEntity.getBlockState().getValue(MobCageBlock.FACING);

        int i = (int)blockEntity.getBlockPos().asLong();


            poseStack.pushPose();
            poseStack.translate(0.5D, 0.0625D*2D, 0.5D);
            Direction direction1 = Direction.from2DDataValue((direction.get2DDataValue()) % 4).getOpposite().getClockWise();
            float f = -direction1.toYRot();
            poseStack.mulPose(Vector3f.YP.rotationDegrees(f));
            //poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            poseStack.scale(0.25F, 0.25F, 0.25F);
            Minecraft.getInstance().getEntityRenderDispatcher().render(EntityType.COW.create(blockEntity.getLevel()),0.0D, 0.0D, 0.0D, 0.0F, 0.0F, poseStack, bufferSource, i);
            poseStack.popPose();

    }


}