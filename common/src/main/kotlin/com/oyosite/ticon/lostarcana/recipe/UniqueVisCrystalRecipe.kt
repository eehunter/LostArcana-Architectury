package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.MapCodec
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.item.VisCrystalItem
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class UniqueVisCrystalRecipe(val base: ShapelessRecipe): CraftingRecipe {

    override fun category(): CraftingBookCategory = base.category()

    override fun matches(
        recipeInput: CraftingInput,
        level: Level
    ): Boolean {
        if(!base.matches(recipeInput, level))return false
        val aspects = mutableSetOf<Aspect>()
        recipeInput.items().forEach {
            val item = it.item
            if(item !is VisCrystalItem)return@forEach
            if(!(item.getAspects(it)?.firstOrNull()?.aspect?.let(aspects::add)?:true))return false
        }
        return true
    }

    override fun assemble(
        recipeInput: CraftingInput,
        provider: HolderLookup.Provider
    ): ItemStack = base.assemble(recipeInput, provider)

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = base.canCraftInDimensions(i,j)

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = base.getResultItem(provider)

    override fun getIngredients(): NonNullList<Ingredient> = base.ingredients

    override fun getSerializer(): RecipeSerializer<*> = Serializer

    object Serializer: RecipeSerializer<UniqueVisCrystalRecipe>{
        val CODEC = MapCodec.of(
            RecipeSerializer.SHAPELESS_RECIPE.codec().comap(UniqueVisCrystalRecipe::base),
            RecipeSerializer.SHAPELESS_RECIPE.codec().map(::UniqueVisCrystalRecipe),
        )
        //.of(UniqueVisCrystalRecipe::base, MapCodec.assumeMapUnsafe(ShapelessRecipe.CODEC))

        val STREAM_CODEC = RecipeSerializer.SHAPELESS_RECIPE.streamCodec().map(::UniqueVisCrystalRecipe, UniqueVisCrystalRecipe::base)

        override fun codec(): MapCodec<UniqueVisCrystalRecipe> = CODEC

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, UniqueVisCrystalRecipe> = STREAM_CODEC

    }
}