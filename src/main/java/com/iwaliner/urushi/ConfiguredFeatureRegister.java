package com.iwaliner.urushi;

import com.iwaliner.urushi.block.LanternPlantBlock;
import com.iwaliner.urushi.block.WallShiitakeBlock;
import com.iwaliner.urushi.world.feature.JapaneseTimberBambooFeature;
import com.iwaliner.urushi.world.feature.KakuriyoPortalFeature;
import com.iwaliner.urushi.world.feature.KakuriyoTreeConfigration;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.OptionalInt;

public class ConfiguredFeatureRegister {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> ConfiguredFeatures = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ModCoreUrushi.ModID);

   // private static final WeightedStateProvider YOMI_VINES_BODY_PROVIDER = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ItemAndBlockRegister.yomi_vines_plant.get().defaultBlockState(), 4).add(ItemAndBlockRegister.yomi_vines_plant.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1));

   // private static final RandomizedIntStateProvider YOMI_VINES_HEAD_PROVIDER = new RandomizedIntStateProvider(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ItemAndBlockRegister.yomi_vines.get().defaultBlockState(), 4).add(ItemAndBlockRegister.yomi_vines.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1)), CaveVinesBlock.AGE, UniformInt.of(23, 25));

    public static final RuleTest NATURAL_SAND = new TagMatchTest(BlockTags.SAND);
    public static final RuleTest NATURAL_YOMI_STONE = new TagMatchTest(TagUrushi.YOMI_STONE);
 private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
    }

   public static final RegistryObject<ConfiguredFeature<?, ?>> HOT_SPRING =
           ConfiguredFeatures.register("hot_spring", () ->
                   new ConfiguredFeature<>(Feature.LAKE,
                           new LakeFeature.Configuration(BlockStateProvider.simple(ItemAndBlockRegister.HotSpringBlock.get()),
                                   BlockStateProvider.simple(Blocks.TUFF))));
   public static final RegistryObject<ConfiguredFeature<?, ?>> BAMBOO =
            ConfiguredFeatures.register("japanese_timber_bamboo", () ->
                    new ConfiguredFeature<>(FeatureRegister.Bamboo.get(),
                            new JapaneseTimberBambooFeature.Configuration(BlockStateProvider.simple(ItemAndBlockRegister.japanese_timber_bamboo.get()))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> KAKURIYO_PORTAL_OVERWORLD =
            ConfiguredFeatures.register("kakuriyo_portal_overworld", () ->
                    new ConfiguredFeature<>(FeatureRegister.KakuriyoPortal.get(),
                            new KakuriyoPortalFeature.Configuration(BlockStateProvider.simple(ItemAndBlockRegister.ghost_red_kakuriyo_portal_frame.get()))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> APRICOT =
            ConfiguredFeatures.register("japanese_apricot", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_apricot_log.get()),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_apricot_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GLOWING_APRICOT =
            ConfiguredFeatures.register("glowing_japanese_apricot", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_apricot_log.get()),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.glowing_japanese_apricot_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));
    public static final RegistryObject<ConfiguredFeature<?, ?>> SAKURA =
            ConfiguredFeatures.register("sakura", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.sakura_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FANCY_SAKURA =
            ConfiguredFeatures.register("fancy_sakura", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()),
                            new FancyTrunkPlacer(3, 11, 0),
                            BlockStateProvider.simple(ItemAndBlockRegister.sakura_leaves.get()),
                            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GLOWING_SAKURA =
            ConfiguredFeatures.register("glowing_sakura", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.glowing_sakura_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GLOWING_FANCY_SAKURA =
            ConfiguredFeatures.register("glowing_fancy_sakura", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()),
                            new FancyTrunkPlacer(3, 11, 0),
                            BlockStateProvider.simple(ItemAndBlockRegister.glowing_sakura_leaves.get()),
                            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> RED =
            ConfiguredFeatures.register("red", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(Blocks.OAK_LOG),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.red_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORANGE =
            ConfiguredFeatures.register("orange", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(Blocks.OAK_LOG),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.orange_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> YELLOW =
            ConfiguredFeatures.register("yellow", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(Blocks.OAK_LOG),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.yellow_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> LACQUER =
            ConfiguredFeatures.register("lacquer", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.lacquer_log.get()),
                            new StraightTrunkPlacer(5, 2, 2),
                            BlockStateProvider.simple(ItemAndBlockRegister.lacquer_leaves.get()),
                            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                            new TwoLayersFeatureSize(1, 0, 1)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CYPRESS =
            ConfiguredFeatures.register("cypress", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.cypress_log.get()),
                            new StraightTrunkPlacer(5, 2, 1),
                            BlockStateProvider.simple(ItemAndBlockRegister.cypress_leaves.get()),
                            new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)),
                            new TwoLayersFeatureSize(2, 0, 2)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MEGA_CYPRESS =
            ConfiguredFeatures.register("mega_cypress", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.cypress_log.get()),
                            new GiantTrunkPlacer(13, 2, 14),
                            BlockStateProvider.simple(ItemAndBlockRegister.cypress_leaves.get()),
                            new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
                            new TwoLayersFeatureSize(1, 1, 2)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CEDAR =
            ConfiguredFeatures.register("japanese_cedar", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_log.get()),
                            new StraightTrunkPlacer(5, 2, 1),
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_leaves.get()),
                            new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)),
                            new TwoLayersFeatureSize(2, 0, 2)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MEGA_CEDAR =
            ConfiguredFeatures.register("mega_japanese_cedar", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_log.get()),
                            new GiantTrunkPlacer(13, 2, 14),
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_leaves.get()),
                            new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
                            new TwoLayersFeatureSize(1, 1, 2)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> KAKURIYO_MEGA_CEDAR =
            ConfiguredFeatures.register("mega_japanese_cedar_in_kakuriyo", () ->
                    new ConfiguredFeature<>(Feature.TREE, new KakuriyoTreeConfigration.KakuriyoTreeConfigurationBuilder(
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_log.get()),
                            new GiantTrunkPlacer(13, 2, 14),
                            BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_leaves.get()),
                            new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)),
                            new TwoLayersFeatureSize(1, 1, 2)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> YOMI_VINES =
            ConfiguredFeatures.register("yomi_vines", () ->
                    new ConfiguredFeature<>(Feature.BLOCK_COLUMN,
                            new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(
                                    new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().
                                            add(UniformInt.of(800, 1000), 2)
                                            .add(UniformInt.of(1, 2), 3)
                                            .add(UniformInt.of(1, 6), 30)
                                            .build()), new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ItemAndBlockRegister.yomi_vines_plant.get().defaultBlockState(), 4).add(ItemAndBlockRegister.yomi_vines_plant.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1))),
                                    BlockColumnConfiguration.layer(ConstantInt.of(1),
                                            new RandomizedIntStateProvider(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ItemAndBlockRegister.yomi_vines.get().defaultBlockState(), 4).add(ItemAndBlockRegister.yomi_vines.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1)), CaveVinesBlock.AGE, UniformInt.of(23, 25)))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_IRONSAND =
            ConfiguredFeatures.register("ore_ironsand", () ->
                    new ConfiguredFeature<>(Feature.ORE,
                            new OreConfiguration(List.of(OreConfiguration.target(NATURAL_SAND, ItemAndBlockRegister.ironsand_ore.get().defaultBlockState())), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_JADEITE =
            ConfiguredFeatures.register("ore_jadeite", () ->
                    new ConfiguredFeature<>(Feature.ORE,
                            new OreConfiguration(List.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.jadeite_ore.get().defaultBlockState())), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_IRON =
            ConfiguredFeatures.register("ore_yomi_iron", () ->
                    new ConfiguredFeature<>(Feature.ORE,
                            new OreConfiguration(List.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.yomi_iron_ore.get().defaultBlockState())), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_GOLD =
            ConfiguredFeatures.register("ore_yomi_gold", () ->
                    new ConfiguredFeature<>(Feature.ORE,
                            new OreConfiguration(List.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.yomi_gold_ore.get().defaultBlockState())), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_COPPER =
            ConfiguredFeatures.register("ore_yomi_copper", () ->
                    new ConfiguredFeature<>(Feature.ORE,
                            new OreConfiguration(List.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.yomi_copper_ore.get().defaultBlockState())), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SHIITAKE =
            ConfiguredFeatures.register("shiitake", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.shiitake.get()), 50)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> WALL_SHIITAKE_N =
            ConfiguredFeatures.register("wall_shiitake_n", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.NORTH)), 30)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> WALL_SHIITAKE_E =
            ConfiguredFeatures.register("wall_shiitake_e", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.EAST)), 30)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> WALL_SHIITAKE_S =
            ConfiguredFeatures.register("wall_shiitake_s", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.SOUTH)), 30)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> WALL_SHIITAKE_W =
            ConfiguredFeatures.register("wall_shiitake_w", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.WEST)), 30)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> EULALIA =
            ConfiguredFeatures.register("eulalia", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.eulalia.get()), 60)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> TALL_EULALIA =
            ConfiguredFeatures.register("tall_eulalia", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.double_eulalia.get()), 60)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> LYCORIS =
            ConfiguredFeatures.register("lycoris", () ->
                    new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR,
                            new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.lycoris.get()))))))));
    public static final RegistryObject<ConfiguredFeature<?, ?>> AJISAI =
            ConfiguredFeatures.register("ajisai", () ->
                    new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR,
                            new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.ajisai.get()))))))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> QUARTZ_CLUSTER =
            ConfiguredFeatures.register("quartz_cluster", () ->
                    new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR,
                            new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.quartz_cluster.get()))))))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> LANTERN_PLANT =
            ConfiguredFeatures.register("lantern_plant", () ->
                    new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR,
                            new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.lantern_plant.get().defaultBlockState().setValue(LanternPlantBlock.AGE,Integer.valueOf(1))))))))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FALLEN_SAKUEA_LEAVES =
            ConfiguredFeatures.register("fallen_sakura_leaves", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_sakura_leaves.get()), 20)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FALLEN_RED_LEAVES =
            ConfiguredFeatures.register("fallen_red_leaves", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_red_leaves.get()), 30)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FALLEN_ORANGE_LEAVES =
            ConfiguredFeatures.register("fallen_orange_leaves", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_orange_leaves.get()), 30)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FALLEN_YELLOW_LEAVES =
            ConfiguredFeatures.register("fallen_yellow_leaves", () ->
                    new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                            grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_yellow_leaves.get()), 20)));




 public static final RegistryObject<ConfiguredFeature<?, ?>> HOT_SPRING_SPAWN =
         ConfiguredFeatures.register("hot_spring_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                 new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                         PlacedFeatureRegister.HOT_SPRING_CHECKED.getHolder().get(),
                         0.5F)), PlacedFeatureRegister.HOT_SPRING_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> BAMBOO_SPAWN =
            ConfiguredFeatures.register("japanese_timber_bamboo_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.BAMBOO_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.BAMBOO_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> KAKURIYO_PORTAL_OVERWORLD_SPAWN =
            ConfiguredFeatures.register("kakuriyo_portal_overworld_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.KAKURIYO_PORTAL_OVERWORLD_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.KAKURIYO_PORTAL_OVERWORLD_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> APRICOT_SPAWN =
            ConfiguredFeatures.register("japanese_apricot_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.APRICOT_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.APRICOT_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GLOWING_APRICOT_SPAWN =
            ConfiguredFeatures.register("glowing_japanese_apricot_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.GLOWING_APRICOT_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.GLOWING_APRICOT_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SAKURA_SPAWN =
            ConfiguredFeatures.register("sakura_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.SAKURA_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.SAKURA_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GLOWING_SAKURA_SPAWN =
            ConfiguredFeatures.register("glowing_sakura_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.GLOWING_SAKURA_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.GLOWING_SAKURA_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FANCY_SAKURA_SPAWN =
            ConfiguredFeatures.register("fancy_sakura_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.FANCY_SAKURA_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.FANCY_SAKURA_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GLOWING_FANCY_SAKURA_SPAWN =
            ConfiguredFeatures.register("glowing_fancy_sakura_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.GLOWING_FANCY_SAKURA_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.GLOWING_FANCY_SAKURA_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> RED_SPAWN =
            ConfiguredFeatures.register("red_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.RED_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.RED_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORANGE_SPAWN =
            ConfiguredFeatures.register("orange_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.ORANGE_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.ORANGE_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> YELLOW_SPAWN =
            ConfiguredFeatures.register("yellow_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacedFeatureRegister.YELLOW_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.YELLOW_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> LACQUER_SPAWN =
            ConfiguredFeatures.register("lacquer_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.LACQUER_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.LACQUER_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CYPRESS_SPAWN =
            ConfiguredFeatures.register("cypress_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.CYPRESS_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.CYPRESS_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CEDAR_SPAWN =
            ConfiguredFeatures.register("japanese_cedar_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.CEDAR_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.CEDAR_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MEGA_CEDAR_SPAWN =
            ConfiguredFeatures.register("mega_japanese_cedar_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.MEGA_CEDAR_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.MEGA_CEDAR_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> KAKURIYO_MEGA_CEDAR_SPAWN =
            ConfiguredFeatures.register("mega_japanese_cedar_in_kakuriyo_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.KAKURIYO_MEGA_CEDAR_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.KAKURIYO_MEGA_CEDAR_CHECKED.getHolder().get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> YOMI_VINES_SPAWN =
            ConfiguredFeatures.register("yomi_vines_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            PlacedFeatureRegister.YOMI_VINES_CHECKED.getHolder().get(),
                            0.5F)), PlacedFeatureRegister.YOMI_VINES_CHECKED.getHolder().get())));






    static void register(IEventBus eventBus) {
        ConfiguredFeatures.register(eventBus);
    }
}
