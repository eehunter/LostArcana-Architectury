package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.item.CastingItem

class WandItem(properties: Properties) : CastingItem(properties) {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    override val maxVis: Float
        get() = 100f
}