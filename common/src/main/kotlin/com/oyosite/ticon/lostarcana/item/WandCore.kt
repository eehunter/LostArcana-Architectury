package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

open class WandCore(properties: Properties, val storage: Float, val color: UInt) : Item(properties), ModularCastingItemPart {
    override fun getColor(stack: ItemStack): UInt = color
    override fun getEfficiencyMultiplier(stack: ItemStack): Float = 1f
    override fun getStorageMultiplier(stack: ItemStack): Float = storage
}