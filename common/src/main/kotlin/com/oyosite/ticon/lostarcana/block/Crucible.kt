package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.CRUCIBLE_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.CrucibleBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class Crucible(properties: Properties) : Block(properties), EntityBlock {


    override fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult
    ): ItemInteractionResult {


        return (level.getBlockEntity(blockPos) as? CrucibleBlockEntity)?.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult) ?: ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
    }

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

    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = CrucibleBlockEntity(blockPos, blockState)

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T?>
    ): BlockEntityTicker<T?>? {
        return if(blockEntityType == CRUCIBLE_BLOCK_ENTITY.value()) BlockEntityTicker(CrucibleBlockEntity::tick) as BlockEntityTicker<T?> else null
    }

    override fun animateTick(blockState: BlockState, level: Level, blockPos: BlockPos, randomSource: RandomSource) {
        val be = level.getBlockEntity(blockPos) as? CrucibleBlockEntity?: return
        if(be.heatLevel < 300) return
        level.addParticle(ParticleTypes.BUBBLE, blockPos.x + randomSource.nextDouble() * 3/4 + 1.0/8, blockPos.y + (be.fluidAmount / 1000.0) + .01, blockPos.z + randomSource.nextDouble() * 3/4 + 1.0/8, 10.0, .01, 10.0)
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