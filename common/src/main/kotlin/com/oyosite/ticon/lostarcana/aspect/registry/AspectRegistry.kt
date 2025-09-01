package com.oyosite.ticon.lostarcana.aspect.registry

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.ASPECT_REGISTRY_KEY
import com.oyosite.ticon.lostarcana.aspect.Aspect
import dev.architectury.registry.registries.DeferredRegister

object AspectRegistry {

    val ASPECTS: DeferredRegister<Aspect> = DeferredRegister.create(LostArcana.MOD_ID, ASPECT_REGISTRY_KEY)
}