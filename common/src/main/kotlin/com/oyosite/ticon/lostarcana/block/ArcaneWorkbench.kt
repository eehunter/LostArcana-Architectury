package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class ArcaneWorkbench(properties: Properties) : Block(properties) {
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

    override fun getMenuProvider(blockState: BlockState, level: Level, blockPos: BlockPos): MenuProvider? {
        return SimpleMenuProvider(TODO(), TITLE)
    }

    companion object{
        val TITLE: MutableComponent = Component.translatable("container.lostarcana.arcane_workbench")
    }
}