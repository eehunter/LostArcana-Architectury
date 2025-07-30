package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.PLACEHOLDER_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.PlaceholderBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import kotlin.jvm.optionals.getOrNull

class MultiblockPlaceholder(properties: Properties) : Block(properties), EntityBlock {
    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity =
        PlaceholderBlockEntity(blockPos, blockState)

    override fun onRemove(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        //println("Removing placeholder block")
        val be = level.getBlockEntity(blockPos, PLACEHOLDER_BLOCK_ENTITY.value()).getOrNull()?:return
        //println("Obtained placeholder BlockEntity")
        //val controllerBE = level.getBlockEntity(be.linkedBlock)
        //println(controllerBE)
        val controller = level.getBlockState(be.linkedBlock).block as? MultiblockController
        println(controller)
        controller?.deconstruct(level, be.linkedBlock, blockPos, bl)

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
        val be = level.getBlockEntity(blockPos, PLACEHOLDER_BLOCK_ENTITY.value()).getOrNull()?:return
        val linkedState = level.getBlockState(be.linkedBlock)
        val linkedEntity = level.getBlockEntity(be.linkedBlock)
        linkedState.block.playerDestroy(level, player, blockPos, linkedState, linkedEntity, itemStack)
    }

    /*override fun destroy(levelAccessor: LevelAccessor, blockPos: BlockPos, blockState: BlockState) {
        val be = levelAccessor.getBlockEntity(blockPos, PLACEHOLDER_BLOCK_ENTITY.value()).getOrNull()?:return
        val controller = levelAccessor.getBlockEntity(be.linkedBlock) as? MultiblockController ?: return
        controller.deconstruct(levelAccessor, be.linkedBlock, blockPos)

    }*/

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.INVISIBLE
}