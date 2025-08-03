package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.item.CastingItemComponent
import com.oyosite.ticon.lostarcana.item.ModularCastingItemPart
import com.oyosite.ticon.lostarcana.item.VIS_STORAGE_COMPONENT
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level
import java.util.Optional

class CastingItemModificationRecipe(val castingItem: Ingredient, val part: Ingredient, val partSlot: Identifier, val relativeSlotId: Int, val clearVis: Optional<Boolean>): CraftingRecipe {
    override fun category(): CraftingBookCategory = CraftingBookCategory.EQUIPMENT

    val relativeSlot get() = listOf(-1 to -1, 0 to -1, 1 to -1, -1 to 0, 1 to 0, -1 to 1, 0 to 1, 1 to 1)[relativeSlotId]

    val shouldClearVis: Boolean = clearVis.orElseGet { true }

    override fun matches(
        recipeInput: CraftingInput,
        level: Level
    ): Boolean {
        if(recipeInput.items().filterNot(ItemStack::isEmpty).size != 2) return false
        return matchesIgnoreOtherItems(recipeInput, level)
    }

    fun matchesIgnoreOtherItems(
        recipeInput: CraftingInput,
        level: Level
    ): Boolean {
        for(i in 0 until recipeInput.width()) for(j in 0 until recipeInput.height()){
            if(matchesSlot(recipeInput, level, i, j))return true
        }
        return false
    }

    fun matchesSlot(recipeInput: CraftingInput, level: Level?, i: Int, j: Int): Boolean{
        val item = recipeInput.getItem(i, j)
        if(castingItem.test(item)){
            val itemType = item.item
            if(itemType !is CastingItem)return false
            if(!itemType.getPartComponents(item).contains(BuiltInRegistries.DATA_COMPONENT_TYPE.get(partSlot))) return false
            val x = relativeSlot.first + i
            val y = relativeSlot.second + j
            if(x !in 0 until recipeInput.width()) return false
            if(y !in 0 until recipeInput.height()) return false
            return part.test(recipeInput.getItem(x,y)) && recipeInput.getItem(x,y).item is ModularCastingItemPart
        }
        return false
    }

    override fun assemble(
        recipeInput: CraftingInput,
        provider: HolderLookup.Provider
    ): ItemStack {
        for(i in 0 until recipeInput.width()) for(j in 0 until recipeInput.height()) {
            val item = recipeInput.getItem(i, j)
            if (castingItem.test(item)) {
                val x = relativeSlot.first + i
                val y = relativeSlot.second + j

                val augment = recipeInput.getItem(x,y)
                return transformStack(recipeInput, provider, item, augment)
            }
        }
        throw IllegalStateException("Recipe was assembled but doesn't match.")
    }

    fun transformStack(
        recipeInput: CraftingInput,
        provider: HolderLookup.Provider,
        stack: ItemStack,
        augment: ItemStack
    ): ItemStack{
        val itemType = stack.item
        assert(itemType is CastingItem)
        val otpt = stack.copy()
        otpt.set<CastingItemComponent>(BuiltInRegistries.DATA_COMPONENT_TYPE.get(partSlot) as DataComponentType<CastingItemComponent>, (augment.item as ModularCastingItemPart).castingItemComponent(augment))
        if(shouldClearVis && otpt.has(VIS_STORAGE_COMPONENT))otpt.set(VIS_STORAGE_COMPONENT, 0f)
        return otpt
    }


    override fun getRemainingItems(recipeInput: CraftingInput): NonNullList<ItemStack> {
        val list = NonNullList.withSize(recipeInput.size(), ItemStack.EMPTY)
        return getRemainingItems(recipeInput, list)
    }

    fun getRemainingItems(recipeInput: CraftingInput, list: NonNullList<ItemStack>): NonNullList<ItemStack>{
        for(i in 0 until recipeInput.width()) for(j in 0 until recipeInput.height()) {
            val item = recipeInput.getItem(i, j)
            if(!castingItem.test(item))continue
            list[i + relativeSlot.first + (j + relativeSlot.second)*recipeInput.width()] = item.get(BuiltInRegistries.DATA_COMPONENT_TYPE.get(partSlot) as DataComponentType<CastingItemComponent>)?.stack?.copy?: ItemStack.EMPTY
        }
        return list
    }

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = true

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack? = castingItem.items.firstOrNull()

    override fun getSerializer(): RecipeSerializer<*> = Serializer

    object Serializer: RecipeSerializer<CastingItemModificationRecipe> {
        val CODEC = RecordCodecBuilder.create {
            it.group(
                Ingredient.CODEC.fieldOf("castingItem").forGetter(CastingItemModificationRecipe::castingItem),
                Ingredient.CODEC.fieldOf("part").forGetter(CastingItemModificationRecipe::part),
                ResourceLocation.CODEC.fieldOf("partSlot").forGetter(CastingItemModificationRecipe::partSlot),
                Codec.INT.fieldOf("relativeSlotId").forGetter(CastingItemModificationRecipe::relativeSlotId),
                Codec.BOOL.optionalFieldOf("clearVis").forGetter(CastingItemModificationRecipe::clearVis)
            ).apply(it, ::CastingItemModificationRecipe)
        }
        val MAP_CODEC = MapCodec.assumeMapUnsafe(CODEC)

        val STREAM_CODEC = StreamCodec.of<RegistryFriendlyByteBuf, CastingItemModificationRecipe>({ buf, recp ->
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recp.castingItem)
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recp.part)
            ResourceLocation.STREAM_CODEC.encode(buf, recp.partSlot)
            buf.writeInt(recp.relativeSlotId)
            buf.writeBoolean(recp.shouldClearVis)
        }){buf->
            CastingItemModificationRecipe(
                Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                ResourceLocation.STREAM_CODEC.decode(buf),
                buf.readInt(),
                Optional.of(buf.readBoolean())
            )
        }

        override fun codec(): MapCodec<CastingItemModificationRecipe> = MAP_CODEC

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, CastingItemModificationRecipe> = STREAM_CODEC

    }
}