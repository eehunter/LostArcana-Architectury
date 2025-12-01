package com.oyosite.ticon.lostarcana.util

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.aura.LevelAuraManager
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

val AURA_DECAY_COEFFICIENT = 0.025f

val Level.auraSources get() = (this as LevelAuraManager).`lostarcana$getSources`() as MutableSet<AuraSource>

fun triggerFluxEvent(level: Level, pos: Vec3, auraSource: AuraSource){
    //TODO
}

/**
 * Releases flux into an area.
 * If no aura sources are loaded to accept flux, returns false.
 * Otherwise, returns true.
 * */
fun releaseFluxAtLocation(level: Level, pos: Vec3, amount: Float, exclude: Collection<AuraSource> = listOf()): Boolean{
    val s = level.auraSources.filterNot(exclude::contains).map { it to getFluxAffinityFromSourceAtLocation(it, pos) }.filterNot { it.second == 0f }
    if(s.isEmpty())return false
    val totalAffinity = s.map(Pair<AuraSource, Float>::second).sum()
    s.forEach {
        it.first.flux +=  amount*it.second/totalAffinity
    }
    return true
}
fun getFluxFromSourceAtLocation(source: AuraSource, pos: Vec3): Float = source.flux/((pos.distanceToSqr(source.pos) * AURA_DECAY_COEFFICIENT) + 1).toFloat()
fun getFluxAtLocation(level: Level, pos: Vec3): Float = level.auraSources.map { getFluxFromSourceAtLocation(it, pos) }.sum()

fun getFluxAffinityFromSourceAtLocation(source: AuraSource, pos: Vec3): Float = source.fluxAffinity/((pos.distanceToSqr(source.pos) * AURA_DECAY_COEFFICIENT) + 1).toFloat()

fun getAuraFromSourceAtLocation(source: AuraSource, pos: Vec3): Float = source.vis/((pos.distanceToSqr(source.pos) * AURA_DECAY_COEFFICIENT) + 1).toFloat()
fun getAuraAtLocation(level: Level, pos: Vec3): Float = level.auraSources.map { getAuraFromSourceAtLocation(it, pos) }.sum()
fun drainAuraFromSource(source: AuraSource, amount: Float) {source.vis-=amount}
fun drainAuraAtLocation(level: Level, pos: Vec3, amount: Float){
    val auraStrength = getAuraAtLocation(level, pos)
    level.auraSources.forEach {
        drainAuraFromSource(it,amount*getAuraFromSourceAtLocation(it, pos)/auraStrength)
    }
}


fun getNearestAuraSourceInRange(level: Level, pos: Vec3, range: Double): AuraSource? {
    //TODO: Add a way for blocks to be usable AuraSources as well.
    val nodes: List<Entity> = level.getEntities(null, AABB.ofSize(pos, 2*range, 2*range, 2*range)) { it is AuraSource }
    val node = nodes.minByOrNull { it.distanceToSqr(pos) }
    return node as? AuraSource
}