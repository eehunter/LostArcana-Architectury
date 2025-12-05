package com.oyosite.ticon.lostarcana.loot

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.util.ColorableBlockEntity
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition

class CopyDyedBlockColorFunction(list: List<LootItemCondition>) : LootItemConditionalFunction(list) {
    override fun getType(): LootItemFunctionType<out LootItemConditionalFunction> = TYPE

    override fun run(
        itemStack: ItemStack,
        lootContext: LootContext
    ): ItemStack {
        val be = lootContext.getParamOrNull(LootContextParams.BLOCK_ENTITY) as? ColorableBlockEntity ?: return itemStack
        return itemStack.apply { set(DataComponents.DYED_COLOR, be.asDyedItemColor()) }
    }

    companion object{
        val CODEC: MapCodec<CopyDyedBlockColorFunction> = RecordCodecBuilder.mapCodec { commonFields(it).apply(it, ::CopyDyedBlockColorFunction) }
        val TYPE = LootItemFunctionType(CODEC)
    }
}