package com.iwaliner.urushi.mixin;

import com.google.common.collect.ImmutableSet;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.SlideDoorBlock;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;

@Mixin(Villager.class)
public abstract class VillagerMixin {
@Shadow@Final
public static Map<Item, Integer> FOOD_POINTS;

    @Inject(method = "wantsToPickUp",at = @At("HEAD"), cancellable = true,remap = false)
    private void wantsToPickUpInject(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        FOOD_POINTS.put(ItemAndBlockRegister.roasted_rice_cake.get(),4);
        Item item = stack.getItem();
        Set<Item> WANTED_URUSHI_ITEMS = ImmutableSet.of(ItemAndBlockRegister.rice.get(),ItemAndBlockRegister.rice_cake.get(),ItemAndBlockRegister.roasted_rice_cake.get(),ItemAndBlockRegister.tofu.get());
          if(WANTED_URUSHI_ITEMS.contains(item)){
            cir.setReturnValue(true);
        }
    }




}
