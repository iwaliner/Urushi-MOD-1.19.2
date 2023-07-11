package com.iwaliner.urushi;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BiomeRegister {
    public static final DeferredRegister<Biome> BIOMES;
    public static final ResourceKey<Biome> SakuraForest;
    public static final ResourceKey<Biome> EulaliaPlains;
    public static final ResourceKey<Biome> AutumnForest;
    public static final ResourceKey<Biome> CedarForest;

   // public static final ResourceKey<Biome> LycorisPlains;

    public static final List<ResourceKey<Biome>> KakuriyoList = new ArrayList<>();

    public BiomeRegister() {
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
        KakuriyoList.add(SakuraForest);
        KakuriyoList.add(EulaliaPlains);
        KakuriyoList.add(AutumnForest);
        KakuriyoList.add(CedarForest);
      //  KakuriyoList.add(LycorisPlains);

    }

    private static ResourceKey<Biome> registerRiver(String p_48229_) {
        BIOMES.register(p_48229_, BiomeRegister::river);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID, p_48229_));
    }

    private static ResourceKey<Biome> registerOcean(String p_48229_) {
        BIOMES.register(p_48229_, BiomeRegister::ocean);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID, p_48229_));
    }

    private static ResourceKey<Biome> registerForest(String p_48229_) {
        BIOMES.register(p_48229_, BiomeRegister::forest);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID, p_48229_));
    }

    private static ResourceKey<Biome> registerPlains(String p_48229_) {
        BIOMES.register(p_48229_, BiomeRegister::plains);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID, p_48229_));
    }

    private static ResourceKey<Biome> registerVoid(String p_48229_) {
        BIOMES.register(p_48229_, OverworldBiomes::theVoid);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID, p_48229_));
    }

    static {
        BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ModCoreUrushi.ModID);
        SakuraForest = registerForest("sakura_forest");
        EulaliaPlains = registerPlains("eulalia_plains");
        AutumnForest = registerForest("autumn_forest");
        CedarForest = registerForest("cedar_forest");
     //   LycorisPlains = registerPlains("lycoris_plains");


    }

    public static Biome river() {
        return OverworldBiomes.river(false);
    }

    public static Biome plains() {
        return OverworldBiomes.plains(false, false, false);
    }

    public static Biome forest() {
        return OverworldBiomes.forest(false, false, false);
    }

    public static Biome ocean() {
        return OverworldBiomes.ocean(false);
    }
}