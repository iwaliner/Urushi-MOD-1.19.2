package com.iwaliner.urushi.recipe;

import com.google.gson.JsonObject;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.RecipeTypeRegister;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;

public class WoodElementTier2CraftingRecipe extends AbstractElementCraftingRecipe{
    public WoodElementTier2CraftingRecipe(NonNullList<Ingredient> input, ItemStack output, ResourceLocation location, int reiryoku) {
        super(input,output,location,reiryoku);
    }
    public RecipeType<?> getType() {
        return RecipeTypeRegister.WoodElementTier2CraftingRecipe;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegister.WoodElementTier2CraftingSerializer.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ItemAndBlockRegister.wood_element_crafting_table_tier2.get());
    }

    public static class WoodElementTier2CraftingRecipeType implements RecipeType<WoodElementTier2CraftingRecipe> {
        @Override
        public String toString() {
            return new ResourceLocation(ModCoreUrushi.ModID,"wood_element_tier2_crafting").toString();
        }
    }

    public static class WoodElementTier2CraftingSerializer <T extends WoodElementTier2CraftingRecipe>implements RecipeSerializer<WoodElementTier2CraftingRecipe> {


        @Override
        public WoodElementTier2CraftingRecipe fromJson(ResourceLocation location, JsonObject json) {
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));

                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                int i = GsonHelper.getAsInt(json, "reiryoku");
                return new WoodElementTier2CraftingRecipe(nonnulllist,itemstack,location,i);

        }

        @Nullable
        @Override
        public WoodElementTier2CraftingRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input=NonNullList.withSize(4,Ingredient.EMPTY);
            for(int j = 0; j < input.size(); ++j) {
                input.set(j, Ingredient.fromNetwork(buffer));
            }
            ItemStack output=buffer.readItem();
            int i = buffer.readVarInt();
            return new WoodElementTier2CraftingRecipe(input,output,location,i);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WoodElementTier2CraftingRecipe recipe) {
            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem());
          //  buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getReiryoku());

        }
    }
}
