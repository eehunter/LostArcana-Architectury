package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.LevelAccessor

interface MultiblockController {
    fun deconstruct(levelAccessor: LevelAccessor, blockPos: BlockPos, brokenFrom: BlockPos?)
    fun construct(context: UseOnContext): Boolean

    fun generateDrops(level: ServerLevel, pos: BlockPos, corePos: BlockPos, player: Player, tool: ItemStack)
}