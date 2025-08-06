package com.oyosite.ticon.lostarcana.fabric.datagen.recipe.builder

import com.oyosite.ticon.lostarcana.mixin.ShapelessRecipeBuilderAccessor
import com.oyosite.ticon.lostarcana.recipe.UniqueVisCrystalRecipe
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.ShapelessRecipe

class UniqueVisCrystalRecipeBuilder(val base: ShapelessRecipeBuilder): RecipeBuilder {
    private val accessor = base as ShapelessRecipeBuilderAccessor

    override fun unlockedBy(
        string: String,
        criterion: Criterion<*>
    ): RecipeBuilder = base.unlockedBy(string, criterion)

    override fun group(string: String?): RecipeBuilder = base.group(string)

    override fun getResult(): Item = base.result

    var stack: ItemStack? = null

    fun setOutput(stack: ItemStack) = apply {this.stack = stack}

    override fun save(
        recipeOutput: RecipeOutput,
        resourceLocation: ResourceLocation
    ) {
        accessor.invokeEnsureValid(resourceLocation)
        val builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR)!!
        accessor.criteria.forEach(builder::addCriterion)
        val recipe = UniqueVisCrystalRecipe(ShapelessRecipe(
            accessor.group?:"",
            RecipeBuilder.determineBookCategory(accessor.category),
            stack?:ItemStack(result, accessor.count),
            accessor.ingredients
        ))

    }
}