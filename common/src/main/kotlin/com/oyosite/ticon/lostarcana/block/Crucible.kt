package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class Crucible(properties: Properties) : Block(properties) {


    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape? {
        return SHAPE
    }

    override fun getInteractionShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos
    ): VoxelShape? {
        return INSIDE
    }



    companion object{
        val INSIDE = box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0)
        val SHAPE = Shapes.join(
            Shapes.block(),
            Shapes.or(
                box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
                *arrayOf<VoxelShape>(
                    box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
                    box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
                    INSIDE
                )
            ),
            BooleanOp.ONLY_FIRST
        )
    }
}