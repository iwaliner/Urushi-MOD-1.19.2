package com.iwaliner.urushi.util;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.item.AbstractMagatamaItem;
import com.iwaliner.urushi.util.interfaces.*;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.*;


public class ElementUtils {

    public static boolean hasReiryoku(Item item) {
        return item instanceof HasReiryokuItem;
    }

    public static boolean isWoodElementItem(ItemStack stack) {
        return stack.is(TagUrushi.WOOD_ELEMENT_ITEM);
    }

    public static boolean isFireElementItem(ItemStack stack) {
        return stack.is(TagUrushi.FIRE_ELEMENT_ITEM);
    }

    public static boolean isEarthElementItem(ItemStack stack) {
        return stack.is(TagUrushi.EARTH_ELEMENT_ITEM);
    }

    public static boolean isMetalElementItem(ItemStack stack) {
        return stack.is(TagUrushi.METAL_ELEMENT_ITEM);
    }

    public static boolean isWaterElementItem(ItemStack stack) {
        return stack.is(TagUrushi.WATER_ELEMENT_ITEM);
    }

    public static boolean isMiningSpeedChanger(Item item) {
        return TagUrushi.elementMiningSpeedChangeItemMap.containsKey(item);
    }

    public static boolean isWoodMiningSpeedChanger(Item item) {
        return isMiningSpeedChanger(item) && TagUrushi.elementMiningSpeedChangeItemMap.get(item).wood() != 0;
    }

    public static boolean isFireMiningSpeedChanger(Item item) {
        return isMiningSpeedChanger(item) && TagUrushi.elementMiningSpeedChangeItemMap.get(item).fire() != 0;
    }

    public static boolean isEarthMiningSpeedChanger(Item item) {
        return isMiningSpeedChanger(item) && TagUrushi.elementMiningSpeedChangeItemMap.get(item).earth() != 0;
    }

    public static boolean isMetalMiningSpeedChanger(Item item) {
        return isMiningSpeedChanger(item) && TagUrushi.elementMiningSpeedChangeItemMap.get(item).metal() != 0;
    }

    public static boolean isWaterMiningSpeedChanger(Item item) {
        return isMiningSpeedChanger(item) && TagUrushi.elementMiningSpeedChangeItemMap.get(item).water() != 0;
    }

    // @debug, this does not work correctly now, mining speed keeps unchanged -- roseyasa 2024.4.14
    public static int getExtraMiningPercent(Item item, ElementType elementType) {
        if (item == null) {
            return 0;
        }
        TagUrushi.ElementMiningSpeedModifier modifier = TagUrushi.elementMiningSpeedChangeItemMap.get(item);
        if (modifier == null) {
            return 0;
        }
        switch (elementType) {
            case WoodElement -> {
                return modifier.wood();
            }
            case FireElement -> {
                return modifier.fire();
            }
            case EarthElement -> {
                return modifier.earth();
            }
            case MetalElement -> {
                return modifier.metal();
            }
            case WaterElement -> {
                return modifier.water();
            }
        }
        return 0;
    }

    public static boolean isWoodElement(BlockState state) {
        Block block = state.getBlock();

        if (block.defaultDestroyTime() < 0) {
            return false;
        }
        if (state.is(TagUrushi.DISABLE_WOOD_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_WOOD_ELEMENT)) {
            return true;
        }

        if (block instanceof CampfireBlock ||
                block instanceof TorchBlock ||
                block instanceof DiodeBlock
        ) {
            return false;
        }

        if (block instanceof PistonHeadBlock ||
                block instanceof LeavesBlock ||
                block instanceof SaplingBlock
        ) {
            return true;
        }

        Set<SoundType> woodSoundTypeSet = Set.of(
                SoundType.WOOD, SoundType.LADDER, SoundType.BAMBOO,
                SoundType.BAMBOO_SAPLING, SoundType.SCAFFOLDING,
                SoundType.AZALEA_LEAVES, SoundType.AZALEA,
                SoundType.FLOWERING_AZALEA, SoundType.STEM
        );
        return woodSoundTypeSet.contains(state.getSoundType());
    }

    public static boolean isFireElement(BlockState state) {
        Block block = state.getBlock();

        if (block.defaultDestroyTime() < 0) {
            return false;
        }
        if (state.is(TagUrushi.DISABLE_FIRE_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_FIRE_ELEMENT)) {
            return true;
        }


        if( block instanceof TntBlock ||
                block instanceof AbstractFurnaceBlock ||
                block instanceof CampfireBlock ||
                block instanceof TorchBlock
        ) {
            return true;
        }

        Set<SoundType> fireSoundTypeSet = Set.of(
                SoundType.NYLIUM, SoundType.FUNGUS, SoundType.SHROOMLIGHT,
                SoundType.WART_BLOCK, SoundType.NETHERRACK, SoundType.NETHER_BRICKS,
                SoundType.CANDLE, SoundType.NETHER_WART, SoundType.WOOL
        );
        return fireSoundTypeSet.contains(state.getSoundType());
    }

    public static boolean isEarthElement(BlockState state) {
        Block block = state.getBlock();

        if (block.defaultDestroyTime() < 0) {
            return false;
        }
        if (state.is(TagUrushi.DISABLE_EARTH_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_EARTH_ELEMENT)) {
            return true;
        }
        if (state.getMaterial() == Material.AIR) {
            return false;
        }

        Set<Material> nonEarthMaterialsSet = Set.of(Material.AIR, Material.PLANT, Material.REPLACEABLE_PLANT);
        if (nonEarthMaterialsSet.contains(state.getMaterial())) {
            return false;
        }

        Set<SoundType> earthSoundTypeSet = Set.of(
                SoundType.GRAVEL, SoundType.GRASS, SoundType.STONE, SoundType.SAND,
                SoundType.SWEET_BERRY_BUSH, SoundType.CROP, SoundType.HARD_CROP, SoundType.VINE,
                SoundType.ROOTS, SoundType.CALCITE, SoundType.DRIPSTONE_BLOCK, SoundType.POINTED_DRIPSTONE,
                SoundType.CAVE_VINES, SoundType.SPORE_BLOSSOM, SoundType.MOSS, SoundType.MOSS_CARPET,
                SoundType.BIG_DRIPLEAF, SoundType.SMALL_DRIPLEAF, SoundType.ROOTED_DIRT, SoundType.HANGING_ROOTS,
                SoundType.GLOW_LICHEN, SoundType.DEEPSLATE, SoundType.DEEPSLATE_BRICKS, SoundType.DEEPSLATE_TILES,
                SoundType.POLISHED_DEEPSLATE, SoundType.BASALT, SoundType.SOUL_SAND, SoundType.SOUL_SOIL,
                SoundType.TUFF, SoundType.LODESTONE, SoundType.NETHER_SPROUTS, SoundType.WEEPING_VINES,
                SoundType.TWISTING_VINES
        );
        return earthSoundTypeSet.contains(state.getSoundType());
    }

    public static boolean isMetalElement(BlockState state) {
        Block block = state.getBlock();

        if (block.defaultDestroyTime() < 0) {
            return false;
        }
        if (state.is(TagUrushi.DISABLE_METAL_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_METAL_ELEMENT)) {
            return true;
        }

        Set<SoundType> metalSoundTypeSet = Set.of(
                SoundType.METAL, SoundType.ANVIL, SoundType.LANTERN, SoundType.NETHER_ORE,
                SoundType.NETHER_GOLD_ORE, SoundType.CHAIN, SoundType.AMETHYST, SoundType.AMETHYST_CLUSTER,
                SoundType.LARGE_AMETHYST_BUD, SoundType.MEDIUM_AMETHYST_BUD, SoundType.SMALL_AMETHYST_BUD,
                SoundType.COPPER, SoundType.BONE_BLOCK, SoundType.GILDED_BLACKSTONE
        );
        return metalSoundTypeSet.contains(state.getSoundType());
    }

    public static boolean isWaterElement(BlockState state) {
        Block block = state.getBlock();

        if (block.defaultDestroyTime() < 0) {
            return false;
        }
        if (state.is(TagUrushi.DISABLE_WATER_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_WATER_ELEMENT)) {
            return true;
        }

        if (block instanceof SpongeBlock || block instanceof WetSpongeBlock) {
            return true;
        }

        Set<SoundType> waterSoundTypeSet = Set.of(
                SoundType.LILY_PAD, SoundType.SNOW, SoundType.POWDER_SNOW, SoundType.SLIME_BLOCK,
                SoundType.HONEY_BLOCK, SoundType.WET_GRASS, SoundType.GLASS, SoundType.CORAL_BLOCK
        );
        return waterSoundTypeSet.contains(state.getSoundType());
    }

    public static boolean isSpecificElement(ElementType type, BlockState state) {
        switch (type) {
            case WoodElement -> {
                return ElementUtils.isWoodElement(state);
            }
            case FireElement -> {
                return ElementUtils.isFireElement(state);
            }
            case EarthElement -> {
                return ElementUtils.isEarthElement(state);
            }
            case MetalElement -> {
                return ElementUtils.isMetalElement(state);
            }
            case WaterElement -> {
                return ElementUtils.isWaterElement(state);
            }
            default -> {
                return false;
            }
        }
    }

    public static float countMiningPercentByInventory(Player player, ElementType type) {
        float extra = 100;
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack stack = player.getInventory().getItem(i);

            extra += ElementUtils.getExtraMiningPercent(stack.getItem(), type) * stack.getCount();
        }
        return extra / 100;
    }

    public static void setBreakSpeedInfo(List<Component> list, int i, ElementType type) {
        String s = "";
        ChatFormatting chatFormatting = ChatFormatting.GRAY;
        if(i < 0){
            list.add((Component.translatable("info.urushi.blank").append(i + "% ").append(Component.translatable(s))).withStyle(chatFormatting));
            return;
        }
        switch (type) {
            case WoodElement:
                s = "info.urushi.wood_element_of_block";
                chatFormatting = ChatFormatting.DARK_GREEN;
                break;
            case FireElement:
                s = "info.urushi.fire_element_of_block";
                chatFormatting = ChatFormatting.DARK_RED;
                break;
            case EarthElement:
                s = "info.urushi.earth_element_of_block";
                chatFormatting = ChatFormatting.GOLD;
                break;
            case MetalElement:
                s = "info.urushi.metal_element_of_block";
                // chatFormatting = ChatFormatting.GRAY;
                break;
            case WaterElement:
                s = "info.urushi.water_element_of_block";
                chatFormatting = ChatFormatting.DARK_PURPLE;
                break;
            default:
                //break;
                // throw exception?
                return; // as no element matches, just return
        }
        
        list.add((Component.translatable("info.urushi.blank").append("+" + i + "% ").append(Component.translatable(s))).withStyle(chatFormatting));
    }

    public static boolean isWoodElementMob(LivingEntity entity) {
        return entity instanceof Chicken;
    }

    public static boolean isFireElementMob(LivingEntity entity) {
        return entity instanceof Sheep;
    }

    public static boolean isEarthElementMob(LivingEntity entity) {
        return entity instanceof Cow;
    }

    public static boolean isMetalElementMob(LivingEntity entity) {
        return entity instanceof Horse;
    }

    public static boolean isWaterElementMob(LivingEntity entity) {
        return entity instanceof Pig;
    }

    public static boolean isElementMob(LivingEntity entity) {
        return isWoodElementMob(entity) || isFireElementMob(entity) || isEarthElementMob(entity) || isMetalElementMob(entity) || isWaterElementMob(entity);
    }

    public static final String REIRYOKU_AMOUNT = "stored_reiryoku";

    /**
     * 霊力の最大貯蔵可能量を返す
     **/
    public static int getReiryokuCapacity(ItemStack stack) {
        if (stack.getItem() instanceof HasReiryokuItem) {
            return ((HasReiryokuItem) stack.getItem()).getReiryokuCapacity();
        }
        return 0;
    }

    /**
     * 現在の霊力貯蔵量を返す
     **/
    public static int getStoredReiryokuAmount(ItemStack stack) {
        if (getReiryokuCapacity(stack) <= 0) {
            return 0;
        }
        CompoundTag compoundtag = stack.getTag();
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
            return 0;
        }
        return compoundtag.getInt(REIRYOKU_AMOUNT);

    }

    /**
     * 霊力貯蔵量を特定の量に変更する
     **/
    public static void setStoredReiryokuAmount(ItemStack stack, int i) {
        if (0 <= i && i <= getReiryokuCapacity(stack)) {
            CompoundTag compoundtag = stack.getTag();
            if (compoundtag == null) {
                stack.setTag(new CompoundTag());
            }
            stack.getTag().putInt(REIRYOKU_AMOUNT, i);
        }
    }

    /**
     * 霊力を増減させるが、結果が0以下や最大容量以上になる場合を考慮していない。
     **/
    public static void increaseStoredReiryokuAmount(ItemStack stack, int i) {
        CompoundTag compoundtag = stack.getTag();
        int pre = 0;
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
        } else {
            pre = stack.getTag().getInt(REIRYOKU_AMOUNT);
        }
        if (0 <= pre + i && pre + i <= getReiryokuCapacity(stack)) {
            stack.getTag().putInt(REIRYOKU_AMOUNT, pre + i);
        } else if (pre + i > getReiryokuCapacity(stack)) {
            stack.getTag().putInt(REIRYOKU_AMOUNT, getReiryokuCapacity(stack));
        } else {
            stack.getTag().putInt(REIRYOKU_AMOUNT, 0);
        }
    }

    /**
     * 霊力を増減させたとき、計算結果が定義域に含まれているかどうか
     **/
    public static boolean willBeInDomain(ItemStack stack, int i) {
        CompoundTag compoundtag = stack.getTag();
        int pre = 0;
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
        } else {
            pre = stack.getTag().getInt(REIRYOKU_AMOUNT);
        }
        return 0 <= pre + i && pre + i <= getReiryokuCapacity(stack);
    }

    /**
     * 霊力を増減させたとき、定義域からはみ出た端数を返す
     **/
    public static int getExtraReiryokuAmount(ItemStack stack, int i) {
        CompoundTag compoundtag = stack.getTag();
        int pre = 0;
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
        } else {
            pre = stack.getTag().getInt(REIRYOKU_AMOUNT);
        }

        if (0 <= pre + i && pre + i <= getReiryokuCapacity(stack)) {
            //stack.getTag().putInt(REIRYOKU_AMOUNT, pre + i);
            return 0;
        } else if (pre + i > getReiryokuCapacity(stack)) {
            /**霊力を増やしすぎて計算結果が最大容量を超えたとき、入りきらなかった量を正の値で返す。つまり、貯蔵量4990、最大貯蔵量5000にの霊力を30増やそうとすると20が返ってくる。**/
            //  stack.getTag().putInt(REIRYOKU_AMOUNT, getReiryokuCapacity(stack));
            return pre + i - getReiryokuCapacity(stack);
        } else {
            /**霊力を減らしすぎて計算結果が0になったとき、引ききれなかった量を負の値で返す。つまり、貯蔵量10の霊力を90減らそうとすると-80が返ってくる。**/
            //   stack.getTag().putInt(REIRYOKU_AMOUNT, 0);
            return -(i - pre);
        }
    }

    /**
     * インベントリ内の勾玉を探す
     */
    public static ItemStack getMagatamaInInventory(Player player, ElementType elementType) {
        ItemStack result = ItemStack.EMPTY;
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (itemstack.getItem() instanceof AbstractMagatamaItem magatamaItem) {
                if (magatamaItem.getElementType() == elementType) {
                    result = itemstack;
                    break;
                }
            }
        }
        return result;
    }


    public static boolean isSoukokuBlock(LevelAccessor level, BlockPos pos, BlockState currentState) {
        BlockState neighborState = level.getBlockState(pos);
        if (currentState.getBlock() == Blocks.CYAN_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.YELLOW_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.RED_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.WHITE_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.YELLOW_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.PURPLE_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.WHITE_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.CYAN_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.PURPLE_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.RED_CONCRETE_POWDER;
        }
        return false;

    }

    public static BlockState getSojoBlock(BlockState currentState) {
        if (currentState == null) {
            return null;
        }
        if (currentState.getBlock() == Blocks.RED_CONCRETE_POWDER) { //C
            return Blocks.CYAN_CONCRETE_POWDER.defaultBlockState(); //R
        } else if (currentState.getBlock() == Blocks.YELLOW_CONCRETE_POWDER) { //R
            return Blocks.RED_CONCRETE_POWDER.defaultBlockState(); //Y
        } else if (currentState.getBlock() == Blocks.WHITE_CONCRETE_POWDER) { //Y
            return Blocks.YELLOW_CONCRETE_POWDER.defaultBlockState(); //W
        } else if (currentState.getBlock() == Blocks.PURPLE_CONCRETE_POWDER) { //W
            return Blocks.WHITE_CONCRETE_POWDER.defaultBlockState(); //P
        } else if (currentState.getBlock() == Blocks.CYAN_CONCRETE_POWDER) { //P
            return Blocks.PURPLE_CONCRETE_POWDER.defaultBlockState(); //C
        }
        return null;

    }

    public static BlockState getRandomElementBlock(LevelAccessor level) {
        int random = level.getRandom().nextInt(5);
        if (random == 0) {
            return Blocks.CYAN_CONCRETE_POWDER.defaultBlockState();
        } else if (random == 1) {
            return Blocks.RED_CONCRETE_POWDER.defaultBlockState();
        } else if (random == 2) {
            return Blocks.YELLOW_CONCRETE_POWDER.defaultBlockState();
        } else if (random == 3) {
            return Blocks.WHITE_CONCRETE_POWDER.defaultBlockState();
        } else {
            return Blocks.PURPLE_CONCRETE_POWDER.defaultBlockState();
        }
    }

    public static ItemStack getOverflowStack(ElementType type) {
        ItemStack overflowItem;
        switch (type) {
            case WoodElement -> overflowItem = new ItemStack(ItemAndBlockRegister.wood_amber.get());
            case FireElement -> overflowItem = new ItemStack(ItemAndBlockRegister.fire_amber.get());
            case EarthElement -> overflowItem = new ItemStack(ItemAndBlockRegister.earth_amber.get());
            case MetalElement -> overflowItem = new ItemStack(ItemAndBlockRegister.metal_amber.get());
            case WaterElement -> overflowItem = new ItemStack(ItemAndBlockRegister.water_amber.get());
            default -> overflowItem = ItemStack.EMPTY;
        }
        return overflowItem;
    }

    public static ParticleOptions getMediumElementParticle(ElementType type) {
        ParticleOptions particleOptions;
        switch (type) {
            case WoodElement -> particleOptions = ParticleRegister.WoodElementMedium.get();
            case FireElement -> particleOptions = ParticleRegister.FireElementMedium.get();
            case EarthElement -> particleOptions = ParticleRegister.EarthElementMedium.get();
            case MetalElement -> particleOptions = ParticleRegister.MetalElementMedium.get();
            // case WaterElement ->
            default -> particleOptions = ParticleRegister.WaterElementMedium.get();
        }
        return particleOptions;

    }

    public static Component getStoredReiryokuDisplayMessage(int current, int max, ElementType elementType) {
        ChatFormatting color = ChatFormatting.RESET;
        String string = "error";
        switch (elementType) {
            case WoodElement -> {
                color = ChatFormatting.GREEN;
                string = "info.urushi.stored_wood_reiryoku.message";
            }
            case FireElement -> {
                color = ChatFormatting.RED;
                string = "info.urushi.stored_fire_reiryoku.message";
            }
            case EarthElement -> {
                color = ChatFormatting.GOLD;
                string = "info.urushi.stored_earth_reiryoku.message";
            }
            case MetalElement -> {
                color = ChatFormatting.WHITE;
                string = "info.urushi.stored_metal_reiryoku.message";
            }
            // case WaterElement -> {
            default -> {
                color = ChatFormatting.LIGHT_PURPLE;
                string = "info.urushi.stored_water_reiryoku.message";
            }
        }
        return Component.translatable(string).append(" " + current + " / " + max).withStyle(color);
    }
}
