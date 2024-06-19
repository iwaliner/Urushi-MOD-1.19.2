package com.iwaliner.urushi.block;


import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class TVBlock extends HorizonalRotateBlock{

    protected static final VoxelShape SHAPEA = Block.box(15D, 2.0D, 0D, 16D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPEB = Block.box(0D, 2.0D, 15D, 16D, 16.0D, 16D);
    protected static final VoxelShape SHAPEC = Block.box(0D, 2.0D, 0D, 1D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPED = Block.box(0D, 2.0D, 0D, 16D, 16.0D, 1D);

    private Block nextBlock;
    public TVBlock(Block b, Properties p_i48377_1_) {
        super(p_i48377_1_);
            nextBlock = b;

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(state.getValue(FACING)== Direction.NORTH){
            return SHAPEB;
        }else if(state.getValue(FACING)== Direction.SOUTH){
            return SHAPED;
        }else if(state.getValue(FACING)== Direction.EAST){
            return SHAPEC;
        }else{
            return SHAPEA;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }



/*    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {

        Direction direction=state.getValue(FACING);
        if(nextBlock instanceof TVBlock) {
                world.setBlock(pos, nextBlock.defaultBlockState().setValue(FACING, direction),2);
                world.playSound((Player) null, pos, SoundEvents.DISPENSER_DISPENSE, SoundSource.BLOCKS, 1F, 1F);
                return InteractionResult.SUCCESS;
            }else if(nextBlock==null){
            world.setBlock(pos, ItemAndBlockRegister.tv_idle.get().defaultBlockState().setValue(FACING, direction),2);
            world.playSound((Player) null, pos, SoundEvents.DISPENSER_DISPENSE, SoundSource.BLOCKS, 1F, 1F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }*/

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        list.add((Component.translatable("info.urushi.tv" )).withStyle(ChatFormatting.GRAY));
    }

}
