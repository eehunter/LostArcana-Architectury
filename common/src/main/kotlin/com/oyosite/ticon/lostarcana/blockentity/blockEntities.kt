package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.block.MAGIC_BRICKS
import com.oyosite.ticon.lostarcana.unaryPlus
import com.oyosite.ticon.lostarcana.util.platformRegisterBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

val MAGIC_BRICKS_BLOCK_ENTITY = "magic_bricks"(::MagicBricksBlockEntity, MAGIC_BRICKS)


operator fun <T: BlockEntity, R: BlockEntityType<T>> String.invoke(blockEntityFactory: (BlockPos, BlockState)->T, vararg blocks: Holder<out Block>): Holder<BlockEntityType<T>> =
    platformRegisterBlockEntity(this) { BlockEntityType.Builder.of(blockEntityFactory, *blocks.map(Holder<out Block>::value).toTypedArray()) }