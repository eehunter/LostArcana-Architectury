package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

interface ModularCastingItemPart {
    fun getColor(stack: ItemStack): UInt
    fun getEfficiencyMultiplier(stack: ItemStack): Float
    fun getStorageMultiplier(stack: ItemStack): Float

    val castingItemComponent: CastingItemComponent get() = (this as? Item)?.let(::ItemStack)?.let { CastingItemComponent(getColor(it).toInt(), getEfficiencyMultiplier(it), getStorageMultiplier(it), it) }?:throw AssertionError("A ModularCastingItemPart must be an Item.")
    fun castingItemComponent(stack: ItemStack) = CastingItemComponent(getColor(stack).toInt(), getEfficiencyMultiplier(stack), getStorageMultiplier(stack), stack.copy())
}