package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.util.drainAuraAtLocation
import net.minecraft.core.component.DataComponentType
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

open class CasterGauntlet(properties: Properties): CastingItem(properties) {
    override fun getPartComponents(stack: ItemStack): List<DataComponentType<CastingItemComponent>> = listOf(WAND_CAP)

    override fun availableVis(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float {
        val resonatorComponent = stack.get(RESONATOR)?:return 0f
        val resonator = resonatorComponent.resonator?:return 0f
        return resonator.getAvailableAura(stack, level, pos, entity) * visEfficiency(stack)
    }

    override fun consumeVis(
        stack: ItemStack, level: Level, pos: Vec3,
        amount: Float,
        entity: Entity?
    ): Boolean {
        val resonatorComponent = stack.get(RESONATOR)?:return false
        val resonator = resonatorComponent.resonator?:return false
        val amt = amount/visEfficiency(stack)
        if(availableVis(stack, level, pos, entity) < amt) return false
        resonator.drainAura(stack,  level, pos, amt, entity)
        return true
    }
}