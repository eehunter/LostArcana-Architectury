package com.oyosite.ticon.lostarcana.util

import com.oyosite.ticon.lostarcana.aura.AuraSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3


fun getNearestAuraSourceInRange(entity: Entity?, range: Double): AuraSource? {
    entity?: return null
    return getNearestAuraSourceInRange(entity.level(), entity.position(), range)
}
fun getNearestAuraSourceInRange(level: Level, pos: Vec3, range: Double): AuraSource? {
    //TODO: Add a way for blocks to be usable AuraSources as well.
    val nodes: List<Entity> = level.getEntities(null, AABB.ofSize(pos, 2*range, 2*range, 2*range)) { it is AuraSource }
    val node = nodes.minByOrNull { it.distanceToSqr(pos) }
    return node as? AuraSource
}