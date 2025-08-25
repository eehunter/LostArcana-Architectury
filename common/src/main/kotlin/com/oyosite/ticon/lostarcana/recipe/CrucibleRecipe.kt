package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.AspectStacks
import com.oyosite.ticon.lostarcana.blockentity.CrucibleBlockEntity
import com.oyosite.ticon.lostarcana.item.ASPECTS_CODEC
import com.oyosite.ticon.lostarcana.item.ASPECTS_STREAM_CODEC
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

open class CrucibleRecipe(val result: ItemStack, val catalyst: Ingredient, val aspects: AspectStacks): Recipe<CrucibleBlockEntity> {
    override fun matches(
        recipeInput: CrucibleBlockEntity,
        level: Level
    ): Boolean {
        if(!catalyst.test(recipeInput.usedItem)) return false
        return aspects.all { recipeInput.aspects.getOrElse(it.aspect){0} >= it.amount }
    }

    override fun assemble(
        recipeInput: CrucibleBlockEntity,
        provider: HolderLookup.Provider
    ): ItemStack = result.copy()

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = true

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = result

    override fun getSerializer(): RecipeSerializer<*> = Serializer

    override fun getType(): RecipeType<*> = Type


    object Type: RecipeType<CrucibleRecipe>{
        override fun toString(): String = "lostarcana:crucible"
    }

    object Serializer: RecipeSerializer<CrucibleRecipe>{
        val CODEC = MapCodec.assumeMapUnsafe(RecordCodecBuilder.create { it.group(
            ItemStack.CODEC.fieldOf("result").forGetter(CrucibleRecipe::result),
            Ingredient.CODEC.fieldOf("catalyst").forGetter(CrucibleRecipe::catalyst),
            ASPECTS_CODEC.fieldOf("aspects").forGetter(CrucibleRecipe::aspects)
            ).apply(it, ::CrucibleRecipe) })

        val STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, CrucibleRecipe::result,
            Ingredient.CONTENTS_STREAM_CODEC, CrucibleRecipe::catalyst,
            ASPECTS_STREAM_CODEC, CrucibleRecipe::aspects,
            ::CrucibleRecipe
        )

        override fun codec(): MapCodec<CrucibleRecipe> = CODEC

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipe> = STREAM_CODEC

    }
}