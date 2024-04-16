package com.iwaliner.urushi.blockentity;


import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.blockentity.menu.AutoCraftingTableMenu;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class AutoCraftingTableBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, RecipeHolder, MenuProvider {
   /* private NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    private static final int[] INGREDIENT_SLOTS = new int[]{1,2,3,4,5,6,7,8,9};
    private static final int[] RESULT_SLOT = new int[]{0};
 protected final RecipeType<? extends CraftingRecipe> recipeType;
    private int processTime;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public AutoCraftingTableBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.AutoCraftingTable.get(), p_155052_, p_155053_);
        this.recipeType = RecipeType.CRAFTING;
    }

    public void load(CompoundTag p_155025_) {
        super.load(p_155025_);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
       ContainerHelper.loadAllItems(p_155025_, this.items);
        this.processTime = p_155025_.getInt("processTime");
        CompoundTag compoundtag = p_155025_.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }

    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
        p_187452_.putInt("processTime", this.processTime);
        ContainerHelper.saveAllItems(p_187452_, this.items);

        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        p_187452_.put("RecipesUsed", compoundtag);
    }
    private static CraftingContainer makeContainer(NonNullList<ItemStack> list) {
        CraftingContainer craftingcontainer = new CraftingContainer(new AbstractContainerMenu((MenuType) MenuRegister.AutoCraftingTableMenu.get(), -1) {
            public ItemStack quickMoveStack(Player p_218264_, int p_218265_) {
                return ItemStack.EMPTY;
            }

            public boolean stillValid(Player p_29888_) {
                return false;
            }
        }, 3, 3);
        for (int i = 0; i < 9; i++) {
            craftingcontainer.setItem(i,list.get(i+1));

        }

        return craftingcontainer;
    }
    public static void tick(Level level, BlockPos pos, BlockState state, AutoCraftingTableBlockEntity blockEntity) {


        if (!level.isClientSide) {
            CraftingContainer craftingcontainer = makeContainer(blockEntity.items);
            Optional<CraftingRecipe> optional = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingcontainer, level);

           ItemStack itemstack = ItemStack.EMPTY;
            if (optional.isPresent()) {
                CraftingRecipe craftingrecipe = optional.get();
              //  if (blockEntity.setRecipeUsed(level, blockEntity, craftingrecipe)) {
                    itemstack = craftingrecipe.assemble(craftingcontainer);
             //   }
            }
                if(!itemstack.isEmpty()&& blockEntity.getItem(0).sameItem(itemstack) && ItemStack.tagMatches(blockEntity.getItem(0), itemstack)) {

                boolean flag=false;
                for (int i = 1; i < 10; i++) {
                   if( blockEntity.getItem(i).getCount()==1){
                       flag=true;
                   }
                }
                    if(!flag&&blockEntity.getItem(0).getCount()+itemstack.getCount()<=itemstack.getItem().getMaxStackSize(itemstack)&&blockEntity.getItem(0).getCount()<=itemstack.getCount()*2) {
                    for (int i = 1; i < 10; i++) {
                        blockEntity.getItem(i).shrink(1);

                    }
                    ItemStack newStack=itemstack.copy();
                    newStack.setCount(itemstack.getCount()+blockEntity.getItem(0).getCount());
                    blockEntity.setItem(0, newStack);
                }
                blockEntity.setChanged();
            }else{
                blockEntity.setItem(0, itemstack);
                blockEntity.setChanged();
            }
         }
    }



    protected Component getDefaultName() {
        return Component.translatable("container.urushi_auto_crafting_table");
    }





    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new AutoCraftingTableMenu(i, inventory,this);
    }


    @Override
    public int getContainerSize() {
        return this.items.size();

    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    public ItemStack removeItemNoUpdate(int slot) {
        if(slot==0){
            for (int i = 1; i < 10; i++) {
                if( !this.getItem(i).isEmpty()){
                    this.getItem(i).shrink(1);
                }
            }
        }
       return ContainerHelper.takeItem(this.items, slot);
    }

    public ItemStack removeItem(int slot, int amount) {
        if(slot==0){
            for (int i = 1; i < 10; i++) {
                if( !this.getItem(i).isEmpty()){
                    this.getItem(i).shrink(1);
                }
            }
        }
        return ContainerHelper.removeItem(this.items, slot,amount);
    }

    public void setItem(int slot, ItemStack stack) {
        if(slot==0||(slot!=0&&this.getItem(slot).getCount()<2)) {
            ItemStack itemstack = this.items.get(slot);
            boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
            this.items.set(slot, stack);
            if (stack.getCount() > this.getMaxStackSize()) {
                stack.setCount(this.getMaxStackSize());
            }

            if (slot != 0 && !flag) {
                this.setChanged();
            }
        }
    }

    public void setChanged() {
    }

    public boolean stillValid(Player p_70300_1_) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_70300_1_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }
    public boolean canPlaceItem(int slot, ItemStack stack) {
       return slot!=0&&!this.getItem(slot).isEmpty()&&this.getItem(slot).getCount()<2;
    }


    public void clearContent() {
        this.items.clear();
    }



    public void setRecipeUsed(@Nullable Recipe<?> p_193056_1_) {
        if (p_193056_1_ != null) {
            ResourceLocation resourcelocation = p_193056_1_.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }
    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipes(Player p_201560_1_) {
    }
    public void fillStackedContents(StackedContents p_194018_1_) {
        for(ItemStack itemstack : this.items) {
            p_194018_1_.accountStack(itemstack);
        }

    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            //if (facing == Direction.DOWN)
                return handlers[0].cast();

        }
        return handlers[0].cast();
       // return super.getCapability(capability, facing);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }


    @Override
    public int @NotNull [] getSlotsForFace(Direction direction) {
        return direction==Direction.DOWN? RESULT_SLOT : INGREDIENT_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @org.jetbrains.annotations.Nullable Direction direction) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        boolean flag=false;
        for (int i = 1; i < 10; i++) {
            if( this.getItem(i).getCount()==1){
               flag=true;
            }
        }
        return slot==0&&!flag;

    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }









*/





   /* private static final int[] INGREDIENT_SLOTS = new int[]{1,2,3,4,5,6,7,8,9};
    private static final int[] RESULT_SLOT = new int[]{0};
       private final ItemStackHandler ingredients = new ItemStackHandler(9) {
           @Override
           protected void onContentsChanged(int slot) {
               super.onContentsChanged(slot);
               setChanged();
           }
       };
       private final ItemStackHandler result = new ItemStackHandler(1) {
           @Override
           protected void onContentsChanged(int slot) {
               super.onContentsChanged(slot);
               setChanged();
           }

       };
       private LazyOptional<ItemStackHandler> resultOptional = LazyOptional.empty();

       private LazyOptional<ItemStackHandler> ingredientsOptional = LazyOptional.empty();
       private final NonNullList<ItemStack> slotList = NonNullList.of(result.getStackInSlot(0), ingredients.getStackInSlot(0),ingredients.getStackInSlot(1),ingredients.getStackInSlot(2),ingredients.getStackInSlot(3),ingredients.getStackInSlot(4),ingredients.getStackInSlot(5),ingredients.getStackInSlot(6),ingredients.getStackInSlot(7),ingredients.getStackInSlot(8));
       protected final RecipeType<? extends CraftingRecipe> recipeType;
    private int processTime;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public AutoCraftingTableBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.AutoCraftingTable.get(), p_155052_, p_155053_);
        this.recipeType = RecipeType.CRAFTING;
    }
    @Override
    public void onLoad() {
        super.onLoad();
        resultOptional = LazyOptional.of(() -> result);
        ingredientsOptional = LazyOptional.of(() -> ingredients);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag data = tag.getCompound(ModCoreUrushi.ModID);
        if(data.contains("ingredients", Tag.TAG_COMPOUND)) {
            this.ingredients.deserializeNBT(data.getCompound("ingredients"));
        }
        if(data.contains("result", Tag.TAG_COMPOUND)) {
            this.result.deserializeNBT(data.getCompound("result"));
        }
         this.processTime = tag.getInt("processTime");
        CompoundTag compoundtag = tag.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }

    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
         var data=new CompoundTag();
         data.put("ingredients",this.ingredients.serializeNBT());
         data.put("result",this.result.serializeNBT());
         p_187452_.put(ModCoreUrushi.ModID,data);


        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        p_187452_.put("RecipesUsed", compoundtag);
    }
    private static CraftingContainer makeContainer(NonNullList<ItemStack> list) {
        CraftingContainer craftingcontainer = new CraftingContainer(new AbstractContainerMenu((MenuType) MenuRegister.AutoCraftingTableMenu.get(), -1) {
            public ItemStack quickMoveStack(Player p_218264_, int p_218265_) {
                return ItemStack.EMPTY;
            }

            public boolean stillValid(Player p_29888_) {
                return false;
            }
        }, 3, 3);
        for (int i = 0; i < 9; i++) {
            craftingcontainer.setItem(i,list.get(i+1));

        }

        return craftingcontainer;
    }
    public static void tick(Level level, BlockPos pos, BlockState state, AutoCraftingTableBlockEntity blockEntity) {


        if (!level.isClientSide) {
             CraftingContainer craftingcontainer = new CraftingContainer(new AbstractContainerMenu((MenuType) MenuRegister.AutoCraftingTableMenu.get(), -1) {
                public ItemStack quickMoveStack(Player p_218264_, int p_218265_) {
                    return ItemStack.EMPTY;
                }

                public boolean stillValid(Player p_29888_) {
                    return false;
                }
            }, 3, 3);
            for (int i = 0; i < 9; i++) {
                craftingcontainer.setItem(i,blockEntity.ingredients.getStackInSlot(i));

            }
            Optional<CraftingRecipe> optional = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingcontainer, level);

             ItemStack itemstack = ItemStack.EMPTY;
            if (optional.isPresent()) {
                CraftingRecipe craftingrecipe = optional.get();
                itemstack = craftingrecipe.assemble(craftingcontainer);

            }
            if(!itemstack.isEmpty()&& blockEntity.getItem(0).sameItem(itemstack) && ItemStack.tagMatches(blockEntity.getItem(0), itemstack)) {

                boolean flag=false;
                for (int i = 1; i < 10; i++) {
                    if( blockEntity.getItem(i).getCount()==1){
                        flag=true;
                    }
                }
                if(!flag&&blockEntity.getItem(0).getCount()+itemstack.getCount()<=itemstack.getItem().getMaxStackSize(itemstack)) {
                    for (int i = 1; i < 10; i++) {
                        blockEntity.getItem(i).shrink(1);

                    }
                    ItemStack newStack=itemstack.copy();
                    newStack.setCount(itemstack.getCount()+blockEntity.getItem(0).getCount());
                    blockEntity.setItem(0, newStack);
                }
                blockEntity.setChanged();
            }else{
                blockEntity.setItem(0, itemstack);
                blockEntity.setChanged();
            }
        }
    }



    protected Component getDefaultName() {
        return Component.translatable("container.urushi_auto_crafting_table");
    }





    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new AutoCraftingTableMenu(i, inventory,this);
    }


    @Override
    public int getContainerSize() {
        return 10;

    }

    @Override
    public boolean isEmpty() {
        for(int i=0;i<9;i++) {
            if (!this.ingredients.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return this.result.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem(int slot) {
        return slot==0? this.result.getStackInSlot(0) : this.ingredients.getStackInSlot(slot-1);
    }

    public ItemStack removeItemNoUpdate(int slot) {
        if(slot==0){
            for (int i = 1; i < 10; i++) {
                if( !this.getItem(i).isEmpty()){
                    this.getItem(i).shrink(1);
                }
            }
        }
        return ContainerHelper.takeItem(this.slotList, slot);
    }

    public ItemStack removeItem(int slot, int amount) {
        if(slot==0){
            for (int i = 1; i < 10; i++) {
                if( !this.getItem(i).isEmpty()){
                    this.getItem(i).shrink(1);
                }
            }
        }
        return ContainerHelper.removeItem(this.slotList, slot,amount);
    }

    public void setItem(int slot, ItemStack stack) {
        if(slot==0||(slot!=0&&this.getItem(slot).getCount()<2)) {
            ItemStack itemstack = slot==0? this.result.getStackInSlot(0) : this.ingredients.getStackInSlot(slot-1);
            boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
            if(slot==0){
                this.result.setStackInSlot(0,stack);
            }else{
                this.ingredients.setStackInSlot(slot-1,stack);

            }
            if (stack.getCount() > this.getMaxStackSize()) {
                stack.setCount(this.getMaxStackSize());
            }

            if (slot != 0 && !flag) {
                this.setChanged();
            }
        }
    }

    public void setChanged() {
    }

    public boolean stillValid(Player p_70300_1_) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_70300_1_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot!=0&&!this.getItem(slot).isEmpty()&&this.getItem(slot).getCount()<2;
    }


    public void clearContent() {

    }



    public void setRecipeUsed(@Nullable Recipe<?> p_193056_1_) {
        if (p_193056_1_ != null) {
            ResourceLocation resourcelocation = p_193056_1_.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }
    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipes(Player p_201560_1_) {
    }
    public void fillStackedContents(StackedContents p_194018_1_) {
        for(int i=0;i<9;i++) {
            p_194018_1_.accountStack(this.ingredients.getStackInSlot(i));
        }
        p_194018_1_.accountStack(this.result.getStackInSlot(0));
    }


    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if(capability == ForgeCapabilities.ITEM_HANDLER) {
            if(facing == Direction.DOWN)
                return this.resultOptional.cast();


            return this.ingredientsOptional.cast();
        }

        return super.getCapability(capability, facing);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.ingredientsOptional.invalidate();
        this.resultOptional.invalidate();
    }




    @Override
    public int @NotNull [] getSlotsForFace(Direction direction) {
        return direction==Direction.DOWN? RESULT_SLOT : INGREDIENT_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @org.jetbrains.annotations.Nullable Direction direction) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        boolean flag=false;
        for (int i = 1; i < 10; i++) {
            if( this.getItem(i).getCount()==1){
                flag=true;
            }
        }
        return slot==0&&!flag;

    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }*/








    private static final int[] INGREDIENT_SLOTS = new int[]{1,2,3,4,5,6,7,8,9};
    private static final int[] RESULT_SLOT = new int[]{0};
    private final ItemStackHandler ingredients = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };
    private final ItemStackHandler result = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }

    };
    private final ItemStackHandler ingredientsSample = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };
    private final ItemStackHandler resultSample = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }

    };
    private LazyOptional<ItemStackHandler> resultOptional = LazyOptional.empty();

    private LazyOptional<ItemStackHandler> ingredientsOptional = LazyOptional.empty();
    private LazyOptional<ItemStackHandler> resultSampleOptional = LazyOptional.empty();

    private LazyOptional<ItemStackHandler> ingredientsSampleOptional = LazyOptional.empty();
    private final NonNullList<ItemStack> slotList = NonNullList.of(result.getStackInSlot(0), ingredients.getStackInSlot(0),ingredients.getStackInSlot(1),ingredients.getStackInSlot(2),ingredients.getStackInSlot(3),ingredients.getStackInSlot(4),ingredients.getStackInSlot(5),ingredients.getStackInSlot(6),ingredients.getStackInSlot(7),ingredients.getStackInSlot(8),resultSample.getStackInSlot(0), ingredientsSample.getStackInSlot(0),ingredientsSample.getStackInSlot(1),ingredientsSample.getStackInSlot(2),ingredientsSample.getStackInSlot(3),ingredientsSample.getStackInSlot(4),ingredientsSample.getStackInSlot(5),ingredientsSample.getStackInSlot(6),ingredientsSample.getStackInSlot(7),ingredientsSample.getStackInSlot(8));
    protected final RecipeType<? extends CraftingRecipe> recipeType;
    private int processTime;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public AutoCraftingTableBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.AutoCraftingTable.get(), p_155052_, p_155053_);
        this.recipeType = RecipeType.CRAFTING;
    }
    @Override
    public void onLoad() {
        super.onLoad();
        resultOptional = LazyOptional.of(() -> result);
        ingredientsOptional = LazyOptional.of(() -> ingredients);
        resultSampleOptional = LazyOptional.of(() -> resultSample);
        ingredientsSampleOptional = LazyOptional.of(() -> ingredientsSample);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag data = tag.getCompound(ModCoreUrushi.ModID);
        if(data.contains("ingredients", Tag.TAG_COMPOUND)) {
            this.ingredients.deserializeNBT(data.getCompound("ingredients"));
        }
        if(data.contains("result", Tag.TAG_COMPOUND)) {
            this.result.deserializeNBT(data.getCompound("result"));
        }
        if(data.contains("ingredientsSample", Tag.TAG_COMPOUND)) {
            this.ingredientsSample.deserializeNBT(data.getCompound("ingredientsSample"));
        }
        if(data.contains("resultSample", Tag.TAG_COMPOUND)) {
            this.resultSample.deserializeNBT(data.getCompound("resultSample"));
        }
        this.processTime = tag.getInt("processTime");
        CompoundTag compoundtag = tag.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }

    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
        var data=new CompoundTag();
        data.put("ingredients",this.ingredients.serializeNBT());
        data.put("result",this.result.serializeNBT());
        data.put("ingredientsSample",this.ingredientsSample.serializeNBT());
        data.put("resultSample",this.resultSample.serializeNBT());
        p_187452_.put(ModCoreUrushi.ModID,data);


        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        p_187452_.put("RecipesUsed", compoundtag);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, AutoCraftingTableBlockEntity blockEntity) {


        if (!level.isClientSide) {
            CraftingContainer craftingcontainer = new CraftingContainer(new AbstractContainerMenu((MenuType) MenuRegister.AutoCraftingTableMenu.get(), -1) {
                public ItemStack quickMoveStack(Player p_218264_, int p_218265_) {
                    return ItemStack.EMPTY;
                }

                public boolean stillValid(Player p_29888_) {
                    return false;
                }
            }, 3, 3);
       /*     CraftingContainer craftingcontainerSample = new CraftingContainer(new AbstractContainerMenu((MenuType) MenuRegister.AutoCraftingTableMenu.get(), -1) {
                public ItemStack quickMoveStack(Player p_218264_, int p_218265_) {
                    return ItemStack.EMPTY;
                }

                public boolean stillValid(Player p_29888_) {
                    return false;
                }
            }, 3, 3);*/
            for (int i = 0; i < 9; i++) {
                craftingcontainer.setItem(i, blockEntity.ingredients.getStackInSlot(i));
           //     craftingcontainerSample.setItem(i, blockEntity.ingredientsSample.getStackInSlot(i));
            }
            Optional<CraftingRecipe> optional = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingcontainer, level);
          //  Optional<CraftingRecipe> optionalSample = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingcontainerSample, level);

            ItemStack itemstack = ItemStack.EMPTY;
           // ItemStack itemstackSample = ItemStack.EMPTY;
            if (optional.isPresent()) {
                CraftingRecipe craftingrecipe = optional.get();
                itemstack = craftingrecipe.assemble(craftingcontainer);

            }
           /* if (optionalSample.isPresent()) {
                CraftingRecipe craftingrecipe = optionalSample.get();
                itemstackSample = craftingrecipe.assemble(craftingcontainerSample);

            }*/
          //  if(itemstack.sameItem(itemstackSample)&&ItemStack.tagMatches(itemstack, itemstackSample)){
            if (!itemstack.isEmpty() && blockEntity.getItem(0).sameItem(itemstack) && ItemStack.tagMatches(blockEntity.getItem(0), itemstack)) {


                if (blockEntity.getItem(0).getCount() + itemstack.getCount() <= itemstack.getItem().getMaxStackSize(itemstack)) {
                    for (int i = 1; i < 10; i++) {
                        blockEntity.getItem(i).shrink(1);

                    }
                    ItemStack newStack = itemstack.copy();
                    newStack.setCount(itemstack.getCount() + blockEntity.getItem(0).getCount());
                    blockEntity.setItem(0, newStack);
                }
                blockEntity.setChanged();
            } else if (!itemstack.isEmpty() && blockEntity.getItem(0).isEmpty()) {
                for (int i = 1; i < 10; i++) {
                    blockEntity.getItem(i).shrink(1);

                }
                blockEntity.setItem(0, itemstack);
                blockEntity.setChanged();
            }
       // }
        }
    }



    protected Component getDefaultName() {
        return Component.translatable("container.urushi_auto_crafting_table");
    }





    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new AutoCraftingTableMenu(i, inventory,this);
    }


    @Override
    public int getContainerSize() {
        return 20;

    }

    @Override
    public boolean isEmpty() {
        for(int i=0;i<9;i++) {
            if (!this.ingredients.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return this.result.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem(int slot) {
        if(slot==0){
            return this.result.getStackInSlot(0);
        }else if(slot<10){
            return this.ingredients.getStackInSlot(slot-1);
        }else if(slot==10){
            return this.resultSample.getStackInSlot(0);
        }else{
            return this.ingredientsSample.getStackInSlot(slot-11);
        }

    }

    public ItemStack removeItemNoUpdate(int slot) {

        return ContainerHelper.takeItem(this.slotList, slot);
    }

    public ItemStack removeItem(int slot, int amount) {

        return ContainerHelper.removeItem(this.slotList, slot,amount);
    }

    public void setItem(int slot, ItemStack stack) {

            ItemStack itemstack = this.getItem(slot);
            boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
            if(slot==0){
                this.result.setStackInSlot(0,stack);
            }else if(slot<10){
                this.ingredients.setStackInSlot(slot-1,stack);
            }else if(slot==10){
                this.resultSample.setStackInSlot(0,stack);
            }else{
                this.ingredientsSample.setStackInSlot(slot-11,stack);
            }
            if (stack.getCount() > this.getMaxStackSize()) {
                stack.setCount(this.getMaxStackSize());
            }

            if (slot != 0 && !flag) {
                this.setChanged();
            }
      //  }
    }

    public void setChanged() {
    }

    public boolean stillValid(Player p_70300_1_) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_70300_1_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot!=0;
    }


    public void clearContent() {

    }



    public void setRecipeUsed(@Nullable Recipe<?> p_193056_1_) {
        if (p_193056_1_ != null) {
            ResourceLocation resourcelocation = p_193056_1_.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }

    }
    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipes(Player p_201560_1_) {
    }
    public void fillStackedContents(StackedContents p_194018_1_) {
        for(int i=0;i<9;i++) {
            p_194018_1_.accountStack(this.ingredients.getStackInSlot(i));
        }
        p_194018_1_.accountStack(this.result.getStackInSlot(0));
        for(int i=0;i<9;i++) {
            p_194018_1_.accountStack(this.ingredientsSample.getStackInSlot(i));
        }
        p_194018_1_.accountStack(this.resultSample.getStackInSlot(0));
    }


    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if(capability == ForgeCapabilities.ITEM_HANDLER) {
            if(facing == Direction.DOWN)
                return this.resultOptional.cast();


            return this.ingredientsOptional.cast();
        }

        return super.getCapability(capability, facing);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.ingredientsOptional.invalidate();
        this.resultOptional.invalidate();
        this.ingredientsSampleOptional.invalidate();
        this.resultSampleOptional.invalidate();
    }




    @Override
    public int @NotNull [] getSlotsForFace(Direction direction) {
        return direction==Direction.DOWN? RESULT_SLOT : INGREDIENT_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @org.jetbrains.annotations.Nullable Direction direction) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {

        return  slot==0;

    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
