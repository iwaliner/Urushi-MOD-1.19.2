package com.iwaliner.urushi;

import com.iwaliner.urushi.block.*;
import com.iwaliner.urushi.blockentity.ShichirinBlockEntity;
import com.iwaliner.urushi.mixin.VillagerMixin;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementItem;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.ChatFormatting;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

@Mod("urushi")
public class ModCoreUrushi {
    public static final String ModID = "urushi";
    public static File dataDirectory;
    public static File dataInBuildDirectory;
    public static File assetsDirectory;
    public static File assetsInBuildDirectory;

    public static List<String> pickaxeList=new ArrayList<>();
    public static List<String> axeList=new ArrayList<>();
    public static List<String> shovelList=new ArrayList<>();
    public static List<String> hoeList=new ArrayList<>();
    public static List<String> woodenToolList=new ArrayList<>();
    public static List<String> stoneToolList=new ArrayList<>();
    public static List<String> ironToolList=new ArrayList<>();
    public static List<String> goldenToolList=new ArrayList<>();
    public static List<String> diamondToolList=new ArrayList<>();
    public static List<String> netheriteToolList=new ArrayList<>();
    public static List<Item> underDevelopmentList=new ArrayList<>();

    public static boolean isDebug=FMLPaths.GAMEDIR.get().toString().contains("イワライナー")&&FMLPaths.GAMEDIR.get().toString().contains("run");
    public static Logger logger = LogManager.getLogger("urushi");
    public static final CreativeModeTab UrushiTab = new CreativeModeTab("urushi") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemAndBlockRegister.kasuga_lantern.get());
        }
    };
    public static final CreativeModeTab UrushiPlasterTab = new CreativeModeTab("urushi_plaster") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemAndBlockRegister.red_framed_plaster.get());
        }
    };
    public static final CreativeModeTab UrushiWoodTab = new CreativeModeTab("urushi_wood") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemAndBlockRegister.big_sakura_sapling.get());
        }
    };
    public static final CreativeModeTab UrushiFoodTab = new CreativeModeTab("urushi_food") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemAndBlockRegister.color_dango.get());
        }
    };
    public static final CreativeModeTab UrushiMagicTab = new CreativeModeTab("urushi_magic") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemAndBlockRegister.earth_element_magatama.get());
        }
    };

    public ModCoreUrushi() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        /**コンフィグを登録*/
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,ConfigUrushi.spec,"urushi.toml");

        /**アイテムとブロックを登録*/
        ItemAndBlockRegister.register(modEventBus);

        /**ブロックエンティティ(旧タイルエンティティ)を登録*/
        BlockEntityRegister.register(modEventBus);

        /**エンティティを登録*/
        EntityRegister.register(modEventBus);

        /**流体を登録*/
        FluidRegister.register(modEventBus);
        FluidTypeRegister.register(modEventBus);

        /**バイオームを登録*/
        BiomeRegister.register(modEventBus);

        /**レシピタイプを登録*/
        RecipeTypeRegister.register(modEventBus);

        /**メニュー(旧コンテナ)を登録*/
        MenuRegister.register(modEventBus);

        /**ディメンションを登録*/
        DimensionRegister.register();

        /**パーティクルを登録*/
       ParticleRegister.register(modEventBus);

        /**サウンドを登録*/
        SoundRegister.register(modEventBus);


        FeatureRegister.register(modEventBus);

        PlacedFeatureRegister.register(modEventBus);

        ConfiguredFeatureRegister.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }


    /**燃料を登録*/
    @SubscribeEvent
    public void FuelEvent(FurnaceFuelBurnTimeEvent event) {

        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
        DispenserBlock.registerBehavior(Items.BOWL, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level level = source.getLevel();
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = source.getPos().relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.getBlock() instanceof ChiseledLacquerLogBlock) {
                    if (blockstate.getValue(ChiseledLacquerLogBlock.FILLED)) {
                        this.setSuccess(true);
                        stack.shrink(1);
                        level.setBlockAndUpdate(blockpos, ItemAndBlockRegister.chiseled_lacquer_log.get().defaultBlockState().setValue(ChiseledLacquerLogBlock.FILLED, Boolean.valueOf(false)).setValue(ChiseledLacquerLogBlock.FACING, blockstate.getValue(ChiseledLacquerLogBlock.FACING)));
                        level.gameEvent((Entity) null, GameEvent.BLOCK_PLACE, blockpos);
                        if (stack.isEmpty()) {
                            return new ItemStack(ItemAndBlockRegister.raw_urushi_ball.get());
                        } else {
                            if (source.<DispenserBlockEntity>getEntity().addItem(new ItemStack(ItemAndBlockRegister.raw_urushi_ball.get()).copy()) < 0) {
                                defaultDispenseItemBehavior.dispense(source, new ItemStack(ItemAndBlockRegister.raw_urushi_ball.get()).copy());
                            }
                        }
                    }
                    return stack;
                }
                return super.execute(source, stack);
            }
        });


        DispenserBlock.registerBehavior(ItemAndBlockRegister.rice_crop.get(), new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level level = source.getLevel();
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = source.getPos().relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.getBlock() instanceof SenbakokiBlock) {
                    this.setSuccess(true);
                       stack.shrink(1);
                       level.gameEvent((Entity) null, GameEvent.BLOCK_PLACE, blockpos);
                        if (stack.isEmpty()) {
                            return new ItemStack(ItemAndBlockRegister.raw_rice.get(),2);
                        } else {
                            if (source.<DispenserBlockEntity>getEntity().addItem(new ItemStack(ItemAndBlockRegister.raw_rice.get(),2).copy()) < 0) {
                                defaultDispenseItemBehavior.dispense(source, new ItemStack(ItemAndBlockRegister.raw_rice.get(),2).copy());
                            }
                        }
                    return stack;
                }
                return super.execute(source, stack);
            }
        });


        TagUrushi.fileMap.put( Blocks.STONE.defaultBlockState(),Blocks.SMOOTH_STONE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.COBBLESTONE.defaultBlockState(),Blocks.STONE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ANDESITE.defaultBlockState(),Blocks.POLISHED_ANDESITE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DIORITE.defaultBlockState(),Blocks.POLISHED_DIORITE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.GRANITE.defaultBlockState(),Blocks.POLISHED_GRANITE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BASALT.defaultBlockState(),Blocks.SMOOTH_BASALT.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BLACKSTONE.defaultBlockState(),Blocks.POLISHED_BLACKSTONE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DEEPSLATE.defaultBlockState(),Blocks.POLISHED_DEEPSLATE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.COBBLED_DEEPSLATE.defaultBlockState(),Blocks.DEEPSLATE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.QUARTZ_BLOCK.defaultBlockState(),Blocks.SMOOTH_QUARTZ.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.RED_SANDSTONE.defaultBlockState(),Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SANDSTONE.defaultBlockState(),Blocks.SMOOTH_SANDSTONE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.OAK_PLANKS.defaultBlockState(),ItemAndBlockRegister.smooth_oak_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SPRUCE_PLANKS.defaultBlockState(),ItemAndBlockRegister.smooth_spruce_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BIRCH_PLANKS.defaultBlockState(),ItemAndBlockRegister.smooth_birch_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.JUNGLE_PLANKS.defaultBlockState(),ItemAndBlockRegister.smooth_jungle_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ACACIA_PLANKS.defaultBlockState(),ItemAndBlockRegister.smooth_acacia_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DARK_OAK_PLANKS.defaultBlockState(),ItemAndBlockRegister.smooth_dark_oak_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_apricot_planks.get().defaultBlockState(),ItemAndBlockRegister.smooth_japanese_apricot_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.sakura_planks.get().defaultBlockState(),ItemAndBlockRegister.smooth_sakura_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cypress_planks.get().defaultBlockState(),ItemAndBlockRegister.smooth_cypress_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_cedar_planks.get().defaultBlockState(),ItemAndBlockRegister.smooth_japanese_cedar_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.red_urushi_planks.get().defaultBlockState(),ItemAndBlockRegister.smooth_red_urushi_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.black_urushi_planks.get().defaultBlockState(),ItemAndBlockRegister.smooth_black_urushi_planks.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cobbled_yomi_stone.get().defaultBlockState(),ItemAndBlockRegister.yomi_stone.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.yomi_stone.get().defaultBlockState(),ItemAndBlockRegister.polished_yomi_stone.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.jadeite_block.get().defaultBlockState(),ItemAndBlockRegister.smooth_jadeite_block.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ANDESITE_SLAB.defaultBlockState(),Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ANDESITE_STAIRS.defaultBlockState(),Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DIORITE_SLAB.defaultBlockState(),Blocks.POLISHED_DIORITE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DIORITE_STAIRS.defaultBlockState(),Blocks.POLISHED_DIORITE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.GRANITE_SLAB.defaultBlockState(),Blocks.POLISHED_GRANITE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.GRANITE_STAIRS.defaultBlockState(),Blocks.POLISHED_GRANITE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.COBBLESTONE_SLAB.defaultBlockState(),Blocks.STONE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.COBBLESTONE_STAIRS.defaultBlockState(),Blocks.STONE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.STONE_SLAB.defaultBlockState(),Blocks.SMOOTH_STONE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BLACKSTONE_SLAB.defaultBlockState(),Blocks.POLISHED_BLACKSTONE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BLACKSTONE_STAIRS.defaultBlockState(),Blocks.POLISHED_BLACKSTONE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.COBBLED_DEEPSLATE_SLAB.defaultBlockState(),Blocks.POLISHED_DEEPSLATE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.COBBLED_DEEPSLATE_STAIRS.defaultBlockState(),Blocks.POLISHED_DEEPSLATE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.COBBLED_DEEPSLATE_WALL.defaultBlockState(),Blocks.POLISHED_DEEPSLATE_WALL.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.QUARTZ_SLAB.defaultBlockState(),Blocks.SMOOTH_QUARTZ_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.QUARTZ_STAIRS.defaultBlockState(),Blocks.SMOOTH_QUARTZ_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.RED_SANDSTONE_SLAB.defaultBlockState(),Blocks.SMOOTH_RED_SANDSTONE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.RED_SANDSTONE_STAIRS.defaultBlockState(),Blocks.SMOOTH_RED_SANDSTONE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SANDSTONE_SLAB.defaultBlockState(),Blocks.SMOOTH_SANDSTONE_SLAB.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SANDSTONE_STAIRS.defaultBlockState(),Blocks.SMOOTH_SANDSTONE_STAIRS.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.OAK_SLAB.defaultBlockState(),ItemAndBlockRegister.smooth_oak_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.OAK_STAIRS.defaultBlockState(),ItemAndBlockRegister.smooth_oak_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SPRUCE_SLAB.defaultBlockState(),ItemAndBlockRegister.smooth_spruce_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SPRUCE_STAIRS.defaultBlockState(),ItemAndBlockRegister.smooth_spruce_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BIRCH_SLAB.defaultBlockState(),ItemAndBlockRegister.smooth_birch_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BIRCH_STAIRS.defaultBlockState(),ItemAndBlockRegister.smooth_birch_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.JUNGLE_SLAB.defaultBlockState(),ItemAndBlockRegister.smooth_jungle_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.JUNGLE_STAIRS.defaultBlockState(),ItemAndBlockRegister.smooth_jungle_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ACACIA_SLAB.defaultBlockState(),ItemAndBlockRegister.smooth_acacia_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ACACIA_STAIRS.defaultBlockState(),ItemAndBlockRegister.smooth_acacia_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DARK_OAK_SLAB.defaultBlockState(),ItemAndBlockRegister.smooth_dark_oak_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DARK_OAK_STAIRS.defaultBlockState(),ItemAndBlockRegister.smooth_dark_oak_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_apricot_slab.get().defaultBlockState(),ItemAndBlockRegister.smooth_japanese_apricot_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_apricot_stairs.get().defaultBlockState(),ItemAndBlockRegister.smooth_japanese_apricot_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.sakura_slab.get().defaultBlockState(),ItemAndBlockRegister.smooth_sakura_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.sakura_stairs.get().defaultBlockState(),ItemAndBlockRegister.smooth_sakura_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cypress_slab.get().defaultBlockState(),ItemAndBlockRegister.smooth_cypress_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cypress_stairs.get().defaultBlockState(),ItemAndBlockRegister.smooth_cypress_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_cedar_slab.get().defaultBlockState(),ItemAndBlockRegister.smooth_japanese_cedar_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_cedar_stairs.get().defaultBlockState(),ItemAndBlockRegister.smooth_japanese_cedar_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.red_urushi_slab.get().defaultBlockState(),ItemAndBlockRegister.smooth_red_urushi_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.red_urushi_stairs.get().defaultBlockState(),ItemAndBlockRegister.smooth_red_urushi_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.black_urushi_slab.get().defaultBlockState(),ItemAndBlockRegister.smooth_black_urushi_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.black_urushi_stairs.get().defaultBlockState(),ItemAndBlockRegister.smooth_black_urushi_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cobbled_yomi_stone_slab.get().defaultBlockState(),ItemAndBlockRegister.yomi_stone_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cobbled_yomi_stone_stairs.get().defaultBlockState(),ItemAndBlockRegister.yomi_stone_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.yomi_stone_slab.get().defaultBlockState(),ItemAndBlockRegister.polished_yomi_stone_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.yomi_stone_stairs.get().defaultBlockState(),ItemAndBlockRegister.polished_yomi_stone_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.jadeite_slab.get().defaultBlockState(),ItemAndBlockRegister.smooth_jadeite_slab.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.jadeite_stairs.get().defaultBlockState(),ItemAndBlockRegister.smooth_jadeite_stairs.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cobbled_yomi_stone_wall.get().defaultBlockState(),ItemAndBlockRegister.polished_yomi_stone_wall.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.OAK_LOG.defaultBlockState(),Blocks.STRIPPED_OAK_LOG.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BIRCH_LOG.defaultBlockState(),Blocks.STRIPPED_BIRCH_LOG.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SPRUCE_LOG.defaultBlockState(),Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.JUNGLE_LOG.defaultBlockState(),Blocks.STRIPPED_JUNGLE_LOG.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ACACIA_LOG.defaultBlockState(),Blocks.STRIPPED_ACACIA_LOG.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DARK_OAK_LOG.defaultBlockState(),Blocks.STRIPPED_DARK_OAK_LOG.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.MANGROVE_LOG.defaultBlockState(),Blocks.STRIPPED_MANGROVE_LOG.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.CRIMSON_STEM.defaultBlockState(),Blocks.STRIPPED_CRIMSON_STEM.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.WARPED_STEM.defaultBlockState(),Blocks.STRIPPED_WARPED_STEM.defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.sakura_log.get().defaultBlockState(),ItemAndBlockRegister.stripped_sakura_log.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_apricot_log.get().defaultBlockState(),ItemAndBlockRegister.stripped_japanese_apricot_log.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cypress_log.get().defaultBlockState(),ItemAndBlockRegister.stripped_cypress_log.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_cedar_log.get().defaultBlockState(),ItemAndBlockRegister.stripped_japanese_cedar_log.get().defaultBlockState());
        TagUrushi.fileMap.put( Blocks.OAK_WOOD.defaultBlockState(),Blocks.STRIPPED_OAK_WOOD.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.BIRCH_WOOD.defaultBlockState(),Blocks.STRIPPED_BIRCH_WOOD.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.SPRUCE_WOOD.defaultBlockState(),Blocks.STRIPPED_SPRUCE_WOOD.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.JUNGLE_WOOD.defaultBlockState(),Blocks.STRIPPED_JUNGLE_WOOD.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.ACACIA_WOOD.defaultBlockState(),Blocks.STRIPPED_ACACIA_WOOD.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.DARK_OAK_WOOD.defaultBlockState(),Blocks.STRIPPED_DARK_OAK_WOOD.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.MANGROVE_WOOD.defaultBlockState(),Blocks.STRIPPED_MANGROVE_WOOD.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.CRIMSON_HYPHAE.defaultBlockState(),Blocks.STRIPPED_CRIMSON_HYPHAE.defaultBlockState());
        TagUrushi.fileMap.put( Blocks.WARPED_HYPHAE.defaultBlockState(),Blocks.STRIPPED_WARPED_HYPHAE.defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.sakura_wood.get().defaultBlockState(),ItemAndBlockRegister.stripped_sakura_wood.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_apricot_wood.get().defaultBlockState(),ItemAndBlockRegister.stripped_japanese_apricot_wood.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.cypress_wood.get().defaultBlockState(),ItemAndBlockRegister.stripped_cypress_wood.get().defaultBlockState());
        TagUrushi.fileMap.put( ItemAndBlockRegister.japanese_cedar_wood.get().defaultBlockState(),ItemAndBlockRegister.stripped_japanese_cedar_wood.get().defaultBlockState());


      /*  Villager.FOOD_POINTS.put(ItemAndBlockRegister.rice.get(), 3);
        Villager.FOOD_POINTS.put(ItemAndBlockRegister.rice_cake.get(), 4);
        Villager.FOOD_POINTS.put(ItemAndBlockRegister.roasted_rice_cake.get(), 4);
        Villager.FOOD_POINTS.put(ItemAndBlockRegister.tofu.get(), 2);*/



        TagUrushi.earthMiningSpeedChangeItemMap.put( ItemAndBlockRegister.wood_element_magatama.get(),50);
        TagUrushi.fireMiningSpeedChangeItemMap.put( ItemAndBlockRegister.wood_element_magatama.get(),-30);
        TagUrushi.woodMiningSpeedChangeItemMap.put( ItemAndBlockRegister.wood_element_magatama.get(),-40);
        TagUrushi.metalMiningSpeedChangeItemMap.put( ItemAndBlockRegister.fire_element_magatama.get(),50);
        TagUrushi.earthMiningSpeedChangeItemMap.put( ItemAndBlockRegister.fire_element_magatama.get(),-30);
        TagUrushi.fireMiningSpeedChangeItemMap.put( ItemAndBlockRegister.fire_element_magatama.get(),-40);
        TagUrushi.waterMiningSpeedChangeItemMap.put( ItemAndBlockRegister.earth_element_magatama.get(),50);
        TagUrushi.metalMiningSpeedChangeItemMap.put( ItemAndBlockRegister.earth_element_magatama.get(),-30);
        TagUrushi.earthMiningSpeedChangeItemMap.put( ItemAndBlockRegister.earth_element_magatama.get(),-40);
        TagUrushi.woodMiningSpeedChangeItemMap.put( ItemAndBlockRegister.metal_element_magatama.get(),50);
        TagUrushi.waterMiningSpeedChangeItemMap.put( ItemAndBlockRegister.metal_element_magatama.get(),-30);
        TagUrushi.metalMiningSpeedChangeItemMap.put( ItemAndBlockRegister.metal_element_magatama.get(),-40);
        TagUrushi.fireMiningSpeedChangeItemMap.put( ItemAndBlockRegister.water_element_magatama.get(),50);
        TagUrushi.woodMiningSpeedChangeItemMap.put( ItemAndBlockRegister.water_element_magatama.get(),-30);
        TagUrushi.waterMiningSpeedChangeItemMap.put( ItemAndBlockRegister.water_element_magatama.get(),-40);




        if (event.getItemStack().getItem()==ItemAndBlockRegister.bamboo_charcoal.get()) {
            event.setBurnTime(1600);
        }else if(event.getItemStack().getItem()==ItemAndBlockRegister.japanese_apricot_bark.get()) {
            event.setBurnTime(200);
        }else if(event.getItemStack().getItem()==ItemAndBlockRegister.sakura_bark.get()) {
            event.setBurnTime(200);
        }else if(event.getItemStack().getItem()==ItemAndBlockRegister.cypress_bark.get()) {
            event.setBurnTime(200);
        }else if(event.getItemStack().getItem()==ItemAndBlockRegister.wood_chip.get()) {
            event.setBurnTime(200);
        }else if(event.getItemStack().getItem()== Item.byBlock(ItemAndBlockRegister.bamboo_charcoal_block.get())) {
            event.setBurnTime(16000);
        }
    }

    /**プレイヤーの移動速度を上昇*/
    @SubscribeEvent
    public void PlayerSpeedEvent(EntityEvent.EnteringSection event) {
        if(ConfigUrushi.TurnOnSpeedUp.get()) {
            if (event.getEntity() instanceof Player) {

                Player entityPlayer = (Player) event.getEntity();
                if(!entityPlayer.isPassenger()) {
                    Objects.requireNonNull(entityPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.116D);
                    entityPlayer.getAttributes().save();

                }
            }
        }

    }


    /**草を壊して種が出るように*/
    @SubscribeEvent
    public void GrassDropEvent(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().isCreative() && (event.getLevel().getBlockState(event.getPos()).getBlock()==Blocks.FERN || event.getLevel().getBlockState(event.getPos()).getBlock()==Blocks.TALL_GRASS || event.getLevel().getBlockState(event.getPos()).getBlock()==Blocks.GRASS) ) {
            if (( event.getLevel()).getRandom().nextFloat() < 0.075F) {
                ItemEntity entity = new ItemEntity((Level) event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(ItemAndBlockRegister.rice_crop.get()));
                event.getLevel().addFreshEntity(entity);
            }
            else if (( event.getLevel()).getRandom().nextFloat() < 0.075F) {
                ItemEntity entity = new ItemEntity((Level) event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(ItemAndBlockRegister.soy_crop.get()));
                event.getLevel().addFreshEntity(entity);
            } else if (( event.getLevel()).getRandom().nextFloat() < 0.075F) {
                ItemEntity entity = new ItemEntity((Level) event.getLevel(), (double) event.getPos().getX(), (double) event.getPos().getY(), (double) event.getPos().getZ(), new ItemStack(ItemAndBlockRegister.azuki_crop.get()));
                event.getLevel().addFreshEntity(entity);
            }else if (( event.getLevel()).getRandom().nextFloat() < 0.075F) {
                ItemEntity entity = new ItemEntity((Level) event.getLevel(), (double) event.getPos().getX(), (double) event.getPos().getY(), (double) event.getPos().getZ(), new ItemStack(ItemAndBlockRegister.green_onion_crop.get()));
                event.getLevel().addFreshEntity(entity);
            }
        }
    }

    /**玉鋼作るときに右クリックおしっぱだとブロックがドロップして壊れる*/
    @SubscribeEvent
    public void HammerCancelEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().getBlockState(event.getPos()).getBlock() instanceof IronIngotBlock){
            {
                if(event.getEntity() instanceof Player) {
                    if( event.getEntity().getCooldowns().isOnCooldown(ItemAndBlockRegister.hammer.get())) {
                        event.getLevel().destroyBlock(event.getPos(),true);
                        event.setCanceled(true);
                    }
                }
            }
        }

        if(isDebug){
            LevelAccessor level=event.getLevel();
            BlockPos pos=event.getPos();
            BlockState currentState=event.getLevel().getBlockState(pos);
            BlockState sojoState=ElementUtils.getSojoBlock(ElementUtils.getSojoBlock(event.getLevel().getBlockState(event.getPos())));
            if(sojoState!=null){
                event.getLevel().setBlockAndUpdate(event.getPos(),sojoState);
            }

        }
    }

    /**孫の手で手の届く範囲を広げる*/
  /*  @SubscribeEvent
    public void MagonoteEvent(PlayerInteractEvent event) {

                if(event.getEntity() instanceof Player) {

                    int amount=0;

                    for (int i = 0; i < event.getPlayer().getInventory().getContainerSize(); ++i)
                    {
                        ItemStack stack =  event.getPlayer().getInventory().getItem(i);
                        if(stack.getItem()==ItemAndBlockRegister.magonote.get()){
                            amount+=stack.getCount();
                            if(event instanceof PlayerInteractEvent.RightClickBlock||event instanceof PlayerInteractEvent.LeftClickBlock) {
                                event.getPlayer().getInventory().getItem(i).hurtAndBreak(1, event.getPlayer(), (x) -> {
                                });
                            }
                        }
                    }
                      Objects.requireNonNull(event.getPlayer().getAttribute(ForgeMod.REACH_DISTANCE.get())).setBaseValue(5D+amount*1D);


                }


    }*/

    /**葉の上に落下したとき落下ダメージを受けないように*/
    @SubscribeEvent
    public void LeavesDamageEvent(LivingHurtEvent event) {
        if(event.getSource()== DamageSource.FALL){
            if(event.getEntity().level.getBlockState(event.getEntity().blockPosition().below()).getBlock() instanceof LeavesBlock){
                event.setCanceled(true);
            }
        }
    }

    /**砂が海岸や海系のバイオーム内で水に接すると塩を含んだ砂になる*/
    @SubscribeEvent
    public void SaltEvent(BlockEvent.NeighborNotifyEvent event) {

        if (event.getLevel().getBiome(event.getPos()).is(BiomeTags.IS_BEACH)||event.getLevel().getBiome(event.getPos()).is(BiomeTags.IS_OCEAN)||event.getLevel().getBiome(event.getPos()).is(BiomeTags.IS_DEEP_OCEAN)) {
            if (event.getState().getMaterial() == Material.WATER) {
                for (int i = 0; i < 6; i++) {
                    if (event.getLevel().getBlockState(event.getPos().relative(UrushiUtils.getDirectionFromInt(i))).getBlock() == Blocks.SAND) {
                        event.getLevel().setBlock(event.getPos().relative(UrushiUtils.getDirectionFromInt(i)), ItemAndBlockRegister.salt_and_sand.get().defaultBlockState(), 2);
                        event.getLevel().playSound((Player) null, event.getPos().relative(UrushiUtils.getDirectionFromInt(i)), SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0F, 1F);
                    }
                }

            }
            else if (event.getState().getBlock() == Blocks.SAND) {
                UrushiUtils.BlockChangeNeighborMaterialSurvey((Level) event.getLevel(),event.getPos(),Material.WATER,ItemAndBlockRegister.salt_and_sand.get().defaultBlockState(), SoundEvents.SAND_BREAK);
            }
        }

        if(ModCoreUrushi.isDebug){
            LevelAccessor level=event.getLevel();
            BlockPos pos=event.getPos();
            BlockState currentState=level.getBlockState(pos);

            for (int i = 2; i < 6; i++) {
                if (ElementUtils.isSoukokuBlock(level,pos.relative(UrushiUtils.getDirectionFromInt(i)),currentState)) {
                    level.setBlock(pos.relative(UrushiUtils.getDirectionFromInt(i)),Blocks.AIR.defaultBlockState(), 2);
                    level.setBlock(pos.relative(UrushiUtils.getDirectionFromInt(i)).above(200),ElementUtils.getRandomElementBlock(level), 2);

                    event.getLevel().playSound((Player) null, event.getPos().relative(UrushiUtils.getDirectionFromInt(i)), SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0F, 1F);
                }
            }
        }
    }

    /**油揚げを狐が食べると狐火に*/
    @SubscribeEvent
    public void FoxEvent(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Fox &&event.getFrom().getItem()== ItemAndBlockRegister.aburaage.get()) {
            event.getEntity().setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(ItemAndBlockRegister.kitsunebiItem.get()));
        }

    }
    /**アイテムに説明書きを追加*/
    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent event) {

        ItemStack stack = event.getItemStack();
        Item item = event.getItemStack().getItem();
        if (item instanceof ElementItem) {
            ElementItem elementItem = (ElementItem) item;
            ElementType elementType = elementItem.getElementType();
            if (elementType == ElementType.WoodElement) {
                event.getToolTip().add((Component.translatable("info.urushi.wood_element_item")).withStyle(ChatFormatting.DARK_GREEN));
            } else if (elementType == ElementType.FireElement) {
                event.getToolTip().add((Component.translatable("info.urushi.fire_element_item")).withStyle(ChatFormatting.DARK_RED));
            } else if (elementType == ElementType.EarthElement) {
                event.getToolTip().add((Component.translatable("info.urushi.earth_element_item")).withStyle(ChatFormatting.GOLD));
            } else if (elementType == ElementType.MetalElement) {
                event.getToolTip().add((Component.translatable("info.urushi.metal_element_item")).withStyle(ChatFormatting.GRAY));
            } else if (elementType == ElementType.WaterElement) {
                event.getToolTip().add((Component.translatable("info.urushi.water_element_item")).withStyle(ChatFormatting.DARK_PURPLE));
            }
        }
        if (Block.byItem(event.getItemStack().getItem()) instanceof Tiered) {
            Tiered tiered = (Tiered) Block.byItem(event.getItemStack().getItem());
            int tier = tiered.getTier();
            if (tier == 1) {
                event.getToolTip().add((Component.translatable("info.urushi.tier1")).withStyle(ChatFormatting.GRAY));
            } else if (tier == 2) {
                event.getToolTip().add((Component.translatable("info.urushi.tier2")).withStyle(ChatFormatting.GRAY));
            } else if (tier == 3) {
                event.getToolTip().add((Component.translatable("info.urushi.tier3")).withStyle(ChatFormatting.GRAY));
            }
        }
        if(!ConfigUrushi.disableBlockElementDisplaying.get()){
        if (Block.byItem(event.getItemStack().getItem()) != Blocks.AIR) {
            BlockState state = Block.byItem(event.getItemStack().getItem()).defaultBlockState();
            if (ElementUtils.isWoodElement(state)) {
                event.getToolTip().add((Component.translatable("info.urushi.wood_element_block")).withStyle(ChatFormatting.DARK_GREEN));
            }
            if (ElementUtils.isFireElement(state)) {
                event.getToolTip().add((Component.translatable("info.urushi.fire_element_block")).withStyle(ChatFormatting.DARK_RED));
            }
            if (ElementUtils.isEarthElement(state)) {
                event.getToolTip().add((Component.translatable("info.urushi.earth_element_block")).withStyle(ChatFormatting.GOLD));
            }
            if (ElementUtils.isMetalElement(state)) {
                event.getToolTip().add((Component.translatable("info.urushi.metal_element_block")).withStyle(ChatFormatting.GRAY));
            }
            if (ElementUtils.isWaterElement(state)) {
                event.getToolTip().add((Component.translatable("info.urushi.water_element_block")).withStyle(ChatFormatting.DARK_PURPLE));
            }
        }
    }
        if(ElementUtils.isMiningSpeedChanger(item)){
            event.getToolTip().add((Component.translatable("info.urushi.miningSpeedChanger1" )).withStyle(ChatFormatting.GRAY));
            if(ElementUtils.getExtraMiningPercent(item,ElementType.WoodElement)!=0){
                ElementUtils.setBreakSpeedInfo(event.getToolTip(),ElementUtils.getExtraMiningPercent(item,ElementType.WoodElement),ElementType.WoodElement);
            }
            if(ElementUtils.getExtraMiningPercent(item,ElementType.FireElement)!=0){
                ElementUtils.setBreakSpeedInfo(event.getToolTip(),ElementUtils.getExtraMiningPercent(item,ElementType.FireElement),ElementType.FireElement);
            }
            if(ElementUtils.getExtraMiningPercent(item,ElementType.EarthElement)!=0){
                ElementUtils.setBreakSpeedInfo(event.getToolTip(),ElementUtils.getExtraMiningPercent(item,ElementType.EarthElement),ElementType.EarthElement);
            }
            if(ElementUtils.getExtraMiningPercent(item,ElementType.MetalElement)!=0){
                ElementUtils.setBreakSpeedInfo(event.getToolTip(),ElementUtils.getExtraMiningPercent(item,ElementType.MetalElement),ElementType.MetalElement);
            }
            if(ElementUtils.getExtraMiningPercent(item,ElementType.WaterElement)!=0){
                ElementUtils.setBreakSpeedInfo(event.getToolTip(),ElementUtils.getExtraMiningPercent(item,ElementType.WaterElement),ElementType.WaterElement);
            }
        }
        if(stack.getTag()!=null){
            CompoundTag tag=stack.getTag();
            if(tag.contains("cookingEnum")){
                int i=tag.getInt("cookingEnum");
                int level=ShichirinBlockEntity.getCookingLevel(i);
                ChatFormatting color=ChatFormatting.WHITE;
                if(i==0){
                    color= ChatFormatting.AQUA;
                }else if(i<3){
                    color= ChatFormatting.AQUA;
                }else if(i<5){
                    color= ChatFormatting.AQUA;
                }else if(i<7){
                    color= ChatFormatting.YELLOW;
                }else if(i<9){
                    color= ChatFormatting.YELLOW;
                }else if(i==9){
                    color= ChatFormatting.YELLOW;
                }else if(i<12){
                    color= ChatFormatting.YELLOW;
                }else if(i<14){
                    color= ChatFormatting.YELLOW;
                }else if(i<16){
                    color= ChatFormatting.RED;
                }else if(i<18){
                    color= ChatFormatting.RED;
                }else{
                    color= ChatFormatting.RED;
                }
                if(ShichirinBlockEntity.getCookingType(i).equals("undercooked")){
                    event.getToolTip().add((Component.translatable("info.urushi.undercooked" ).append(" "+level)).withStyle(color));
                }else if(ShichirinBlockEntity.getCookingType(i).equals("wellcooked")){
                    event.getToolTip().add((Component.translatable("info.urushi.wellcooked" ).append(" "+level)).withStyle(color));
                }else{
                    event.getToolTip().add((Component.translatable("info.urushi.overcooked" ).append(" "+level)).withStyle(color));
                }
            }
        }
        if(underDevelopmentList.contains(stack.getItem())){
            UrushiUtils.setInfoWithColor(event.getToolTip(),"under_development",ChatFormatting.YELLOW);
        }
        if(stack.getItem()==Item.byBlock(ItemAndBlockRegister.cypress_sapling.get())){
            UrushiUtils.setInfo(event.getToolTip(),"cypress_sapling");
        }else if(stack.getItem()==Item.byBlock(ItemAndBlockRegister.japanese_cedar_sapling.get())){
            UrushiUtils.setInfo(event.getToolTip(),"japanese_cedar_sapling");
        }else if(stack.getItem()==Item.byBlock(ItemAndBlockRegister.lacquer_sapling.get())){
            UrushiUtils.setInfo(event.getToolTip(),"lacquer_sapling");
        }
        if(Block.byItem(item) instanceof AbstractFramedBlock||Block.byItem(item) instanceof FramedPaneBlock){
            event.getToolTip().add((Component.translatable("info.urushi.framed_block1" )).withStyle(ChatFormatting.GRAY));
            String keyString=  ClientSetUp.connectionKey.getKey().getName();
            String begin=".";
            int beginIndex = keyString.indexOf(begin);
            String preExtractedKey = keyString.substring(beginIndex+1);
            int beginIndex2 = preExtractedKey.indexOf(begin);
            String extractedKey = preExtractedKey.substring(beginIndex2+1);
            event.getToolTip().add((Component.translatable("info.urushi.framed_block2").append(" '"+extractedKey+"' ").append(Component.translatable("info.urushi.framed_block3"))).withStyle(ChatFormatting.GRAY));
            event.getToolTip().add((Component.translatable("info.urushi.framed_block4")).withStyle(ChatFormatting.GRAY));

        }
    }
    /**ブロックの破壊速度を変更*/
    @SubscribeEvent
    public void BreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        try {
            if(ElementUtils.isWoodElement(event.getState())){
                event.setNewSpeed(event.getOriginalSpeed()*ElementUtils.countMiningPercentByInventory(event.getEntity(),ElementType.WoodElement));
            }
            if(ElementUtils.isFireElement(event.getState())){
                event.setNewSpeed(event.getOriginalSpeed()*ElementUtils.countMiningPercentByInventory(event.getEntity(),ElementType.FireElement));
            }
            if(ElementUtils.isEarthElement(event.getState())){
                event.setNewSpeed(event.getOriginalSpeed()*ElementUtils.countMiningPercentByInventory(event.getEntity(),ElementType.EarthElement));
            }
            if(ElementUtils.isMetalElement(event.getState())){
                event.setNewSpeed(event.getOriginalSpeed()*ElementUtils.countMiningPercentByInventory(event.getEntity(),ElementType.MetalElement));
            }
            if(ElementUtils.isWaterElement(event.getState())){
                event.setNewSpeed(event.getOriginalSpeed()*ElementUtils.countMiningPercentByInventory(event.getEntity(),ElementType.WaterElement));
            }
        } catch (Throwable var3) {
        }

    }

    /**食べた後の処理*/
    @SubscribeEvent
    public void FoodEatEvent(LivingEntityUseItemEvent.Finish event) {
        LivingEntity livingEntity=event.getEntity();
        ItemStack stack=event.getResultStack();
        CompoundTag tag=stack.getTag();
        if(tag==null){
            return;
        }
        if(tag.contains("cookingEnum")){
            int ID=tag.getInt("cookingEnum");
            int level=ShichirinBlockEntity.getCookingLevel(ID);
            if(ShichirinBlockEntity.getCookingType(ID).equals("undercooked")){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER,300+60*level,level+15));
            }else if(ShichirinBlockEntity.getCookingType(ID).equals("wellcooked")){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40+level*10, 1));
                if(ID==9){
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,200,0));
                }
            }else{
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON,10+10*level,1));
            }
          }

    }

    /**バニラのルートテーブルに内容を追加*/
    @SubscribeEvent
    public void LoottableEvent(LootTableLoadEvent event) {
        if(event.getName().equals(BuiltInLootTables.FISHING_FISH)){
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemAndBlockRegister.sweetfish.get()).setWeight(25)).add(LootItem.lootTableItem(ItemAndBlockRegister.tsuna.get()).setWeight(25)).build());
        }else if(event.getName().equals(BuiltInLootTables.SIMPLE_DUNGEON)||event.getName().equals(BuiltInLootTables.SPAWN_BONUS_CHEST)||event.getName().equals(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)){
            event.getTable().addPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemAndBlockRegister.lacquer_sapling.get()).setWeight(30)).build());
        }
    }
    /**バニラのルートテーブルに内容を追加*/
    @SubscribeEvent
    public void EntityDropItemEvent(LivingDropsEvent event) {

        if(event.getEntity() instanceof Squid){
       event.getDrops().add(new ItemEntity(event.getEntity().level,event.getEntity().getX(),event.getEntity().getY(),event.getEntity().getZ(), new ItemStack(ItemAndBlockRegister.squid_sashimi.get())));
        }
    }

    @SubscribeEvent
    public void ElementPaperCraftEvent(PlayerInteractEvent.RightClickBlock event) {

        if (event.getEntity() != null) {
            Block block=event.getLevel().getBlockState(event.getPos()).getBlock();
            if(!event.getEntity().isSuppressingBounce()&&block!=ItemAndBlockRegister.sanbo_tier1.get()) {
                if (((LivingEntity) event.getEntity()).getItemInHand(event.getHand()).getItem() == ItemAndBlockRegister.shide.get()) {
                    if (ElementUtils.isWoodElement(event.getLevel().getBlockState(event.getPos()))) {
                        ((LivingEntity) event.getEntity()).setItemInHand(event.getHand(), new ItemStack(ItemAndBlockRegister.wood_element_paper.get(), ((LivingEntity) event.getEntity()).getItemInHand(event.getHand()).getCount()));
                        event.getLevel().playSound((Player) null, event.getPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1F, 1F);
                    } else if (ElementUtils.isFireElement(event.getLevel().getBlockState(event.getPos()))) {
                        ((LivingEntity) event.getEntity()).setItemInHand(event.getHand(), new ItemStack(ItemAndBlockRegister.fire_element_paper.get(), ((LivingEntity) event.getEntity()).getItemInHand(event.getHand()).getCount()));
                        event.getLevel().playSound((Player) null, event.getPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1F, 1F);
                    } else if (ElementUtils.isEarthElement(event.getLevel().getBlockState(event.getPos()))) {
                        ((LivingEntity) event.getEntity()).setItemInHand(event.getHand(), new ItemStack(ItemAndBlockRegister.earth_element_paper.get(), ((LivingEntity) event.getEntity()).getItemInHand(event.getHand()).getCount()));
                        event.getLevel().playSound((Player) null, event.getPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1F, 1F);
                    } else if (ElementUtils.isMetalElement(event.getLevel().getBlockState(event.getPos()))) {
                        ((LivingEntity) event.getEntity()).setItemInHand(event.getHand(), new ItemStack(ItemAndBlockRegister.metal_element_paper.get(), ((LivingEntity) event.getEntity()).getItemInHand(event.getHand()).getCount()));
                        event.getLevel().playSound((Player) null, event.getPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1F, 1F);
                    } else if (ElementUtils.isWaterElement(event.getLevel().getBlockState(event.getPos()))) {
                        ((LivingEntity) event.getEntity()).setItemInHand(event.getHand(), new ItemStack(ItemAndBlockRegister.water_element_paper.get(), ((LivingEntity) event.getEntity()).getItemInHand(event.getHand()).getCount()));
                        event.getLevel().playSound((Player) null, event.getPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1F, 1F);
                    }
                }
            }
        }
    }


}
