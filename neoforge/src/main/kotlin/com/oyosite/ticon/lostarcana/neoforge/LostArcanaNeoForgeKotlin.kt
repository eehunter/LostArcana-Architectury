package com.oyosite.ticon.lostarcana.neoforge

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.LostArcana.id
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECTS
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECT_REGISTRY_KEY
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.platform_aspect_registry
import com.oyosite.ticon.lostarcana.item.ASPECTS_COMPONENT
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import net.minecraft.core.registries.Registries
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NewRegistryEvent
import net.neoforged.neoforge.registries.RegistryBuilder
import java.util.function.Supplier

@Mod(LostArcana.MOD_ID)
class LostArcanaNeoForgeKotlin(modEventBus: IEventBus) {

    val DATA_COMPONENT_REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, LostArcana.MOD_ID)

    init {
        print("Hello world NeoForge")
        //KotlinModLoadingContext.get().getKEventBus()

        //modEventBus.addListener(::registerRegistries)

        NEOFORGE_ASPECTS.register(modEventBus)


        DATA_COMPONENT_REGISTRAR.register("aspect", Supplier { ASPECT_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("aspects", Supplier { ASPECTS_COMPONENT })

        DATA_COMPONENT_REGISTRAR.register(modEventBus)

        LostArcana.init()
    }

    @EventBusSubscriber(modid = LostArcana.MOD_ID)
    companion object {
        val NEOFORGE_ASPECTS = DeferredRegister.create(ASPECT_REGISTRY_KEY, LostArcana.MOD_ID)

        @SubscribeEvent // on the mod event bus
        @JvmStatic
        fun registerRegistries(event: NewRegistryEvent) {
            platform_aspect_registry = RegistryBuilder(ASPECT_REGISTRY_KEY).sync(true).defaultKey(id("none")).create()
            event.register(AspectRegistry.ASPECT_REGISTRY)
        }

        @SubscribeEvent
        @JvmStatic
        fun onCommonSetup(event: FMLCommonSetupEvent) {
        }
    }
}