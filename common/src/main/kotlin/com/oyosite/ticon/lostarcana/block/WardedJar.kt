package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class WardedJar(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = WardedJarBlockEntity(blockPos, blockState)
}