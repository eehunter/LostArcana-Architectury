package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

interface Resonator {
    fun getAvailableAura(stack: ItemStack, level: Level, pos: Vec3, entity: Entity? = null): Float
    fun drainAura(stack: ItemStack, level: Level, pos: Vec3, amount: Float, entity: Entity? = null)
}