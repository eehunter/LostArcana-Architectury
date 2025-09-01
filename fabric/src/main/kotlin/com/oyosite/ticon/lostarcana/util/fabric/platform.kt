@file:JvmName("PlatformKtImpl")
package com.oyosite.ticon.lostarcana.util.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.ASPECT_REGISTRY_KEY
import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import com.oyosite.ticon.lostarcana.fabric.block.WardedJarFluidStorage
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffect
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffectType
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.Container
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

fun platformCreateAspectRegistry(): Registry<Aspect> = FabricRegistryBuilder.createSimple(ASPECT_REGISTRY_KEY)
    .attribute(RegistryAttribute.SYNCED)
    .buildAndRegister()

fun handleTankBucketInteraction(
    itemStack: ItemStack,
    blockState: BlockState,
    level: Level,
    blockPos: BlockPos,
    player: Player,
    interactionHand: InteractionHand,
    blockHitResult: BlockHitResult
): ItemInteractionResult{
    return if(FluidStorageUtil.interactWithFluidStorage(
        ((level.getBlockEntity(blockPos) as? WardedJarBlockEntity)?:return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION).let(::WardedJarFluidStorage),
        player,
        interactionHand
    )) ItemInteractionResult.SUCCESS else ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
}

operator fun <T: LootItemFunction> LootItemFunctionType<T>.invoke(name: String): Unit{
    Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, LostArcana.id(name), this)
}

fun platformCreateCastingFocusEffectTypeRegistry(): Registry<CastingFocusEffectType<*>> =
    FabricRegistryBuilder.createSimple(CastingFocusEffectType.REGISTRY_KEY)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister()

fun <E: CastingFocusEffect, T: CastingFocusEffectType<E>> platformRegisterCastingFocusEffectType(name: String, effectType: ()->T): Holder<T> =
    Registry.registerForHolder(CastingFocusEffectType.REGISTRY, LostArcana.id(name), effectType()) as Holder<T>

fun <T: AbstractContainerMenu> platformRegisterMenuScreen(name: String, menuScreen: MenuType<T>): Holder<MenuType<T>>{
    return Registry.registerForHolder(BuiltInRegistries.MENU, LostArcana.id(name), menuScreen) as Holder<MenuType<T>>
}

operator fun <T: Recipe<*>> RecipeType<T>.invoke(name: String): Unit{
    Registry.register(BuiltInRegistries.RECIPE_TYPE, LostArcana.id(name), this)
}

operator fun <T: Recipe<*>> RecipeSerializer<T>.invoke(name: String): Unit{
    Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, LostArcana.id(name), this)
}

fun platformGetInventoryContentsIfPresent(level: Level, pos: BlockPos): List<ItemStack>{
    val items = mutableListOf<ItemStack>()
    val container = (level.getBlockEntity(pos) as? Container)
    val size = container?.containerSize?:0
    for(i in 0 until size)container?.getItem(i)?.also(items::add)
    return items
}

fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> =
    Registry.registerForHolder(BuiltInRegistries.BLOCK_ENTITY_TYPE, LostArcana.id(name), builder().build(null)) as Holder<BlockEntityType<T>>