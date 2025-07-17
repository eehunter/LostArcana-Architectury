package com.oyosite.ticon.lostarcana.neoforge

import com.oyosite.ticon.lostarcana.LostArcana
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent


@EventBusSubscriber(modid = LostArcana.MOD_ID)
class LostArcanaNeoForgeKt(modEventBus: IEventBus) {
    init {
        print("Hello world NeoForge")
        //KotlinModLoadingContext.get().getKEventBus()
        LostArcana.init()
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent){
    }
}
