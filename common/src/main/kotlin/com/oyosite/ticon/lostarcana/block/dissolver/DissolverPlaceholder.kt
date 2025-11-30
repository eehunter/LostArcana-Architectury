package com.oyosite.ticon.lostarcana.block.dissolver

import com.oyosite.ticon.lostarcana.block.MultiblockPlaceholder
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class DissolverPlaceholder(properties: Properties) : MultiblockPlaceholder(properties) {

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return shape
    }

    companion object{
        val shape: VoxelShape = box(4.0,0.0,4.0,12.0,8.0,12.0)
    }
}