package com.oyosite.ticon.lostarcana

import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.item.ASPECTS_COMPONENT
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import net.minecraft.core.registries.Registries
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NewRegistryEvent
import net.neoforged.neoforge.registries.RegistryBuilder
import java.util.function.Supplier

@EventBusSubscriber(modid = LostArcana.MOD_ID)
object LostArcanaNeoForgeKt {
    fun initialize(modEventBus: IEventBus) {
        print("Hello world NeoForge")
        //KotlinModLoadingContext.get().getKEventBus()
        AspectRegistry.platform_aspect_registry = RegistryBuilder(AspectRegistry.ASPECT_REGISTRY_KEY).sync(true).defaultKey(LostArcana.id("none")).create()

        DATA_COMPONENT_REGISTRAR.register("aspect", Supplier { ASPECT_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("aspects", Supplier { ASPECTS_COMPONENT })

        DATA_COMPONENT_REGISTRAR.register(modEventBus)

        LostArcana.init()
    }

    val DATA_COMPONENT_REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, LostArcana.MOD_ID)

    @SubscribeEvent // on the mod event bus
    fun registerRegistries(event: NewRegistryEvent) {
        event.register(AspectRegistry.ASPECT_REGISTRY)
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent){
    }
}