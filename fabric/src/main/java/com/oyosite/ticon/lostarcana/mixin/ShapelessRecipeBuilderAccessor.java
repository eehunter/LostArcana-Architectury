package com.oyosite.ticon.lostarcana.mixin;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(ShapelessRecipeBuilder.class)
public interface ShapelessRecipeBuilderAccessor {
    @Accessor
    Map<String, Criterion<?>> getCriteria();

    @Accessor
    Item getResult();
    @Accessor
    int getCount();

    @Accessor
    NonNullList<Ingredient> getIngredients();

    @Accessor
    String getGroup();

    @Accessor
    RecipeCategory getCategory();

    @Invoker
    void invokeEnsureValid(ResourceLocation resourceLocation);
}
