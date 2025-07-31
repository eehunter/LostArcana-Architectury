package com.oyosite.ticon.lostarcana.util

import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import java.lang.AssertionError


@ExpectPlatform
operator fun <T: Recipe<*>> RecipeType<T>.invoke(name: String): Unit = throw AssertionError("No platform implementation.")

@ExpectPlatform
operator fun <T: Recipe<*>> RecipeSerializer<T>.invoke(name: String): Unit = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun platformGetInventoryContentsIfPresent(level: Level, pos: BlockPos): List<ItemStack> = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> = throw AssertionError("No platform implementation.")