package com.oyosite.ticon.lostarcana.block.dissolver

import com.oyosite.ticon.lostarcana.blockentity.DISSOLVER_BLOCK_ENTITY
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class DissolverBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(DISSOLVER_BLOCK_ENTITY.value(), blockPos, blockState) {
}