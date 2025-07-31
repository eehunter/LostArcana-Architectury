package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.ItemLike

class ArcaneWorkbenchRecipeBuilder(@get:JvmName("result") val result: Item, val baseRecipe: CraftingRecipe): RecipeBuilder {
    constructor(result: ItemLike, baseRecipe: Recipe<*>): this(result.asItem(), baseRecipe as? CraftingRecipe ?: throw IllegalArgumentException("Base recipe must be a crafting recipe."))
    lateinit var category: RecipeCategory
    val criteria: MutableMap<String, Criterion<*>> = LinkedHashMap()
    var group: String? = null
    var showNotification = true
    val crystals = MutableList(6){0}
    var vis: Float = 0f

    override fun unlockedBy(
        string: String,
        criterion: Criterion<*>
    ): RecipeBuilder = apply{criteria.put(string, criterion)}

    override fun group(string: String?): RecipeBuilder = apply{group = string}

    override fun getResult(): Item = result



    fun setCrystals(aer: Int, aqua: Int, ignis: Int, ordo: Int, perditio: Int, terra: Int) = apply { listOf(aer, aqua, ignis, ordo, perditio, terra).forEachIndexed(crystals::set) }

    fun setVis(vis: Float) = apply { this.vis = vis }

    override fun save(
        recipeOutput: RecipeOutput,
        resourceLocation: ResourceLocation
    ) {
        val recipe = ArcaneWorkbenchRecipe(baseRecipe, crystals, vis)
        val builder: Advancement.Builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
            .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
            .requirements(AdvancementRequirements.Strategy.OR)
        criteria.forEach(builder::addCriterion)
        recipeOutput.accept(resourceLocation, recipe, builder.build(resourceLocation.withPrefix("recipes/arcane_workbench/")))
    }

}