package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.LostArcana.MOD_ID
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

val ITEM_REGISTRY:  DeferredRegister<Item>  = DeferredRegister.create(MOD_ID, Registries.ITEM)




inline operator fun <reified T: Item> String.times(noinline itemSupplier: ()->T): RegistrySupplier<T> =
    ITEM_REGISTRY.register(this, itemSupplier)