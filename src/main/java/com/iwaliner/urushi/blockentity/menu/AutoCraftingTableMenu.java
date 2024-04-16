package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.blockentity.slot.FryerFuelSlot;
import com.iwaliner.urushi.recipe.FryingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class AutoCraftingTableMenu extends RecipeBookMenu<Container> {

    private final Container container;
    private final Player player;

    public AutoCraftingTableMenu(int s, Inventory inventory) {
        this( s, inventory, new SimpleContainer(20));
    }

    public AutoCraftingTableMenu(int s, Inventory inventory,Container c) {
        super(MenuType.CRAFTING, s);
        checkContainerSize(c, 20);
        this.container=c;
        this.player = inventory.player;
        container.startOpen(player);
        this.addSlot(new Slot(container, 0, 146, 33));
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(this.container, i +1, 8 + i * 18, 72));
        }
        this.addSlot(new Slot(container, 10, 124, 35-6));
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.container, j + i * 3+11, 30 + j * 18, 17 + i * 18-6));
            }
        }

     /*   for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 103 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 161));
        }*/
    }

    public void fillCraftSlotsStackedContents(StackedContents p_38976_) {
        if (this.container instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible)this.container).fillStackedContents(p_38976_);
        }

    }

    public void clearCraftingContent() {
        this.container.clearContent();

    }

    public boolean recipeMatches(Recipe<? super Container> recipe) {
        CraftingContainer craftingcontainer = new CraftingContainer(this,3,3);
        for (int i = 0; i < 9; i++) {
            craftingcontainer.setItem(i, container.getItem(i+1));
        }
        return recipe.matches(craftingcontainer, this.player.level);
    }

    public int getResultSlotIndex() {
        return 0;
    }

    public int getGridWidth() {
        return 1;
    }

    public int getGridHeight() {
        return 1;
    }

    public int getSize() {
        return 20;
    }



    public boolean stillValid(Player p_38974_) {
        return this.container.stillValid(p_38974_);
    }

    public ItemStack quickMoveStack(Player player, int slotNumber) {

    /*    ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotNumber);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotNumber == 0) {
               // this.access.execute((p_39378_, p_39379_) -> {
                    itemstack1.getItem().onCraftedBy(itemstack1, player.level, player);
              //  });
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    container.setChanged();
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
                for (int i = 1; i < 10; i++) {
                    container.getItem(i).shrink(1);
                }
            } else if (slotNumber >= 10 && slotNumber < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (slotNumber < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            container.setChanged();
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        container.setChanged();
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                container.setChanged();
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            if (slotNumber == 0) {
                player.drop(itemstack1, false);
            }
        }
        container.setChanged();
        return itemstack;

     */
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotNumber);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotNumber < 20) {
                if (!this.moveItemStackTo(itemstack1, 20, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 20, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }



    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public boolean shouldMoveToInventory(int s) {
        return s != 0;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack stack, int slot1, int slot2, boolean b) {
        return super.moveItemStackTo(stack, slot1, slot2, b);
    }


}

