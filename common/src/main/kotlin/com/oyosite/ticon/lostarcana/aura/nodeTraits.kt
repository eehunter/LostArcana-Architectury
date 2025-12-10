package com.oyosite.ticon.lostarcana.aura

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.util.platformCreateSyncedRegistry
import com.oyosite.ticon.lostarcana.util.platformRegisterNodeTrait
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

val NODE_TRAIT_REGISTRY: ResourceKey<Registry<AuraNodeTrait>> = ResourceKey.createRegistryKey(LostArcana.id("node_traits"))

val NODE_TRAIT_REGISTRY_INTERNAL = platformCreateSyncedRegistry(NODE_TRAIT_REGISTRY)

fun registerBuiltinNodeTraits(){
    "pure"(PureNodeTrait)
    "tainted"(TaintedNodeTrait)
}


operator fun <T: AuraNodeTrait> String.invoke(trait: T) = platformRegisterNodeTrait(this, trait)