package com.oyosite.ticon.lostarcana.neoforge;

import com.oyosite.ticon.lostarcana.LostArcana;
import com.oyosite.ticon.lostarcana.aspect.Aspect;
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

//@Mod(LostArcana.MOD_ID)
//@EventBusSubscriber(modid = LostArcana.MOD_ID)
public class LostArcanaNeoForge {
    public LostArcanaNeoForge(IEventBus modEventBus){
        //System.out.println("Hello world NeoForge");
        new LostArcanaNeoForgeKotlin(modEventBus);
    }

    @SubscribeEvent
    public static void registerRegistriesEvent(NewRegistryEvent event){
        AspectRegistry.INSTANCE.setPlatform_aspect_registry(new RegistryBuilder<>(AspectRegistry.INSTANCE.getASPECT_REGISTRY_KEY()).sync(true).defaultKey(
                LostArcana.INSTANCE.id("none")).create());
        event.register(AspectRegistry.INSTANCE.getASPECT_REGISTRY());
        AspectRegistry.INSTANCE.getASPECTS().register();
    }
}
