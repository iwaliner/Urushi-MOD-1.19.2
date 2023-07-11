package com.iwaliner.urushi.jei;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.recipe.HammeringRecipe;
import com.iwaliner.urushi.recipe.ThrowingInRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

public class ThrowingInRecipeCategory implements IRecipeCategory<ThrowingInRecipe> {
    public static final ResourceLocation location=new ResourceLocation(ModCoreUrushi.ModID,"throwing_in");
    public static final ResourceLocation tex=new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/fryer.png");
    private final IDrawable background;
    private final IDrawable icon;
    private  final String textName;



    public ThrowingInRecipeCategory(IGuiHelper guiHelper){
        this.background=guiHelper.createDrawable(tex,0,166,82,35);
        this.icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(Items.WATER_BUCKET));
        textName="throwing_in";
    }


    @Override
    public RecipeType<ThrowingInRecipe> getRecipeType() {
        return JEIUrushiPlugin.JEI_THROWING_IN;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.jei.category.urushi."+textName);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ThrowingInRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(INPUT, 1, 5)
                .addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(OUTPUT, 61, 5)
                .addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(ThrowingInRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Component text = Component.translatable("gui.jei.category.urushi."+textName+".text");
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int stringWidth = fontRenderer.width(text);
        fontRenderer.draw(stack, text, background.getWidth() - stringWidth, 28, 0xFF808080);

    }
}
