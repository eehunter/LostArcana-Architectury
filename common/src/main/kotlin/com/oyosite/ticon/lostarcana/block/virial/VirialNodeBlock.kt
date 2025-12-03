package com.oyosite.ticon.lostarcana.block.virial

import com.oyosite.ticon.lostarcana.block.generator.VisGeneratorBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.VIRIAL_NODE_BLOCK_ENTITY
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

class VirialNodeBlock(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = VirialNodeBlockEntity(blockPos, blockState)

    override fun <T : BlockEntity> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (blockEntityType == VIRIAL_NODE_BLOCK_ENTITY.value()) VirialNodeBlockEntity as BlockEntityTicker<T> else null
    }

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED
}