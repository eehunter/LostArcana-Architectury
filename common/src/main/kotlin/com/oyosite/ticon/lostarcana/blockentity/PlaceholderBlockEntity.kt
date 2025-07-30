package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PlaceholderBlockEntity(pos: BlockPos, state: BlockState): BlockEntity(PLACEHOLDER_BLOCK_ENTITY.value(), pos, state) {

    var linkedBlock: BlockPos = pos
        set(value) {
            if (field!=value) setChanged()
            field = value
        }


    override fun loadAdditional(tag: CompoundTag, provider: HolderLookup.Provider) {
        linkedBlock = BlockPos.of(tag.getLong("linkPos"))
    }

    override fun saveAdditional(tag: CompoundTag, provider: HolderLookup.Provider) {
        tag.putLong("linkPos", linkedBlock.asLong())
    }

}