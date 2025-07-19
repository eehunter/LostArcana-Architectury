package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.item.Item

abstract class CastingItem(properties: Properties) : Item(properties) {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    abstract val maxVis: Float
}