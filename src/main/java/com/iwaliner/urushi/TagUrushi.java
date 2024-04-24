package com.iwaliner.urushi;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class TagUrushi {
    public static Map< BlockState, BlockState> fileMap = new HashMap<>();

    // this record stores items' mining speed change modifiers.
    public record ElementMiningSpeedModifier(
            int wood,
            int fire,
            int earth,
            int metal,
            int water
    ){};

    // store value example:
    // TagUrushi.elementMiningSpeedChangeItemMap.put(ItemAndBlockRegister.wood_element_magatama.get(),
    //                new TagUrushi.ElementMiningSpeedModifier(-40,-30,50, 0, 0));
    // 0 means no speed change
    public static Map<Item, ElementMiningSpeedModifier> elementMiningSpeedChangeItemMap = new HashMap<>();


    public static final TagKey<Block> YOMI_STONE =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"yomi_stone"));
    public static final TagKey<Block> QUARTZ_CLUSTER_PLACEABLE =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"quartz_cluster_placeable"));
    public static final TagKey<Block> SHIITAKE_GROW_BLOCK =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"shiitake_placeable"));
    public static final TagKey<Block> SHIITAKE_WALL_GROW_BLOCK =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"shiitake_wall_placeable"));
    public static final TagKey<Block> GRASS_BLOCK_WITH_FALLEN_LEAVES_INGREDIENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"grass_block_with_fallen_leaves_ingredient"));
    public static final TagKey<Block> JAPANESE_TIMBER_BAMBOO_PLACEABLE =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"japanese_timber_bamboo_placeable"));
    public static final TagKey<Item> OIL_EXTRACTOR_INSERTALE =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"oil_extractor_insertable"));
    public static final TagKey<Block> DISABLE_WOOD_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"disable_wood_element"));
    public static final TagKey<Block> REGISTER_WOOD_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"register_wood_element"));
    public static final TagKey<Block> DISABLE_FIRE_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"disable_fire_element"));
    public static final TagKey<Block> REGISTER_FIRE_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"register_fire_element"));
    public static final TagKey<Block> DISABLE_EARTH_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"disable_earth_element"));
    public static final TagKey<Block> REGISTER_EARTH_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"register_earth_element"));
    public static final TagKey<Block> DISABLE_METAL_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"disable_metal_element"));
    public static final TagKey<Block> REGISTER_METAL_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"register_metal_element"));
    public static final TagKey<Block> DISABLE_WATER_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"disable_water_element"));
    public static final TagKey<Block> REGISTER_WATER_ELEMENT =TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"register_water_element"));
    public static final TagKey<Item> WOOD_ELEMENT_ITEM =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"wood_element"));
    public static final TagKey<Item> FIRE_ELEMENT_ITEM =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"fire_element"));
    public static final TagKey<Item> EARTH_ELEMENT_ITEM =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"earth_element"));
    public static final TagKey<Item> METAL_ELEMENT_ITEM =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"metal_element"));
    public static final TagKey<Item> WATER_ELEMENT_ITEM =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"water_element"));
    public static final TagKey<Item> SHICHIRIN_FUEL =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"shichirin_fuel"));
    public static final TagKey<Item> IGNITER =TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModCoreUrushi.ModID,"igniter"));

}
