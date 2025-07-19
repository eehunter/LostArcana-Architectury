package com.oyosite.ticon.lostarcana

import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Holder
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.player.Player

inline operator fun <reified T> RegistrySupplier<T>.unaryPlus(): T = get()


@Suppress("unchecked_cast")
val Player.canSeeAuraNode: Boolean get() = (getAttribute(ARCANE_SIGHT as Holder<Attribute>)?.value ?: .0) >= 1.0
