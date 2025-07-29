package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import com.oyosite.ticon.lostarcana.util.getNearestAuraSourceInRange
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.AABB

open class CasterGauntlet(properties: Properties): CastingItem(properties) {
    @Suppress("unchecked_cast")
    override fun availableVis(stack: ItemStack, entity: Entity?): Float {
        entity?:return 0f
        val nodes: List<AuraNodeEntity> = entity.level().getEntities(null, AABB.ofSize(entity.position(), 10.0, 10.0, 10.0)) { it is AuraNodeEntity } as List<AuraNodeEntity>
        return nodes.minByOrNull { it.distanceToSqr(entity) }?.vis?:0f
    }

    @Suppress("unchecked_cast")
    override fun consumeVis(
        stack: ItemStack,
        amount: Float,
        entity: Entity?
    ): Boolean {
        val node = getNearestAuraSourceInRange(entity, AURA_RANGE)?:return false
        if(node.vis < amount)return false
        node.vis -= amount
        return true
    }

    companion object{
        val AURA_RANGE = 10.0
    }
}