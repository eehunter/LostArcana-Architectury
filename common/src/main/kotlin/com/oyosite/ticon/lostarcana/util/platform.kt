package com.oyosite.ticon.lostarcana.util

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffect
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffectType
import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.loot.functions.LootItemFunction
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType
import net.minecraft.world.phys.BlockHitResult


@ExpectPlatform
fun <U: ParticleOptions, T: ParticleType<U>>platformRegisterParticleType(id: Identifier, particle: T): Holder<T> = throw AssertionError("No platform implementation")

@ExpectPlatform
fun platformCreateAspectRegistry(): Registry<Aspect> = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun handleTankBucketInteraction(
    itemStack: ItemStack,
    blockState: BlockState,
    level: Level,
    blockPos: BlockPos,
    player: Player,
    interactionHand: InteractionHand,
    blockHitResult: BlockHitResult
): ItemInteractionResult = throw AssertionError("No platform implementation.")

@ExpectPlatform
operator fun <T: LootItemFunction> LootItemFunctionType<T>.invoke(name: String): Unit = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun platformCreateCastingFocusEffectTypeRegistry(): Registry<CastingFocusEffectType<*>> = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun <E: CastingFocusEffect, T: CastingFocusEffectType<E>> platformRegisterCastingFocusEffectType(name: String, effectType: ()->T): Holder<T> = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun <T: AbstractContainerMenu> platformRegisterMenuScreen(name: String, menuScreen: MenuType<T>): Holder<MenuType<T>> = throw AssertionError("No platform implementation.")

@ExpectPlatform
operator fun <T: Recipe<*>> RecipeType<T>.invoke(name: String): Unit = throw AssertionError("No platform implementation.")

@ExpectPlatform
operator fun <T: Recipe<*>> RecipeSerializer<T>.invoke(name: String): Unit = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun platformGetInventoryContentsIfPresent(level: Level, pos: BlockPos): List<ItemStack> = throw AssertionError("No platform implementation.")

@ExpectPlatform
fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> = throw AssertionError("No platform implementation.")