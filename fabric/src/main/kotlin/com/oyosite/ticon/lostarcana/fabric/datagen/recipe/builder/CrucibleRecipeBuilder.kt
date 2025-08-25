package com.oyosite.ticon.lostarcana.fabric.datagen.recipe.builder

import com.oyosite.ticon.lostarcana.AspectStacks
import com.oyosite.ticon.lostarcana.aspect.AspectStack
import com.oyosite.ticon.lostarcana.recipe.BasicSalisMundisTransformRecipe
import com.oyosite.ticon.lostarcana.recipe.CrucibleRecipe
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class CrucibleRecipeBuilder(@get:JvmName("result") val result: ItemStack): RecipeBuilder {
    constructor(result: Item): this(ItemStack(result))

    val criteria: MutableMap<String, Criterion<*>> = LinkedHashMap()
    var group: String? = null
    lateinit var catalyst: Ingredient
    val aspects: MutableList<AspectStack> = mutableListOf()

    fun catalyst(ingredient: Ingredient) = apply { catalyst = ingredient }
    fun catalyst(ingredient: Item) = catalyst(Ingredient.of(ingredient))
    fun catalyst(ingredient: TagKey<Item>) = catalyst(Ingredient.of(ingredient))
    fun aspect(aspectStack: AspectStack) = apply { aspects.add(aspectStack) }

    override fun unlockedBy(
        string: String,
        criterion: Criterion<*>
    ): RecipeBuilder = apply{ criteria[string] = criterion }

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
        recipeOutput.accept(resourceLocation.withPrefix("crucible/"), CrucibleRecipe(result, catalyst, aspects), builder.build(resourceLocation.withPrefix("recipes/crucible/")))
    }
}