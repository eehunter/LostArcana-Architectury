package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
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
}