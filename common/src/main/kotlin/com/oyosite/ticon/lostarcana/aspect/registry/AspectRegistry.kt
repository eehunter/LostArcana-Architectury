package com.oyosite.ticon.lostarcana.aspect.registry

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.Aspect
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import org.jetbrains.annotations.ApiStatus

object AspectRegistry {
    val ASPECT_REGISTRY_KEY: ResourceKey<Registry<Aspect>> = ResourceKey.createRegistryKey(LostArcana.id("aspects"))

    @ApiStatus.Internal
    lateinit var platform_aspect_registry: Registry<Aspect>
    val ASPECT_REGISTRY: Registry<Aspect> get() = platform_aspect_registry

    val ASPECTS: DeferredRegister<Aspect> = DeferredRegister.create(LostArcana.MOD_ID, ASPECT_REGISTRY_KEY)
}