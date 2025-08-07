package com.oyosite.ticon.lostarcana.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState

class VisLight(properties: Properties) : Block(properties) {
    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.INVISIBLE
}