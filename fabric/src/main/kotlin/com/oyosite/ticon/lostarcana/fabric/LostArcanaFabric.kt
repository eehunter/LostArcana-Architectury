package com.oyosite.ticon.lostarcana.fabric

import com.oyosite.ticon.lostarcana.LostArcana.init
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute

class LostArcanaFabric : ModInitializer {
    override fun onInitialize() {
        print("Hello world Fabric")
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.

        AspectRegistry.platform_aspect_registry = FabricRegistryBuilder.createSimple(AspectRegistry.ASPECT_REGISTRY_KEY).attribute(
            RegistryAttribute.SYNCED).buildAndRegister()
        init()
    }
}
