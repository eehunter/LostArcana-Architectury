package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class EssentiaSmeltery(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity? {
        TODO("Not yet implemented")
    }
}