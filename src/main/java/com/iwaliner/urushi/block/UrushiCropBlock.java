package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UrushiCropBlock extends CropBlock {

    public UrushiCropBlock( Properties p_52247_) {
        super(p_52247_);
    }
    public int getMaxAge() {
        return 4;
    }
    protected ItemLike getBaseSeedId() {
        return this;
    }
   protected boolean mayPlaceOn(BlockState state, BlockGetter p_52303_, BlockPos p_52304_) {
        return state.getBlock() instanceof FarmBlock;
    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(state.getBlock() instanceof CropBlock){
            if(world.getBlockState(pos).getValue(AGE)==Integer.valueOf(4)) {
                world.destroyBlock(pos, true);
                world.setBlockAndUpdate(pos,this.defaultBlockState());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
}
