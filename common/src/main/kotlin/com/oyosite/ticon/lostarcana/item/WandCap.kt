package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

open class WandCap(properties: Properties, val efficiency: Float, val color: UInt) : Item(properties), ModularCastingItemPart {
    override fun getColor(stack: ItemStack): UInt = color
    override fun getEfficiencyMultiplier(stack: ItemStack): Float = efficiency
    override fun getStorageMultiplier(stack: ItemStack): Float = 1f
}