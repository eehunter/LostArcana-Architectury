package com.oyosite.ticon.lostarcana.block.scrubber

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class ScrubberBaseBlock(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = ScrubberBaseBlockEntity(blockPos, blockState)

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED
}