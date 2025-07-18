package com.oyosite.ticon.lostarcana.attribute

import com.oyosite.ticon.lostarcana.LostArcana
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute

val ATTRIBUTE_REGISTRY: DeferredRegister<Attribute> = DeferredRegister.create(LostArcana.MOD_ID, Registries.ATTRIBUTE)

val ARCANE_INSIGHT = "arcane_insight" % { RangedAttribute("attribute.name.lostarcana.arcane_insight",.0, .0,10.0) }
val ARCANE_SIGHT = "arcane_sight" % { RangedAttribute("attribute.name.lostarcana.arcane_sight",.0, .0,10.0) }




inline operator fun <reified T: Attribute> String.rem(noinline supplier: ()->T): RegistrySupplier<T> = ATTRIBUTE_REGISTRY.register(this, supplier)
