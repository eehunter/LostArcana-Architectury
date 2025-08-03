package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.util.drainAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getAuraAtLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

class VisResonator(properties: Properties) : Item(properties), Resonator {
    override fun getAvailableAura(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float =
        getAuraAtLocation(level, pos)

    override fun drainAura(stack: ItemStack, level: Level, pos: Vec3, amount: Float, entity: Entity?) =
        drainAuraAtLocation(level, pos, amount)
}