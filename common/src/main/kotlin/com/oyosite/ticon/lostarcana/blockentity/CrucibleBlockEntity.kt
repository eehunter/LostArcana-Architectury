package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class CrucibleBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(CRUCIBLE_BLOCK_ENTITY.value(), blockPos, blockState) {
    var fluidAmount: Long = 900


    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        fluidAmount = compoundTag.getLong("water")
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        compoundTag.putLong("water", fluidAmount)
    }
}