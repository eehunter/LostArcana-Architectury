package com.oyosite.ticon.lostarcana.recipe

import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType

abstract class SalisMundisTransformRecipe: Recipe<SalisMundisRecipeInput> {
    override fun getType(): RecipeType<*> = Type

    object Type: RecipeType<SalisMundisTransformRecipe>
}