package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana.MOD_ID
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

val ITEM_REGISTRY:  DeferredRegister<Item>  = DeferredRegister.create(MOD_ID, Registries.ITEM)

val VIS_CRYSTAL = "vis_crystal" * { VisCrystalItem(Item.Properties()) }
val SALIS_MUNDIS = "salis_mundis" * { Item(Item.Properties()) }

val WAND_ITEM = "wand" * { WandItem(Item.Properties().stacksTo(1).fireResistant(), 100f) }
val IRON_WAND_CAP = "iron_wand_cap" * { Item(Item.Properties()) }

inline operator fun <reified T: Item> String.times(noinline itemSupplier: ()->T): RegistrySupplier<T> =
    ITEM_REGISTRY.register(this, itemSupplier)