package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.item.ItemStack

interface VisChargeableItem {
    fun maxVis(stack: ItemStack): Float
    fun storedVis(stack: ItemStack): Float
    fun addVis(stack: ItemStack, amount: Float): Float
    fun canAcceptCharge(stack: ItemStack): Boolean = storedVis(stack) < maxVis(stack)
}