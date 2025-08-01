package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.recipe.BasicSalisMundisTransformRecipe
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block

class BasicSalisMundisRecipeBuilder(@get:JvmName("result") val result: ItemStack, val block: Block): RecipeBuilder {
    constructor(result: ItemLike, block: Block): this(ItemStack(result.asItem()), block)

    val criteria: MutableMap<String, Criterion<*>> = LinkedHashMap()
    var group: String? = null

    override fun unlockedBy(
        string: String,
        criterion: Criterion<*>
    ): RecipeBuilder = apply{criteria.put(string, criterion)}

    override fun group(string: String?): RecipeBuilder = apply{group = string}

    override fun getResult(): Item = result.item

    override fun save(
        recipeOutput: RecipeOutput,
        resourceLocation: ResourceLocation
    ) {
        val builder: Advancement.Builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(AdvancementRequirements.Strategy.OR)
        criteria.forEach(builder::addCriterion)
        recipeOutput.accept(resourceLocation.withPrefix("salis_mundis"), BasicSalisMundisTransformRecipe(block, result), builder.build(resourceLocation.withPrefix("recipes/salis_mundis/")))
    }
}