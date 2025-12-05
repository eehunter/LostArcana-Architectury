package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.PLACEHOLDER_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.PlaceholderBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.stats.Stats
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import kotlin.jvm.optionals.getOrNull

open class MultiblockPlaceholder(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity =
        PlaceholderBlockEntity(blockPos, blockState)

    override fun onRemove(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        val be = level.getBlockEntity(blockPos, PLACEHOLDER_BLOCK_ENTITY.value()).getOrNull()?:return
        val controller = level.getBlockState(be.linkedPos).block as? MultiblockController
        controller?.deconstruct(level, be.linkedPos, blockPos)

        super.onRemove(blockState, level, blockPos, blockState2, bl)
    }

    override fun playerDestroy(
        level: Level,
        player: Player,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BlockEntity?,
        itemStack: ItemStack
    ) {
        val be = blockEntity as? PlaceholderBlockEntity?:return
        val linkedController = be.linkedBlock

        (linkedController as? Block)?.let(Stats.BLOCK_MINED::get)?.let(player::awardStat)
        player.causeFoodExhaustion(0.005f)
        if(level is ServerLevel && linkedController != null) {
            linkedController.generateDrops(level, blockPos, be.linkedPos, player, itemStack)
        }
    }



    /*override fun getInteractionShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos
    ): VoxelShape? {
        (blockGetter.getBlockEntity(blockPos) as? PlaceholderBlockEntity)?.let{
            it.shapeDelegate?.shape(blockPos, it.linkedPos)
        }?.let { return it }
        return super.getInteractionShape(blockState, blockGetter, blockPos)
    }



    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape? {
        (blockGetter.getBlockEntity(blockPos) as? PlaceholderBlockEntity)?.let{
            it.shapeDelegate?.shape(blockPos, it.linkedPos)
        }?.let { return it }
        return super.getShape(blockState, blockGetter, blockPos, collisionContext)
    }*/





    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED


    companion object{
        val lootParamSet = LootContextParamSet.builder().required(LootContextParams.ORIGIN).optional(LootContextParams.TOOL).optional(LootContextParams.THIS_ENTITY).optional(LootContextParams.BLOCK_ENTITY).build()
    }
}