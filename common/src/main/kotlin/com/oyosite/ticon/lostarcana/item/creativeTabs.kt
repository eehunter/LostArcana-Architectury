package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.util.thaumonomiconStack
import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack

val TABS_REGISTRY = DeferredRegister.create(LostArcana.MOD_ID, Registries.CREATIVE_MODE_TAB)

val TAB = TABS_REGISTRY.register("lostarcana"){
    CreativeTabRegistry.create(
        Component.translatable("category.lostarcana")
    ) { thaumonomiconStack }
}