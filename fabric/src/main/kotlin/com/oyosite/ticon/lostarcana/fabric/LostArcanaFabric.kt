package com.oyosite.ticon.lostarcana.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.LostArcana.init
import com.oyosite.ticon.lostarcana.aspect.AspectStack
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.item.ASPECTS_COMPONENT
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import dev.architectury.registry.registries.DeferredRegister
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries

class LostArcanaFabric : ModInitializer {
    val DATA_COMPONENT_REGISTRAR: DeferredRegister<DataComponentType<*>> = DeferredRegister.create(LostArcana.MOD_ID, Registries.DATA_COMPONENT_TYPE)

    override fun onInitialize() {
        print("Hello world Fabric")
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.

        AspectRegistry.platform_aspect_registry = FabricRegistryBuilder.createSimple(AspectRegistry.ASPECT_REGISTRY_KEY).attribute(
            RegistryAttribute.SYNCED).buildAndRegister()

        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("aspect")) { ASPECT_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("aspects")) { ASPECTS_COMPONENT }
        //Registry.register(Registries.DATA_COMPONENT_TYPE, "${LostArcana.MOD_ID}:aspect", ASPECT_COMPONENT)
        //Registry.register(Registries.DATA_COMPONENT_TYPE, LostArcana.id("aspects"), ASPECTS_COMPONENT)

        DATA_COMPONENT_REGISTRAR.register()
        init()
    }
}
