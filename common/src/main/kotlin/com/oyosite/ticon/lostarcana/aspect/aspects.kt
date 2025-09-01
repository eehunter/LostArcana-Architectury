package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.util.platformCreateAspectRegistry
import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

val ALL_ASPECTS = mutableListOf<Pair<String, Aspect>>()

val AER = aspect("aer", 0xf6f775u)
val IGNIS = aspect("ignis", 0xff6608u)
val PERDITIO = aspect("perditio", 0x4d5049u)
val TERRA = aspect("terra", 0x66ee08u)
val AQUA = aspect("aqua", 0x4ce6ffu)
val ORDO = aspect("ordo", 0xd8d6fbu)

val PRIMAL_ASPECTS = listOf(AER, IGNIS, PERDITIO, TERRA, AQUA, ORDO)

fun registerBuiltinAspects(){
    ALL_ASPECTS.forEach { registerAspect(it.first, it.second) }
}

@ExpectPlatform
fun <T: Aspect> registerAspect(name: String, aspect: T): T = throw AssertionError()

fun aspect(id: String, color: UInt, translationKey: String? = null) =
    Aspect(LostArcana.id(id), color, translationKey ?: "aspects.${LostArcana.MOD_ID}.${id}.name")
        //.also{ registerAspect(id, it) }
        //.also { AspectRegistry.ASPECTS.register(id) { it } }
        .also { ALL_ASPECTS.add(id to it) }

val ASPECT_REGISTRY_KEY: ResourceKey<Registry<Aspect>> = ResourceKey.createRegistryKey(LostArcana.id("aspects"))
val ASPECT_REGISTRY: Registry<Aspect> = platformCreateAspectRegistry()