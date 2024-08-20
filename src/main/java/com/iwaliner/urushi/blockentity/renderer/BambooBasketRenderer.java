package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.block.BambooBasketBlock;
import com.iwaliner.urushi.block.PlateBlock;
import com.iwaliner.urushi.blockentity.BambooBasketBlockEntity;
import com.iwaliner.urushi.blockentity.PlateBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BambooBasketRenderer implements BlockEntityRenderer<BambooBasketBlockEntity> {

    public BambooBasketRenderer(BlockEntityRendererProvider.Context p_173602_) {
    }

    public void render(BambooBasketBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        Direction direction = blockEntity.getBlockState().getValue(BambooBasketBlock.FACING);

        int l = (int) blockEntity.getBlockPos().asLong();
        for (int i = 0; i < 5; i++) {
            if (!blockEntity.getItem(i).isEmpty()) {
                poseStack.pushPose();
                if(direction.getAxis()== Direction.Axis.X) {
                    poseStack.translate(0.5D, 0.0625D * 2D + (double) ((i + 1) & 4) * 0.0078125D + (double) i * 0.0015625D, i < 3 ? 0.3D + 0.2D * (double) i : 0.4D + 0.2D * (double) (i - 3));
                }else{
                    poseStack.translate(i < 3 ? 0.3D + 0.2D * (double) i : 0.4D + 0.2D * (double) (i - 3), 0.0625D * 2D + (double) ((i + 1) & 4) * 0.0078125D + (double) i * 0.0015625D, 0.5D);
                }
                Direction direction1 = Direction.from2DDataValue((direction.get2DDataValue()) % 4).getOpposite();
                float f = -direction1.toYRot();
                poseStack.mulPose(Vector3f.YP.rotationDegrees(f));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                if(i<3) {
                    poseStack.scale(0.35F, 0.35F, 0.35F);
                }else{
                    poseStack.scale(0.3499F, 0.3499F, 0.3499F);
                }
                Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getItem(i), ItemTransforms.TransformType.FIXED, i1, i2, poseStack, bufferSource, i);
                poseStack.popPose();
            }
        }
    }


}