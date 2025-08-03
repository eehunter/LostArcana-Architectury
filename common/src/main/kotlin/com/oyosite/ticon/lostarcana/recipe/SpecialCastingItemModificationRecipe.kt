package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.item.ModularCastingItemPart
import com.oyosite.ticon.lostarcana.item.WAND_ITEM
import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.client.Minecraft
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.TransientCraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class SpecialCastingItemModificationRecipe(/*val ctx: ContainerLevelAccess*/): CraftingRecipe {
    override fun category(): CraftingBookCategory = CraftingBookCategory.EQUIPMENT

    // This is probably a terrible idea.
    var recipeManager: RecipeManager? = null

    override fun matches(
        recipeInput: CraftingInput,
        level: Level
    ): Boolean {
        var castingItem: ItemStack = ItemStack.EMPTY
        var (x, y) = -1 to -1
        for(i in 0 until recipeInput.width()) for(j in 0 until recipeInput.height()){
            recipeInput.getItem(i, j).takeIf { it.item is CastingItem }?.let {
                castingItem = it
                x = i
                y = j
                break
            }
            //if(i == recipeInput.width()-1 && j == recipeInput.height()-1) return false
        }
        if(castingItem.isEmpty) return false

        val items = (0 until recipeInput.size()).map(recipeInput::getItem).filterNot(ItemStack::isEmpty)
        val parts = items.filter { it.item is ModularCastingItemPart }
        if(parts.size < 2 || parts.size + 1 != items.size) return false
        recipeManager = level.recipeManager
        val possibleMatches = recipeManager!!.getAllRecipesFor(RecipeType.CRAFTING).mapNotNull { it.value as? CastingItemModificationRecipe }
        val relativeSlotIds = mutableSetOf<Int>()
        val uniqueMatches = possibleMatches.filter { it.matchesSlot(recipeInput, level, x, y) }.filter { relativeSlotIds.add(it.relativeSlotId) }

        return uniqueMatches.size == parts.size
    }

    override fun assemble(
        recipeInput: CraftingInput,
        provider: HolderLookup.Provider
    ): ItemStack? {
        /*return ctx.evaluate { level, pos -> assembleWithLevel(recipeInput, provider, level) }.orElseGet {
            Minecraft.getInstance().level?.let { assembleWithLevel(recipeInput, provider, it) }?:
            ItemStack.EMPTY
        }*/
        return assembleWithRecipeManager(recipeInput, provider, recipeManager?: Minecraft.getInstance().level?.recipeManager ?: return null)
    }

    fun assembleWithRecipeManager(
        recipeInput: CraftingInput,
        provider: HolderLookup.Provider,
        recipeManager: RecipeManager
    ): ItemStack?{
        var castingItem: ItemStack = ItemStack.EMPTY
        var (x, y) = -1 to -1
        for(i in 0 until recipeInput.width()) for(j in 0 until recipeInput.height()){
            recipeInput.getItem(i, j).takeIf { it.item is CastingItem }?.let {
                castingItem = it
                x = i
                y = j
                break
            }
        }

        val possibleMatches = recipeManager.getAllRecipesFor(RecipeType.CRAFTING).mapNotNull { it.value as? CastingItemModificationRecipe }
        val relativeSlotIds = mutableSetOf<Int>()
        val uniqueMatches = possibleMatches.filter { it.matchesSlot(recipeInput, null, x, y) }.filter { relativeSlotIds.add(it.relativeSlotId) }

        return uniqueMatches.fold(castingItem){stack, recipe ->
            val slot = recipe.relativeSlot
            recipe.transformStack(recipeInput, provider, stack, recipeInput.getItem(x+slot.first, y+slot.second))
        }
    }

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = i*j >= 3

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = ItemStack(+WAND_ITEM)

    override fun getSerializer(): RecipeSerializer<*> = Serializer

    override fun equals(other: Any?): Boolean {
        return other is SpecialCastingItemModificationRecipe
    }

    object Serializer: RecipeSerializer<SpecialCastingItemModificationRecipe>{
        val CODEC: MapCodec<SpecialCastingItemModificationRecipe> = MapCodec.unit { SpecialCastingItemModificationRecipe() }
        
        //: Codec<SpecialCastingItemModificationRecipe> = Codec.unit { SpecialCastingItemModificationRecipe() }//RecordCodecBuilder.create { it.point(SpecialCastingItemModificationRecipe()) }
        //val MAP_CODEC = MapCodec.assumeMapUnsafe(CODEC)
        
        override fun codec(): MapCodec<SpecialCastingItemModificationRecipe> = CODEC

        val STREAM_CODEC = StreamCodec.unit<RegistryFriendlyByteBuf, SpecialCastingItemModificationRecipe> ( (SpecialCastingItemModificationRecipe()) )

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, SpecialCastingItemModificationRecipe> = STREAM_CODEC
    }
}