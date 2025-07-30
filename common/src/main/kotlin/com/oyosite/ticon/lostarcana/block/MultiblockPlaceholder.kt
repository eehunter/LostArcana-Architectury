package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.PLACEHOLDER_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.PlaceholderBlockEntity
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.stats.Stats
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Consumer
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
        println("Player breaking linked block. 1")

        val be = blockEntity as? PlaceholderBlockEntity?:return
        val linkedState = level.getBlockState(be.linkedPos)
        val linkedEntity = level.getBlockEntity(be.linkedPos)

        player.awardStat(Stats.BLOCK_MINED.get(linkedState.block))
        player.causeFoodExhaustion(0.005f)

        println("Player breaking linked block. 2")

        println(linkedState)
        val linkedController = be.linkedBlock
        println(linkedController)
        if(level is ServerLevel && linkedController != null) {
            val (x,y,z) = blockPos.center


            println("Player breaking linked block. 3")
            /*level.server.reloadableRegistries().getLootTable(linkedController.getLootTable(level, blockPos)).getRandomItems(
                LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, blockPos.center).withParameter(LootContextParams.TOOL, itemStack).withOptionalParameter(LootContextParams.THIS_ENTITY, player).withOptionalParameter(
                    LootContextParams.BLOCK_ENTITY, level.getBlockEntity(blockPos)).create(LootContextParamSet.builder().build()))?.map { ItemEntity(level, x,y,z, it) }
                ?.forEach(level::addFreshEntity)*/

            linkedController.generateDrops(level, blockPos, be.linkedPos, player, itemStack)

            /*(linkedState.block as? MultiblockController)
                ?.generateDrops(
                level,
                be.linkedBlock,
                be.linkedBlock,
                player,
                itemStack
            )*/
        }
        //dropResourcesHere(linkedState, level, be.linkedBlock, linkedEntity, player, itemStack, blockPos)
    }

    fun dropResourcesHere(blockState: BlockState, level: Level, blockPos: BlockPos, blockEntity: BlockEntity?, entity: Entity?, itemStack: ItemStack, localPos: BlockPos) {
        if (level is ServerLevel) {
            getDrops(blockState, level, blockPos, blockEntity, entity, itemStack).forEach(Consumer { itemStackx: ItemStack -> popResource(level, blockPos, itemStackx) })
            blockState.spawnAfterBreak(level, blockPos, itemStack, true)
        }
    }

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED
}