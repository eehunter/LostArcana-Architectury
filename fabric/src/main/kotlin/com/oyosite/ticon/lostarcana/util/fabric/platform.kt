@file:JvmName("PlatformKtImpl")
package com.oyosite.ticon.lostarcana.util.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

fun platformGetInventoryContentsIfPresent(level: Level, pos: BlockPos): List<ItemStack>{
    val items = mutableListOf<ItemStack>()
    val container = (level.getBlockEntity(pos) as? Container)
    val size = container?.containerSize?:0
    for(i in 0 until size)container?.getItem(i)?.also(items::add)
    return items
}

fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> =
    Registry.registerForHolder(BuiltInRegistries.BLOCK_ENTITY_TYPE, LostArcana.id(name), builder().build(null)) as Holder<BlockEntityType<T>>