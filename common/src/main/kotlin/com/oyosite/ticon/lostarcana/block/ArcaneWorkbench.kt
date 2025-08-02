package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.ArcaneWorkbenchBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.ArcaneWorkbenchMenu
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.stats.Stats
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class ArcaneWorkbench(properties: Properties) : Block(properties), EntityBlock {
    override fun useWithoutItem(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        if(level.isClientSide){
            return InteractionResult.SUCCESS_NO_ITEM_USED
        } else {
            player.openMenu(blockState.getMenuProvider(level, blockPos))
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE)
            return InteractionResult.CONSUME
        }
    }

    override fun getMenuProvider(blockState: BlockState, level: Level, blockPos: BlockPos): MenuProvider? =
        (level.getBlockEntity(blockPos) as? ArcaneWorkbenchBlockEntity)?.let { it::createMenu }?.let{SimpleMenuProvider(it, TITLE)}

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity? =
        ArcaneWorkbenchBlockEntity(blockPos, blockState)

    override fun onRemove(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        Containers.dropContentsOnDestroy(blockState, blockState2, level, blockPos)
        super.onRemove(blockState, level, blockPos, blockState2, bl)
    }

    companion object{
        val TITLE_KEY = "container.lostarcana.arcane_workbench"
        val TITLE: MutableComponent = Component.translatable(TITLE_KEY)
    }
}