package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

open class WandItem(properties: Properties) : CastingItem(properties) {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    open fun maxVis(stack: ItemStack): Float = 100f

    override fun availableVis(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float = stack.components.get(VIS_STORAGE_COMPONENT)?:0f
    override fun consumeVis(stack: ItemStack, level: Level, pos: Vec3, amount: Float, entity: Entity?): Boolean {
        val available = availableVis(stack, level, pos, entity)
        if(available < amount)return false
        stack.set(VIS_STORAGE_COMPONENT, available)
        return true
    }
}