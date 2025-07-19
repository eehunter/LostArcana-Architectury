package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

open class WandItem(properties: Properties) : CastingItem(properties) {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    open fun maxVis(stack: ItemStack): Float = 100f

    override fun availableVis(stack: ItemStack, entity: Entity?): Float = stack.components.get(VIS_STORAGE_COMPONENT)?:0f
}