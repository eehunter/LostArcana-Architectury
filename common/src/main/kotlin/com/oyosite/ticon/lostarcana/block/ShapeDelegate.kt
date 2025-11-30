package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.BlockPos
import net.minecraft.world.phys.shapes.VoxelShape

interface ShapeDelegate {
    fun shape(pos: BlockPos, controllerPos: BlockPos): VoxelShape
}