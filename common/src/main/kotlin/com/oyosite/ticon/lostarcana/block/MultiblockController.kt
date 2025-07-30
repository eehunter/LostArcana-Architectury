package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.storage.loot.LootTable

interface MultiblockController {
    fun deconstruct(levelAccessor: LevelAccessor, blockPos: BlockPos, brokenFrom: BlockPos?, bl: Boolean)
    fun construct(context: UseOnContext): Boolean
}