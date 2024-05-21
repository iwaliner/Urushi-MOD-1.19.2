package com.iwaliner.urushi;

import com.iwaliner.urushi.datagen.UrushiBlockStateProvider;
import com.iwaliner.urushi.datagen.UrushiLootTableProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModCoreUrushi.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeServer()) {
            generator.addProvider(true,new UrushiLootTableProvider(generator));
        }


        generator.addProvider(true,new UrushiBlockStateProvider(generator, ModCoreUrushi.ModID, event.getExistingFileHelper()));
    }
}
