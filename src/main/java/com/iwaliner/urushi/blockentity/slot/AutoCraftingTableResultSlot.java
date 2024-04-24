package com.iwaliner.urushi.blockentity.slot;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class AutoCraftingTableResultSlot extends ItemStackHandlerSlot {


    public AutoCraftingTableResultSlot(Container p_39521_, int p_39522_, int p_39523_, int p_39524_) {
        super(p_39521_, p_39522_, p_39523_, p_39524_);
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
       return false;
    }
}
