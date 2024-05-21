package com.iwaliner.urushi.datagen;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class NormalBlockLootTable extends BlockLoot {

    @Override
    public  void addTables(){
        for(int i=0;i<ModCoreUrushi.blockSelfDropList.size();i++) {
            this.dropSelf(ModCoreUrushi.blockSelfDropList.get(i).get());
        }
        this.dropSelf(ItemAndBlockRegister.kitsunebiBlock.get());
        this.dropSelf(ItemAndBlockRegister.invisible_button.get());
        this.dropSelf(ItemAndBlockRegister.hidden_invisible_button.get());
        this.dropSelf(ItemAndBlockRegister.invisible_lever.get());
        this.dropSelf(ItemAndBlockRegister.hidden_invisible_lever.get());
        this.dropSelf(ItemAndBlockRegister.invisible_pressure_plate.get());
        this.dropSelf(ItemAndBlockRegister.hidden_invisible_pressure_plate.get());
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ItemAndBlockRegister.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
    /*@Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues()
                .stream()
                .filter(block -> ModCoreUrushi.ModID
                        .equals(ForgeRegistries.BLOCKS.getKey(block).getNamespace()))
                .collect(Collectors.toSet());
    }*/
}
