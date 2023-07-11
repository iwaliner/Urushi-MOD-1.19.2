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

public class WaterElementTier1CraftingRecipe extends AbstractElementCraftingRecipe{
    public WaterElementTier1CraftingRecipe(NonNullList<Ingredient> input, ItemStack output, ResourceLocation location, int reiryoku) {
        super(input,output,location,reiryoku);
    }
    public RecipeType<?> getType() {
        return RecipeTypeRegister.WaterElementTier1CraftingRecipe;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegister.WaterElementTier1CraftingSerializer.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ItemAndBlockRegister.water_element_crafting_table_tier1.get());
    }

    public static class WaterElementTier1CraftingRecipeType implements RecipeType<WaterElementTier1CraftingRecipe> {
        @Override
        public String toString() {
            return new ResourceLocation(ModCoreUrushi.ModID,"water_element_tier1_crafting").toString();
        }
    }

    public static class WaterElementTier1CraftingSerializer <T extends WaterElementTier1CraftingRecipe> implements RecipeSerializer<WaterElementTier1CraftingRecipe> {


        @Override
        public WaterElementTier1CraftingRecipe fromJson(ResourceLocation location, JsonObject json) {
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));

                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                int i = GsonHelper.getAsInt(json, "reiryoku");
                return new WaterElementTier1CraftingRecipe(nonnulllist,itemstack,location,i);

        }

        @Nullable
        @Override
        public WaterElementTier1CraftingRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input=NonNullList.withSize(4,Ingredient.EMPTY);
            for(int j = 0; j < input.size(); ++j) {
                input.set(j, Ingredient.fromNetwork(buffer));
            }
            ItemStack output=buffer.readItem();
            int i = buffer.readVarInt();
            return new WaterElementTier1CraftingRecipe(input,output,location,i);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WaterElementTier1CraftingRecipe recipe) {
            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.getResultItem());
          //  buffer.writeUtf(recipe.getGroup());
            buffer.writeVarInt(recipe.getReiryoku());

        }
    }
}
