package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.HiddenInvisibleLeverBlock;
import com.iwaliner.urushi.block.HiddenInvisiblePressurePlateBlock;
import com.iwaliner.urushi.block.InvisibleLeverBlock;
import com.iwaliner.urushi.block.InvisiblePressurePlateBlock;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InvisiblePressurePlateItem extends BlockItem {
    public InvisiblePressurePlateItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> list, @NotNull TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list,"invisible_redstone_inputs");
    }
    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int a, boolean b) {
        if(entity instanceof LivingEntity){
            int range=8;
            LivingEntity player= (LivingEntity) entity;
            BlockPos pos=new BlockPos(Math.floor(entity.getX()),Math.floor(entity.getY()),Math.floor(entity.getZ()));
            if (player.getOffhandItem() == stack || player.getMainHandItem() == stack) {
                for(int i=-range; i<range+1;i++) {
                    for(int j=-range; j<range+1;j++) {
                        for(int k=-range; k<range+1;k++) {
                            if( world.getBlockState(pos.offset(i,j,k)).getBlock()== ItemAndBlockRegister.hidden_invisible_pressure_plate.get()){
                                BlockState hiddenState=world.getBlockState(pos.offset(i,j,k));
                                world.setBlockAndUpdate(pos.offset(i,j,k),ItemAndBlockRegister.invisible_pressure_plate.get().defaultBlockState());
                            }
                        }
                    }
                }


            }
        }
    }
}
