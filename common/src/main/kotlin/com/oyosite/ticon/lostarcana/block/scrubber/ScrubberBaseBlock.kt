package com.oyosite.ticon.lostarcana.block.scrubber

import com.oyosite.ticon.lostarcana.blockentity.FLUX_SCRUBBER_BLOCK_ENTITY
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class ScrubberBaseBlock(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = ScrubberBaseBlockEntity(blockPos, blockState)

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED

    override fun <T : BlockEntity> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (blockEntityType == FLUX_SCRUBBER_BLOCK_ENTITY.value()) ScrubberBaseBlockEntity as BlockEntityTicker<T> else null
    }
}