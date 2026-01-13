package com.oyosite.ticon.lostarcana.block.dissolver

import com.oyosite.ticon.lostarcana.block.MultiblockPlaceholder
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
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

    override fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult
    ): ItemInteractionResult {
        val be = level.getBlockEntity(blockPos.below()) as? DissolverBlockEntity ?: return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        return be.inventoryHelper.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult)
    }

    companion object{
        val shape: VoxelShape = box(4.0,0.0,4.0,12.0,8.0,12.0)
    }
}