package com.oyosite.ticon.lostarcana.block.scrubber

import com.oyosite.ticon.lostarcana.blockentity.FLUX_SCRUBBER_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.item.DEFLUXER_PROPERTIES
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class ScrubberBaseBlock(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = ScrubberBaseBlockEntity(blockPos, blockState)

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED

    override fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult
    ): ItemInteractionResult {
        val be = (level.getBlockEntity(blockPos) as? ScrubberBaseBlockEntity) ?: return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        val i = blockHitResult.direction.ordinal - 2
        if (i < 0 || !be.items[i].isEmpty) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        if(itemStack.components.get(DEFLUXER_PROPERTIES)==null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        be.items[i] = itemStack.copyWithCount(1)
        itemStack.shrink(1)
        be.onUpdateDefluxers(i)
        be.setChanged()
        return ItemInteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun useWithoutItem(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        if(!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty) return InteractionResult.PASS
        val be = (level.getBlockEntity(blockPos) as? ScrubberBaseBlockEntity) ?: return InteractionResult.PASS
        val i = blockHitResult.direction.ordinal - 2
        if (i < 0 || be.items[i].isEmpty) return InteractionResult.PASS
        player.setItemInHand(InteractionHand.MAIN_HAND, be.items[i])
        be.items[i] = ItemStack.EMPTY
        be.onUpdateDefluxers(i)
        be.setChanged()

        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (blockEntityType == FLUX_SCRUBBER_BLOCK_ENTITY.value()) ScrubberBaseBlockEntity as BlockEntityTicker<T> else null
    }
}