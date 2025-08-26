package com.oyosite.ticon.lostarcana.util

import net.minecraft.world.item.component.DyedItemColor

interface ColorableBlockEntity {
    var color: UInt
    fun showTooltip(): Boolean = false
    fun asDyedItemColor(): DyedItemColor = DyedItemColor(color.toInt(), showTooltip())
}