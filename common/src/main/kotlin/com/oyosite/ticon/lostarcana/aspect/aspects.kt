package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import dev.architectury.injectables.annotations.ExpectPlatform

val ALL_ASPECTS = mutableListOf<Pair<String, Aspect>>()

val AER = aspect("aer", 0xf6f775u)
val IGNIS = aspect("ignis", 0xff6608u)
val PERDITIO = aspect("perditio", 0x4d5049u)
val TERRA = aspect("terra", 0x66ee08u)
val AQUA = aspect("aqua", 0x4ce6ffu)
val ORDO = aspect("ordo", 0xd8d6fbu)

val PRIMAL_ASPECTS = listOf(AER, IGNIS, PERDITIO, TERRA, AQUA, ORDO)


@ExpectPlatform
fun <T: Aspect> registerAspect(name: String, aspect: T): T = throw AssertionError()

fun aspect(id: String, color: UInt, translationKey: String? = null) =
    Aspect(LostArcana.id(id), color, translationKey ?: "aspects.${LostArcana.MOD_ID}.${id}.name").also{ registerAspect(id, it) }.also { ALL_ASPECTS.add(id to it) }