@file:JvmName("PlatformKtImpl")
package com.oyosite.ticon.lostarcana.util.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> =
    Registry.registerForHolder(BuiltInRegistries.BLOCK_ENTITY_TYPE, LostArcana.id(name), builder().build(null)) as Holder<BlockEntityType<T>>