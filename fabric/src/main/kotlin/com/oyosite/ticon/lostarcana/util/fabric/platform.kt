@file:JvmName("PlatformKtImpl")
package com.oyosite.ticon.lostarcana.util.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffect
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffectType
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.Container
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

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