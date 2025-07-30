package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.storage.loot.LootTable

interface MultiblockController {
    fun deconstruct(levelAccessor: LevelAccessor, blockPos: BlockPos, brokenFrom: BlockPos?)
    fun construct(context: UseOnContext): Boolean

    fun generateDrops(level: ServerLevel, pos: BlockPos, corePos: BlockPos, player: Player, tool: ItemStack)
    fun getLootTable(level: ServerLevel, pos: BlockPos): ResourceKey<LootTable>
}