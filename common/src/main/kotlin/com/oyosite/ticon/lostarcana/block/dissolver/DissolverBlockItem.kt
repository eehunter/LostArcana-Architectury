package com.oyosite.ticon.lostarcana.block.dissolver

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class DissolverBlockItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun canPlace(blockPlaceContext: BlockPlaceContext, blockState: BlockState): Boolean {
        if (!super.canPlace(blockPlaceContext, blockState)) return false

        return blockPlaceContext.level.getBlockState(blockPlaceContext.clickedPos.above()).canBeReplaced()
    }
}