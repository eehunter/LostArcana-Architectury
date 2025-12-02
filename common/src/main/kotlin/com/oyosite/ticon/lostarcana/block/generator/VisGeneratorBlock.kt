package com.oyosite.ticon.lostarcana.block.generator

import com.oyosite.ticon.lostarcana.blockentity.VIS_GENERATOR_BLOCK_ENTITY
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class VisGeneratorBlock(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = VisGeneratorBlockEntity(blockPos, blockState)

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED

    override fun <T : BlockEntity> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (blockEntityType == VIS_GENERATOR_BLOCK_ENTITY.value()) VisGeneratorBlockEntity as BlockEntityTicker<T> else null
    }

}