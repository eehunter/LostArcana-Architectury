@file:JvmName("PlatformKtImpl")
package com.oyosite.ticon.lostarcana.util.neoforge

import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import com.mojang.datafixers.types.Type
import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.IItemHandler
import java.util.function.Supplier


fun <T: AbstractContainerMenu> platformRegisterMenuScreen(name: String, menuScreen: MenuType<T>): Holder<MenuType<T>> =
    LostArcanaNeoForge.NEOFORGE_MENU_SCREENS.register(name, Supplier{menuScreen}) as Holder<MenuType<T>>


operator fun <T: Recipe<*>> RecipeType<T>.invoke(name: String): Unit{
    LostArcanaNeoForge.NEOFORGE_RECIPE_TYPES.register(name, Supplier{this})
}

operator fun <T: Recipe<*>> RecipeSerializer<T>.invoke(name: String): Unit{
    LostArcanaNeoForge.NEOFORGE_RECIPE_SERIALIZERS.register(name, Supplier{this})
}

fun platformGetInventoryContentsIfPresent(level: Level, pos: BlockPos): List<ItemStack>{
    val items = mutableListOf<ItemStack>()
    val itemHandler: IItemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null)?:return items
    for(i in 0 until itemHandler.slots) itemHandler.getStackInSlot(i).also(items::add)
    return items
}

@Suppress("UNCHECKED_CAST")
fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> =
    LostArcanaNeoForge.NEOFORGE_BLOCK_ENTITY_TYPES.register<BlockEntityType<T>>(name, Supplier{builder().build(null as Type<*>?)}) as Holder<BlockEntityType<T>>