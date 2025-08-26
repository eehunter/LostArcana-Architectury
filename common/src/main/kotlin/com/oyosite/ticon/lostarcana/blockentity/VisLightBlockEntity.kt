package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.util.ColorableBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class VisLightBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(VIS_LIGHT_BLOCK_ENTITY.value(), blockPos, blockState), ColorableBlockEntity {
    override var color = 0xFFFFFFFFu

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        val c = compoundTag.getInt("color")
        color = if(c==0) 0xFFFFFFFFu else c.toUInt()
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        compoundTag.putInt("color", color.toInt())
    }


}