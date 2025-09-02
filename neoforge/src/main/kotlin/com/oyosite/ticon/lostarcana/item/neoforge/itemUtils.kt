package com.oyosite.ticon.lostarcana.item.neoforge

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult

fun emptyContents(
    player: Player?,
    level: Level,
    pos: BlockPos,
    hitResult: BlockHitResult?,
    container: ItemStack?,
) {
    if(container!=null)level.getBlockEntity(pos)?.applyComponentsFromItemStack(container)
}