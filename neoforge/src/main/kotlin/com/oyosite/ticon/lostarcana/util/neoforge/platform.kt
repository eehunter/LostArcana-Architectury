@file:JvmName("PlatformKtImpl")
package com.oyosite.ticon.lostarcana.util.neoforge

import com.mojang.datafixers.types.Type
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffect
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffectType
import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.Registry
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
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.fluids.FluidUtil
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.registries.RegistryBuilder
import java.util.function.Supplier

val platformAspectRegistry: Registry<Aspect> get() = LostArcanaNeoForge.NEOFORGE_ASPECT_REGISTRY

fun handleTankBucketInteraction(
    itemStack: ItemStack,
    blockState: BlockState,
    level: Level,
    blockPos: BlockPos,
    player: Player,
    interactionHand: InteractionHand,
    blockHitResult: BlockHitResult
): ItemInteractionResult {
    //val cap = Capabilities.FluidHandler.ITEM.getCapability(itemStack, null)?:return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
    return if(FluidUtil.interactWithFluidHandler(player, interactionHand, level, blockPos, blockHitResult.direction)) ItemInteractionResult.SUCCESS else ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
}

operator fun <T: LootItemFunction> LootItemFunctionType<T>.invoke(name: String): Unit{
    LostArcanaNeoForge.NEOFORGE_LOOT_FUNCTIONS.register(name) { _ -> this }
}

fun platformCreateCastingFocusEffectTypeRegistry(): Registry<CastingFocusEffectType<*>> =
    RegistryBuilder(CastingFocusEffectType.REGISTRY_KEY).sync(true).defaultKey(LostArcana.id("none")).create()

fun <E: CastingFocusEffect, T: CastingFocusEffectType<E>> platformRegisterCastingFocusEffectType(name: String, effectType: ()->T): Holder<T> =
    LostArcanaNeoForge.NEOFORGE_CASTING_EFFECT_TYPES.register(name, effectType) as Holder<T>

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