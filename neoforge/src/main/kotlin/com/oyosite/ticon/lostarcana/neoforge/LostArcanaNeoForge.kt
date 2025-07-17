package com.oyosite.ticon.lostarcana.neoforge

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECT_REGISTRY
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECT_REGISTRY_KEY
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.platform_aspect_registry
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.registries.NewRegistryEvent
import net.neoforged.neoforge.registries.RegistryBuilder


@EventBusSubscriber(modid = LostArcana.MOD_ID)
object LostArcanaNeoForgeKt {
    fun initialize(modEventBus: IEventBus) {
        print("Hello world NeoForge")
        //KotlinModLoadingContext.get().getKEventBus()
        platform_aspect_registry = RegistryBuilder(ASPECT_REGISTRY_KEY).sync(true).defaultKey(LostArcana.id("none")).create()
        LostArcana.init()
    }

    @SubscribeEvent // on the mod event bus
    fun registerRegistries(event: NewRegistryEvent) {
        event.register(ASPECT_REGISTRY)
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent){
    }
}
