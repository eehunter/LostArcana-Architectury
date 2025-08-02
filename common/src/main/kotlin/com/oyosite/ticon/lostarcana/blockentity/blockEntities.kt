package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.block.ARCANE_COLUMN
import com.oyosite.ticon.lostarcana.block.ARCANE_WORKBENCH
import com.oyosite.ticon.lostarcana.block.MAGIC_BRICKS
import com.oyosite.ticon.lostarcana.block.MULTIBLOCK_PLACEHOLDER
import com.oyosite.ticon.lostarcana.block.RECHARGE_PEDESTAL
import com.oyosite.ticon.lostarcana.util.platformRegisterBlockEntity
import com.oyosite.ticon.lostarcana.util.platformRegisterMenuScreen
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.world.flag.FeatureFlagSet
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

val MAGIC_BRICKS_BLOCK_ENTITY = "magic_bricks"(::MagicBricksBlockEntity, MAGIC_BRICKS)
val ARCANE_COLUMN_BLOCK_ENTITY = "arcane_column"(::ArcaneColumnBlockEntity, ARCANE_COLUMN)

val PLACEHOLDER_BLOCK_ENTITY = "multiblock_placeholder"(::PlaceholderBlockEntity, MULTIBLOCK_PLACEHOLDER)

val ARCANE_WORKBENCH_BLOCK_ENTITY = "arcane_workbench"(::ArcaneWorkbenchBlockEntity, ARCANE_WORKBENCH)
val ARCANE_WORKBENCH_MENU_SCREEN = "arcane_workbench"(::ArcaneWorkbenchMenu, FeatureFlagSet.of())

val RECHARGE_PEDESTAL_BLOCK_ENTITY = "recharge_pedestal"(::RechargePedestalBlockEntity, RECHARGE_PEDESTAL)

operator fun <T: BlockEntity, R: BlockEntityType<T>> String.invoke(blockEntityFactory: (BlockPos, BlockState)->T, vararg blocks: Holder<out Block>): Holder<BlockEntityType<T>> =
    platformRegisterBlockEntity(this) { BlockEntityType.Builder.of(blockEntityFactory, *blocks.map(Holder<out Block>::value).toTypedArray()) }

operator fun <T: AbstractContainerMenu> String.invoke(supplier: MenuType.MenuSupplier<T>, featureFlagSet: FeatureFlagSet) = platformRegisterMenuScreen(this, MenuType(supplier, featureFlagSet))