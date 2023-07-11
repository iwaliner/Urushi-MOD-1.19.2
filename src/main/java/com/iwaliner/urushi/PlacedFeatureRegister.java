package com.iwaliner.urushi;

import com.google.common.collect.ImmutableList;
import com.iwaliner.urushi.world.feature.JapaneseTimberBambooFeature;
import com.iwaliner.urushi.world.feature.KakuriyoPortalFeature;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class PlacedFeatureRegister {
    public static final DeferredRegister<PlacedFeature> PlacedFeatures = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ModCoreUrushi.ModID);

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static final RegistryObject<PlacedFeature> HOT_SPRING_CHECKED = PlacedFeatures.register("hot_spring_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.HOT_SPRING.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.HotSpringBlock.get()))));
    public static final RegistryObject<PlacedFeature> BAMBOO_CHECKED = PlacedFeatures.register("japanese_timber_bamboo_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.BAMBOO.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.japanese_timber_bamboo.get()))));

    public static final RegistryObject<PlacedFeature> KAKURIYO_PORTAL_OVERWORLD_CHECKED = PlacedFeatures.register("kakuriyo_portal_overworld_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.KAKURIYO_PORTAL_OVERWORLD.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.red_kakuriyo_portal_frame.get()))));
    public static final RegistryObject<PlacedFeature> APRICOT_CHECKED = PlacedFeatures.register("japanese_apricot_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.APRICOT.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.japanese_apricot_sapling.get()))));

    public static final RegistryObject<PlacedFeature> GLOWING_APRICOT_CHECKED = PlacedFeatures.register("glowing_japanese_apricot_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.GLOWING_APRICOT.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.glowing_japanese_apricot_sapling.get()))));

    public static final RegistryObject<PlacedFeature> SAKURA_CHECKED = PlacedFeatures.register("sakura_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.SAKURA.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.sakura_sapling.get()))));

    public static final RegistryObject<PlacedFeature> FANCY_SAKURA_CHECKED = PlacedFeatures.register("fancy_sakura_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.FANCY_SAKURA.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.big_sakura_sapling.get()))));

    public static final RegistryObject<PlacedFeature> GLOWING_SAKURA_CHECKED = PlacedFeatures.register("glowing_sakura_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.GLOWING_SAKURA.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.glowing_sakura_sapling.get()))));

    public static final RegistryObject<PlacedFeature> GLOWING_FANCY_SAKURA_CHECKED = PlacedFeatures.register("glowing_fancy_sakura_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.GLOWING_FANCY_SAKURA.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.glowing_big_sakura_sapling.get()))));

    public static final RegistryObject<PlacedFeature> RED_CHECKED = PlacedFeatures.register("red_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.RED.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.red_sapling.get()))));

    public static final RegistryObject<PlacedFeature> ORANGE_CHECKED = PlacedFeatures.register("orange_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.ORANGE.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.orange_sapling.get()))));

    public static final RegistryObject<PlacedFeature> YELLOW_CHECKED = PlacedFeatures.register("yellow_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.YELLOW.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.yellow_sapling.get()))));

    public static final RegistryObject<PlacedFeature> LACQUER_CHECKED = PlacedFeatures.register("lacquer_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.LACQUER.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.lacquer_sapling.get()))));

    public static final RegistryObject<PlacedFeature> CYPRESS_CHECKED = PlacedFeatures.register("cypress_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.CYPRESS.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.cypress_sapling.get()))));

    public static final RegistryObject<PlacedFeature> CEDAR_CHECKED = PlacedFeatures.register("japanese_cedar_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.CEDAR.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.japanese_cedar_sapling.get()))));

    public static final RegistryObject<PlacedFeature> MEGA_CEDAR_CHECKED = PlacedFeatures.register("mega_japanese_cedar_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.MEGA_CEDAR.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.japanese_cedar_sapling.get()))));

    public static final RegistryObject<PlacedFeature> KAKURIYO_MEGA_CEDAR_CHECKED = PlacedFeatures.register("mega_japanese_cedar_in_kakuriyo_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.KAKURIYO_MEGA_CEDAR.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.japanese_cedar_sapling.get()))));

    public static final RegistryObject<PlacedFeature> YOMI_VINES_CHECKED = PlacedFeatures.register("yomi_vines_checked",
            () -> new PlacedFeature(ConfiguredFeatureRegister.YOMI_VINES.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(ItemAndBlockRegister.yomi_vines.get()))));


    public static final RegistryObject<PlacedFeature> KAKURIYO_PORTAL_OVERWORLD_PLACED = PlacedFeatures.register("overworld_kakuriyo_portal",
            () -> new PlacedFeature(ConfiguredFeatureRegister.KAKURIYO_PORTAL_OVERWORLD_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    public static final RegistryObject<PlacedFeature> FOREST_BAMBOO = PlacedFeatures.register("forest_japanese_timber_bamboo",
            () -> new PlacedFeature(ConfiguredFeatureRegister.BAMBOO_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    public static final RegistryObject<PlacedFeature> FOREST_APRICOT = PlacedFeatures.register("forest_japanese_apricot",
            () -> new PlacedFeature(ConfiguredFeatureRegister.APRICOT_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    public static final RegistryObject<PlacedFeature> FOREST_SAKURA = PlacedFeatures.register("forest_sakura",
            () -> new PlacedFeature(ConfiguredFeatureRegister.SAKURA_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    public static final RegistryObject<PlacedFeature> SAKURA_FOREST_SAKURA = PlacedFeatures.register("sakura_forest_sakura",
            () -> new PlacedFeature(ConfiguredFeatureRegister.FANCY_SAKURA_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(5, 0.5f, 1))));

    public static final RegistryObject<PlacedFeature> HILLS_CYPRESS = PlacedFeatures.register("hill_cypress",
            () -> new PlacedFeature(ConfiguredFeatureRegister.CYPRESS_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    public static final RegistryObject<PlacedFeature> CEDAR_FOREST_CYPRESS = PlacedFeatures.register("cedar_forest_cypress",
            () -> new PlacedFeature(ConfiguredFeatureRegister.CYPRESS_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1))));

    public static final RegistryObject<PlacedFeature> HILLS_CEDAR = PlacedFeatures.register("hill_japanese_cedar",
            () -> new PlacedFeature(ConfiguredFeatureRegister.CEDAR_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    public static final RegistryObject<PlacedFeature> FOREST_LACQUER = PlacedFeatures.register("forest_lacquer",
            () -> new PlacedFeature(ConfiguredFeatureRegister.LACQUER_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_RED = PlacedFeatures.register("autumn_forest_red",
            () -> new PlacedFeature(ConfiguredFeatureRegister.RED_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.5f, 1))));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_ORANGE = PlacedFeatures.register("autumn_forest_orange",
            () -> new PlacedFeature(ConfiguredFeatureRegister.ORANGE_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.5f, 1))));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_YELLOW = PlacedFeatures.register("autumn_forest_yellow",
            () -> new PlacedFeature(ConfiguredFeatureRegister.YELLOW_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.5f, 1))));

    public static final RegistryObject<PlacedFeature> KAKURIYO_CEDAR_FOREST_CEDAR = PlacedFeatures.register("kakuriyo_cedar_forest_cedar",
            () -> new PlacedFeature(ConfiguredFeatureRegister.KAKURIYO_MEGA_CEDAR_SPAWN.getHolder().get(),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.2f, 1))));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_SHIITAKE = PlacedFeatures.register("autumn_forest_shiitake",
            () -> new PlacedFeature(ConfiguredFeatureRegister.SHIITAKE.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(1)));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_N = PlacedFeatures.register("autumn_forest_wall_shiitake_n",
            () -> new PlacedFeature(ConfiguredFeatureRegister.WALL_SHIITAKE_N.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(1)));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_E= PlacedFeatures.register("autumn_forest_wall_shiitake_e",
            () -> new PlacedFeature(ConfiguredFeatureRegister.WALL_SHIITAKE_E.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(1)));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_S = PlacedFeatures.register("autumn_forest_wall_shiitake_s",
            () -> new PlacedFeature(ConfiguredFeatureRegister.WALL_SHIITAKE_S.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(1)));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_W = PlacedFeatures.register("autumn_forest_wall_shiitake_w",
            () -> new PlacedFeature(ConfiguredFeatureRegister.WALL_SHIITAKE_W.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(1)));

    public static final RegistryObject<PlacedFeature> EULALIA_PLAINS_EULALIA = PlacedFeatures.register("eulalia_plains_eulalia",
            () -> new PlacedFeature(ConfiguredFeatureRegister.EULALIA.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(10)));

    public static final RegistryObject<PlacedFeature> EULALIA_PLAINS_TALL_EULALIA = PlacedFeatures.register("eulalia_plains_tall_eulalia",
            () -> new PlacedFeature(ConfiguredFeatureRegister.TALL_EULALIA.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(10)));

    public static final RegistryObject<PlacedFeature> KAKURIYO_LYCORIS = PlacedFeatures.register("kakuriyo_lycoris",
            () -> new PlacedFeature(ConfiguredFeatureRegister.LYCORIS.getHolder().get(),
                    fullRangeSquaredWithCount(1)));

    public static final RegistryObject<PlacedFeature> SWAMP_AJISAI = PlacedFeatures.register("swamp_ajisai",
            () -> new PlacedFeature(ConfiguredFeatureRegister.AJISAI.getHolder().get(),
                    fullRangeSquaredWithCount(1)));

    public static final RegistryObject<PlacedFeature> SAKURA_FOREST_FALLEN_SAKURA_LEAVES = PlacedFeatures.register("sakura_forest_fallen_sakura_leaves",
            () -> new PlacedFeature(ConfiguredFeatureRegister.FALLEN_SAKUEA_LEAVES.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(10)));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_FALLEN_RED_LEAVES = PlacedFeatures.register("autumn_forest_fallen_red_leaves",
            () -> new PlacedFeature(ConfiguredFeatureRegister.FALLEN_RED_LEAVES.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(10)));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_FALLEN_ORANGE_LEAVES = PlacedFeatures.register("autumn_forest_fallen_orange_leaves",
            () -> new PlacedFeature(ConfiguredFeatureRegister.FALLEN_ORANGE_LEAVES.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(10)));

    public static final RegistryObject<PlacedFeature> AUTUMN_FOREST_FALLEN_YELLOW_LEAVES = PlacedFeatures.register("autumn_forest_fallen_yellow_leaves",
            () -> new PlacedFeature(ConfiguredFeatureRegister.FALLEN_YELLOW_LEAVES.getHolder().get(),
                    VegetationPlacements.worldSurfaceSquaredWithCount(10)));

    public static final RegistryObject<PlacedFeature> ORE_IRONSAND_OVERWORLD = PlacedFeatures.register("overworld_ore_ironsand",
            () -> new PlacedFeature(ConfiguredFeatureRegister.ORE_IRONSAND.getHolder().get(),
                    commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(200)))));

    public static final RegistryObject<PlacedFeature> ORE_JADEITE_KAKURIYO = PlacedFeatures.register("kakuriyo_ore_jadeite",
            () -> new PlacedFeature(ConfiguredFeatureRegister.ORE_JADEITE.getHolder().get(),
                    commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120)))));

    public static final RegistryObject<PlacedFeature> ORE_YOMI_IRON_KAKURIYO = PlacedFeatures.register("kakuriyo_ore_yomi_iron",
            () -> new PlacedFeature(ConfiguredFeatureRegister.ORE_IRON.getHolder().get(),
                    commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120)))));

    public static final RegistryObject<PlacedFeature> ORE_YOMI_GOLD_KAKURIYO = PlacedFeatures.register("kakuriyo_ore_yomi_gold",
            () -> new PlacedFeature(ConfiguredFeatureRegister.ORE_GOLD.getHolder().get(),
                    commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120)))));

    public static final RegistryObject<PlacedFeature> ORE_YOMI_COPPER_KAKURIYO = PlacedFeatures.register("kakuriyo_ore_yomi_copper",
            () -> new PlacedFeature(ConfiguredFeatureRegister.ORE_COPPER.getHolder().get(),
                    commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120)))));

    public static final RegistryObject<PlacedFeature> KAKURIYO_QUARTZ_CLUSTER = PlacedFeatures.register("kakuriyo_quartz_cluster",
            () -> new PlacedFeature(ConfiguredFeatureRegister.QUARTZ_CLUSTER.getHolder().get(),
                    fullRangeSquaredWithCount(10)));

    public static final RegistryObject<PlacedFeature> KAKURIYO_LANTERN_PLANT = PlacedFeatures.register("kakuriyo_lantern_plant",
            () -> new PlacedFeature(ConfiguredFeatureRegister.LANTERN_PLANT.getHolder().get(),
                    fullRangeSquaredWithCount(3)));

    public static final RegistryObject<PlacedFeature> KAKURIYO_YOMI_VINES = PlacedFeatures.register("kakuriyo_yomi_vines",
            () -> new PlacedFeature(ConfiguredFeatureRegister.YOMI_VINES_SPAWN.getHolder().get(),
                    fullRangeSquaredWithCount(20)));

    public static final RegistryObject<PlacedFeature> KAKURIYO_HOT_SPRING = PlacedFeatures.register("kakuriyo_hot_spring",
            () -> new PlacedFeature(ConfiguredFeatureRegister.HOT_SPRING_SPAWN.getHolder().get(),
                    fullRangePlacement(PlacementUtils.countExtra(0, 0.1f, 1))));

    private static ImmutableList.Builder<PlacementModifier> fullRangePlacementBase(PlacementModifier p_195485_) {
        return ImmutableList.<PlacementModifier>builder().add(p_195485_).add(InSquarePlacement.spread()).add(VegetationPlacements.TREE_THRESHOLD).add(PlacementUtils.HEIGHTMAP).add(BiomeFilter.biome());
    }
    public static List<PlacementModifier> fullRangePlacement(PlacementModifier p_195480_) {
        return fullRangePlacementBase(p_195480_).build();
    }
    public static List<PlacementModifier> fullRangeSquaredWithCount(int p_195475_) {
        return List.of(CountPlacement.of(p_195475_), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    }
    public static void register(IEventBus eventBus) {
        PlacedFeatures.register(eventBus);
    }
}
