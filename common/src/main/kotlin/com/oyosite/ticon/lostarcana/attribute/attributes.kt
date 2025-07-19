package com.oyosite.ticon.lostarcana.attribute

import com.oyosite.ticon.lostarcana.LostArcana
import dev.architectury.injectables.annotations.ExpectPlatform
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute

val ATTRIBUTE_REGISTRY: DeferredRegister<Attribute> = DeferredRegister.create(LostArcana.MOD_ID, Registries.ATTRIBUTE)

val ARCANE_INSIGHT: Holder<Attribute> = "arcane_insight" % { RangedAttribute("attribute.name.lostarcana.arcane_insight",.0, .0,10.0).setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE) }
val ARCANE_SIGHT: Holder<Attribute> = "arcane_sight" % { RangedAttribute("attribute.name.lostarcana.arcane_sight",.0, .0,10.0).setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE) }



@ExpectPlatform
fun <T: Attribute> registerAttribute(name: String, supplier: ()->T): Holder<T> = throw AssertionError()

inline operator fun <reified T: Attribute> String.rem(noinline supplier: ()->T): Holder<T> = registerAttribute(this, supplier)
