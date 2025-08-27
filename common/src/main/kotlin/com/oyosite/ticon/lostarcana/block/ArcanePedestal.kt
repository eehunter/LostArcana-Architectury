package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.ArcanePedestalBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.stream.Stream

class ArcanePedestal(properties: Properties) : PedestalBlock(properties, { pos, state -> ArcanePedestalBlockEntity(pos, state) }) {

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = SHAPE

    override fun getInteractionShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos
    ): VoxelShape = SHAPE

    companion object{
        val SHAPE = Stream.of(
            box(0.0,0.0,0.0,16.0,4.0,16.0),
            box(4.0,4.0,4.0,12.0,12.0,12.0),
            box(2.0,12.0,2.0,14.0,16.0,14.0)
        ).reduce { v1, v2 -> Shapes.join(v1, v2, BooleanOp.OR) }.get()
    }
}