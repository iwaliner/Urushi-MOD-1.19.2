package com.iwaliner.urushi.world.tree;

import com.iwaliner.urushi.ConfiguredFeatureRegister;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Random;

public class ApricotTreeGrower extends AbstractTreeGrower {
    public ApricotTreeGrower() {
    }
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_204316_, boolean p_204317_) {
        return ConfiguredFeatureRegister.APRICOT.getHolder().get();
    }
}
