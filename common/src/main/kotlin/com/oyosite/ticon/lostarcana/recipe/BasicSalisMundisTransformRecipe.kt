package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.item.SALIS_MUNDIS
import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block


class BasicSalisMundisTransformRecipe(val block: Block, val result: ItemStack): SalisMundisTransformRecipe() {

    override fun matches(recipeInput: SalisMundisRecipeInput, level: Level): Boolean {
        return block == level.getBlockState(recipeInput.block).block
    }

    override fun assemble(recipeInput: SalisMundisRecipeInput, provider: HolderLookup.Provider): ItemStack = result.copy()

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = true

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = result

    override fun getSerializer(): RecipeSerializer<*> = Serializer

    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(Ingredient.EMPTY, Ingredient.of(block), Ingredient.of(+SALIS_MUNDIS))


    object Serializer: RecipeSerializer<BasicSalisMundisTransformRecipe>{
        val CODEC = RecordCodecBuilder.create {
            it.group(
                BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(BasicSalisMundisTransformRecipe::block),
                ItemStack.CODEC.fieldOf("result").forGetter(BasicSalisMundisTransformRecipe::result)
            ).apply(it, ::BasicSalisMundisTransformRecipe)
        }
        val MAP_CODEC = MapCodec.assumeMapUnsafe(CODEC)

        override fun codec(): MapCodec<BasicSalisMundisTransformRecipe> = MAP_CODEC

        val STREAM_CODEC = StreamCodec.of<RegistryFriendlyByteBuf, BasicSalisMundisTransformRecipe>({ buf, recipe ->
            buf.writeResourceLocation(BuiltInRegistries.BLOCK.getKey(recipe.block))
            ItemStack.STREAM_CODEC.encode(buf, recipe.result)
        }){ buf ->
            BasicSalisMundisTransformRecipe(BuiltInRegistries.BLOCK.get(buf.readResourceLocation()), ItemStack.STREAM_CODEC.decode(buf))
        }

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, BasicSalisMundisTransformRecipe> = STREAM_CODEC

    }
}