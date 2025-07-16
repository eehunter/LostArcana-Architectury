package com.oyosite.ticon.lostarcana.neoforge

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.BLOCK_REGISTRY
import dev.architectury.neoforge.ArchitecturyNeoForge
import dev.architectury.platform.hooks.EventBusesHooks
import net.neoforged.bus.EventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.kotlinforforge.neoforge.KotlinModContainer
import thedarkcolour.kotlinforforge.neoforge.KotlinModLoadingContext

@Mod(LostArcana.MOD_ID)
@EventBusSubscriber(modid = LostArcana.MOD_ID)
object LostArcanaNeoForge {
    init {
        print("Hello world NeoForge")
        //KotlinModLoadingContext.get().getKEventBus()
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent){
        LostArcana.init()
    }
}
