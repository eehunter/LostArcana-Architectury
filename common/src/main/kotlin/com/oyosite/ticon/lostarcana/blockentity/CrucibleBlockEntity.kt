package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.aspect.AER
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CrucibleBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(CRUCIBLE_BLOCK_ENTITY.value(), blockPos, blockState) {
    var fluidAmount: Long = 900

    val aspects = mutableMapOf<Aspect, Int>()
    var waterColor = 0

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        fluidAmount = compoundTag.getLong("water")
        val aspectsTag = compoundTag.getCompound("aspects")
        aspects.clear()
        aspectsTag.allKeys.forEach {
            val aspect = AspectRegistry.ASPECT_REGISTRY.get(Identifier.parse(it)) ?: return@forEach println("Aspect $it was not registered, discarding")
            aspects[aspect] = aspectsTag.getInt(it)
        }
        waterColor = 0
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        compoundTag.putLong("water", fluidAmount)
        val aspectsTag = CompoundTag()
        aspects.forEach { (aspect, amt) -> aspectsTag.putInt(AspectRegistry.ASPECT_REGISTRY.getKey(aspect)?.toString() ?: return@forEach println("Aspect $aspect was not registered, discarding") , amt) }
        compoundTag.put("aspects", aspectsTag)
        waterColor = 0
    }
}