package com.iwaliner.urushi.datagen;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class UrushiBlockStateProvider extends BlockStateProvider {
    public UrushiBlockStateProvider(DataGenerator gen, String modId, ExistingFileHelper exFileHelper) {
        super(gen, modId, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
       // simpleBlockWithItem(ItemAndBlockRegister.bamboo_block);
    }

    private void simpleBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        ModelFile modelFile=cubeAll(blockRegistryObject.get());
       simpleBlock(blockRegistryObject.get(),modelFile);
       //simpleBlockWithItem(blockRegistryObject);
    }
}