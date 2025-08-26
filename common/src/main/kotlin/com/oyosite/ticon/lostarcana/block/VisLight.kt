package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.VisLightBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class VisLight(properties: Properties) : Block(properties), EntityBlock {
    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.INVISIBLE
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = VisLightBlockEntity(blockPos, blockState)


}