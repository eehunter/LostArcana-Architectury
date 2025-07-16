package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.world.item.Item





inline operator fun <reified T: Item> String.invoke(noinline itemSupplier: ()->T): RegistrySupplier<T> =
    LostArcana.ITEM_REGISTRY.register(this, itemSupplier)