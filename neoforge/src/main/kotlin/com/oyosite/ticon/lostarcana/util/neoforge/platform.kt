@file:JvmName("PlatformKtImpl")
package com.oyosite.ticon.lostarcana.util.neoforge

import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForgeKotlin
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import com.mojang.datafixers.types.Type
import net.minecraft.core.Holder
import java.util.function.Supplier


@Suppress("UNCHECKED_CAST")
fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> =
    LostArcanaNeoForgeKotlin.NEOFORGE_BLOCK_ENTITY_TYPES.register<BlockEntityType<T>>(name, Supplier{builder().build(null as Type<*>?)}) as Holder<BlockEntityType<T>>