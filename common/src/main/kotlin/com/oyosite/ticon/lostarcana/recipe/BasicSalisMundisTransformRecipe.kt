package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
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




    object Serializer: RecipeSerializer<BasicSalisMundisTransformRecipe>{
        val CODEC = RecordCodecBuilder.create {
            it.group(
                Block.CODEC.fieldOf("block").forGetter(BasicSalisMundisTransformRecipe::block),
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