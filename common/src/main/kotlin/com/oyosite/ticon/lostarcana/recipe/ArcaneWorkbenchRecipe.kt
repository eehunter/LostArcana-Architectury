package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.blockentity.ArcaneWorkbenchRecipeContainer
import com.oyosite.ticon.lostarcana.item.CastingItem
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

@JvmRecord
data class ArcaneWorkbenchRecipe(val base: CraftingRecipe, val visCost: List<Int>, val auraCost: Float): Recipe<ArcaneWorkbenchRecipeContainer.Wrapper> {
    constructor(base: Recipe<*>, visCost: List<Int>, auraCost: Float): this(base  as? CraftingRecipe ?: throw IllegalArgumentException("Base recipe must be a crafting recipe."), visCost, auraCost)

    override fun matches(
        recipeInput: ArcaneWorkbenchRecipeContainer.Wrapper,
        level: Level
    ): Boolean {
        val focus = recipeInput.castingItem
        val castingItem = focus.item

        if(auraCost != 0f && (focus.isEmpty || castingItem !is CastingItem))return false
        if(recipeInput.getAura(level) < auraCost) return false
        for(i in visCost.indices) if(visCost[i] > recipeInput[i+9].count)return false

        return base.matches(recipeInput.baseCraftingContainer.asCraftInput(), level)
    }

    override fun assemble(
        recipeInput: ArcaneWorkbenchRecipeContainer.Wrapper,
        provider: HolderLookup.Provider
    ): ItemStack = base.assemble(recipeInput.baseCraftingContainer.asCraftInput(), provider)

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = base.canCraftInDimensions(i, j)

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = base.getResultItem(provider)

    override fun getSerializer(): RecipeSerializer<ArcaneWorkbenchRecipe> = Serializer as RecipeSerializer<ArcaneWorkbenchRecipe>

    override fun getType(): RecipeType<*> = Type

    object Type: RecipeType<ArcaneWorkbenchRecipe>

    object Serializer: RecipeSerializer<ArcaneWorkbenchRecipe>{


        val CODEC: Codec<ArcaneWorkbenchRecipe> = RecordCodecBuilder.create {
            it.group(
                CraftingRecipe.CODEC.fieldOf("base").forGetter { it.base },
                Codec.list(Codec.INT).fieldOf("crystalCost").forGetter { it.visCost.toList() },
                Codec.FLOAT.fieldOf("visCost").forGetter { it.auraCost }
            ).apply(it, ::ArcaneWorkbenchRecipe)
        }

        val MAP_CODEC = MapCodec.assumeMapUnsafe(CODEC)

        override fun codec(): MapCodec<ArcaneWorkbenchRecipe> = MAP_CODEC

        val STREAM_CODEC = StreamCodec.of<RegistryFriendlyByteBuf, ArcaneWorkbenchRecipe>({ buf, recipe ->
            CraftingRecipe.STREAM_CODEC.encode(buf, recipe.base)
            recipe.visCost.forEach(buf::writeInt)
            buf.writeFloat(recipe.auraCost)
        }){ buf ->
            val base = CraftingRecipe.STREAM_CODEC.decode(buf) as CraftingRecipe
            val visCost = List(6){buf.readInt()}
            val auraCost = buf.readFloat()
            ArcaneWorkbenchRecipe(base,visCost,auraCost)
        }

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, ArcaneWorkbenchRecipe> = STREAM_CODEC

    }
}