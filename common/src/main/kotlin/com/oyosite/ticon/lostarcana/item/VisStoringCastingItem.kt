package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.item.ItemStack

abstract class VisStoringCastingItem(properties: Properties): CastingItem(properties), VisChargeableItem {

    override fun storedVis(stack: ItemStack): Float = stack.get(VIS_STORAGE_COMPONENT) ?: 0f
    override fun maxVis(stack: ItemStack): Float =
        getPartComponents(stack).mapNotNull(stack::get).map(CastingItemComponent::storage).fold(1f){a,b->a*b}
}