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
val CRUDE_CASTER_GAUNTLET = "crude_caster_gauntlet" * { CasterGauntlet(Item.Properties().stacksTo(1)) }
val IRON_WAND_CAP = "iron_wand_cap" * { Item(Item.Properties()) }

val THAUMOMETER = "thaumometer" * { ThaumometerItem(Item.Properties().stacksTo(1)) }

inline operator fun <reified T: Item> String.times(noinline itemSupplier: ()->T): RegistrySupplier<T> =
    ITEM_REGISTRY.register(this, itemSupplier)