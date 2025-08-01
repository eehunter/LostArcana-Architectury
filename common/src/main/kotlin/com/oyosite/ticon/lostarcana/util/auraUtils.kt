package com.oyosite.ticon.lostarcana.util

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.aura.LevelAuraManager
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

val AURA_DECAY_COEFFICIENT = 0.025f

val Level.auraSources get() = (this as LevelAuraManager).`lostarcana$getSources`() as MutableSet<AuraSource>

fun getAuraFromSourceAtLocation(source: AuraSource, pos: Vec3): Float = source.vis/((pos.distanceToSqr(source.pos) * AURA_DECAY_COEFFICIENT) + 1).toFloat()
fun getAuraAtLocation(level: Level, pos: Vec3): Float = level.auraSources.map { getAuraFromSourceAtLocation(it, pos) }.sum()
fun drainAuraFromSource(source: AuraSource, amount: Float) {source.vis-=amount}
fun drainAuraAtLocation(level: Level, pos: Vec3, amount: Float){
    val auraStrength = getAuraAtLocation(level, pos)
    level.auraSources.forEach {
        drainAuraFromSource(it,amount*getAuraFromSourceAtLocation(it, pos)/auraStrength)
    }
}

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