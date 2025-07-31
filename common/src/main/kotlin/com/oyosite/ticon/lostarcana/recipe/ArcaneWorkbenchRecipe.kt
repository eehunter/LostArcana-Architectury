package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.block.ArcaneWorkbench
import com.oyosite.ticon.lostarcana.blockentity.ArcaneWorkbenchRecipeContainer
import com.oyosite.ticon.lostarcana.item.CasterGauntlet
import com.oyosite.ticon.lostarcana.util.getNearestAuraSourceInRange
import io.netty.buffer.ByteBuf
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

@JvmRecord
data class ArcaneWorkbenchRecipe<T: CraftingRecipe>(val base: T, val visCost: List<Int>, val auraCost: Float): Recipe<ArcaneWorkbenchRecipeContainer.Wrapper> {

    override fun matches(
        recipeInput: ArcaneWorkbenchRecipeContainer.Wrapper,
        level: Level
    ): Boolean {
        if(!base.matches(recipeInput.baseCraftingContainer.asCraftInput(), level))return false
        //TODO: The Arcane Workbench should not draw directly from the nearest AuraSource, but from the item in its wand/gauntlet slot.
        getNearestAuraSourceInRange(level, recipeInput.pos.center, CasterGauntlet.AURA_RANGE)?.vis?.let {
            if(it < auraCost) return false
        }
        for(i in visCost.indices)
            if(visCost[i] < recipeInput[i+9].count)return false

        return true
    }

    override fun assemble(
        recipeInput: ArcaneWorkbenchRecipeContainer.Wrapper,
        provider: HolderLookup.Provider
    ): ItemStack = base.assemble(recipeInput.baseCraftingContainer.asCraftInput(), provider)

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = base.canCraftInDimensions(i, j)

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = base.getResultItem(provider)

    override fun getSerializer(): RecipeSerializer<ArcaneWorkbenchRecipe<*>> = Serializer[this] as RecipeSerializer<ArcaneWorkbenchRecipe<*>>

    override fun getType(): RecipeType<*>? {
        TODO("Not yet implemented")
    }

    class Serializer<T: CraftingRecipe>(base: T): RecipeSerializer<ArcaneWorkbenchRecipe<T>>{

        val CODEC: Codec<ArcaneWorkbenchRecipe<T>> = RecordCodecBuilder.create {
            it.group(
                (base.serializer.codec() as MapCodec<T>).fieldOf("base").forGetter { it.base },
                Codec.list(Codec.INT).fieldOf("crystalCost").forGetter { it.visCost.toList() },
                Codec.FLOAT.fieldOf("visCost").forGetter { it.auraCost }
            ).apply(it, ::ArcaneWorkbenchRecipe)
        }

        val MAP_CODEC = MapCodec.assumeMapUnsafe(CODEC)

        override fun codec(): MapCodec<ArcaneWorkbenchRecipe<T>> = MAP_CODEC

        val STREAM_CODEC = StreamCodec.of<RegistryFriendlyByteBuf, ArcaneWorkbenchRecipe<T>>({ buf, recipe ->
            val baseSerializer = buf.registryAccess().registryOrThrow(Registries.RECIPE_SERIALIZER).getKey(recipe.base.serializer)!!
            buf.writeResourceLocation(baseSerializer)
            recipe.base.serializer.streamCodec().encode(buf, recipe.)
            //buf.registryAccess().registryOrThrow(Registries.RECIPE_TYPE).get()
        }){}

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, ArcaneWorkbenchRecipe<T>> {
            TODO("Not yet implemented")
        }

        companion object{
            val SERIALIZERS = mutableMapOf<RecipeType<*>, Serializer<*>>()
            operator fun get(recipe: ArcaneWorkbenchRecipe<*>): Serializer<*> = SERIALIZERS.getOrPut(recipe.base.type) { Serializer(recipe.base) }
        }
    }
}