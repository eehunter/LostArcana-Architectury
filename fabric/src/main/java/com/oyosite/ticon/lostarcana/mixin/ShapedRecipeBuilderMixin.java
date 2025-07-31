package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.fabric.datagen.ArcaneWorkbenchRecipeBuilder;
import com.oyosite.ticon.lostarcana.fabric.datagen.CraftingRecipeBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mixin(ShapedRecipeBuilder.class)
public class ShapedRecipeBuilderMixin implements CraftingRecipeBuilder {
    @Final @Shadow
    private RecipeCategory category;

    @Final @Shadow
    private Item result;
    @Final @Shadow
    private int count;

    @Final @Shadow
    private List<String> rows;
    @Final @Shadow
    private Map<Character, Ingredient> key;
    @Final @Shadow
    private Map<String, Criterion<?>> criteria;

    @Shadow
    private String group;
    @Shadow
    private boolean showNotification = true;

    @Override
    public @NotNull ArcaneWorkbenchRecipeBuilder arcaneWorkbench() {
        CraftingRecipe recipe = new ShapedRecipe(Objects.requireNonNullElse(group, ""), RecipeBuilder.determineBookCategory(category), ShapedRecipePattern.of(key, rows), new ItemStack(result, count), showNotification);
        return new ArcaneWorkbenchRecipeBuilder(result, recipe);
    }
}
