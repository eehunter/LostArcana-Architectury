package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import com.oyosite.ticon.lostarcana.util.drainAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getNearestAuraSourceInRange
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

open class CasterGauntlet(properties: Properties): CastingItem(properties) {
    @Suppress("unchecked_cast")
    override fun availableVis(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float {
        return getAuraAtLocation(level, pos)
    }

    @Suppress("unchecked_cast")
    override fun consumeVis(
        stack: ItemStack, level: Level, pos: Vec3,
        amount: Float,
        entity: Entity?
    ): Boolean {
        if(availableVis(stack, level, pos, entity) < amount) return false
        drainAuraAtLocation(level, pos, amount)
        return true
    }
}