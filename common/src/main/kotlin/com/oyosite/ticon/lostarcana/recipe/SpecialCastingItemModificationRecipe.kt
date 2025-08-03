package com.oyosite.ticon.lostarcana.recipe

import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.item.ModularCastingItemPart
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class SpecialCastingItemModificationRecipe: CraftingRecipe {
    override fun category(): CraftingBookCategory = CraftingBookCategory.EQUIPMENT

    override fun matches(
        recipeInput: CraftingInput,
        level: Level
    ): Boolean {

        for(i in 0 until recipeInput.width()) for(j in 0 until recipeInput.height()){

        }
        val items = (0 until recipeInput.size()).map(recipeInput::getItem).filterNot(ItemStack::isEmpty)
        val castingItems = items.filter { it.item is CastingItem }
        if(castingItems.size != 1)return false
        val parts = items.filter { it.item is ModularCastingItemPart }
        if(parts.size + 1 != items.size) return false
        val castingItem = castingItems.first()
        level.recipeManager.getAllRecipesFor(RecipeType.CRAFTING).mapNotNull { it.value as? CastingItemModificationRecipe }

        TODO("Not yet implemented")
    }

    override fun assemble(
        recipeInput: CraftingInput,
        provider: HolderLookup.Provider
    ): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun canCraftInDimensions(i: Int, j: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun getSerializer(): RecipeSerializer<*>? {
        TODO("Not yet implemented")
    }
}