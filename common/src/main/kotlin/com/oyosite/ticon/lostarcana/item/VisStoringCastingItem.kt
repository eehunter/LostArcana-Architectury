package com.oyosite.ticon.lostarcana.item

import net.minecraft.core.component.DataComponentType
import net.minecraft.world.item.ItemStack

abstract class VisStoringCastingItem(properties: Properties): CastingItem(properties), VisChargeableItem {

    override fun storedVis(stack: ItemStack): Float = stack.get(VIS_STORAGE_COMPONENT) ?: 0f
    override fun maxVis(stack: ItemStack): Float{
        val comps = getPartComponents(stack)
        //val parts = comps.mapNotNull(stack::get).filter { it.item is ModularCastingItemPart }
        //val partItems = parts.mapNotNull { it.item as? ModularCastingItemPart }
        //return parts.mapIndexed { i, stack -> partItems[i].getStorageMultiplier(stack) }.fold(1f){a,b->a*b}
        return comps.mapNotNull(stack::get).map(CastingItemComponent::storage).fold(1f){a,b->a*b}
    }
}