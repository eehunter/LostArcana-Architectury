package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.util.drainAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getAuraAtLocation
import net.minecraft.core.component.DataComponentType
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

open class CasterGauntlet(properties: Properties): CastingItem(properties) {
    override fun getPartComponents(stack: ItemStack): List<DataComponentType<CastingItemComponent>> = listOf(WAND_CAP)

    @Suppress("unchecked_cast")
    override fun availableVis(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float {
        return getAuraAtLocation(level, pos) * visEfficiency(stack)
    }

    @Suppress("unchecked_cast")
    override fun consumeVis(
        stack: ItemStack, level: Level, pos: Vec3,
        amount: Float,
        entity: Entity?
    ): Boolean {
        val amt = amount/visEfficiency(stack)
        if(availableVis(stack, level, pos, entity) < amt) return false
        drainAuraAtLocation(level, pos, amt)
        return true
    }
}