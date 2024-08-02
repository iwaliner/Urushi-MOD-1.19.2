package com.iwaliner.urushi.blockentity;



import com.iwaliner.urushi.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public  class MobCageBlockEntity extends BlockEntity {

    public MobCageBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.MobCage.get(), p_155052_, p_155053_);
    }


    public void load(CompoundTag p_155025_) {
        super.load(p_155025_);

    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);

    }
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();

        return compoundtag;
    }




}
