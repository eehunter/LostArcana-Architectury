package com.oyosite.ticon.lostarcana.neoforge;

import com.oyosite.ticon.lostarcana.LostArcana;
import com.oyosite.ticon.lostarcana.LostArcanaNeoForgeKt;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(LostArcana.MOD_ID)
public class LostArcanaNeoForge {
    public LostArcanaNeoForge(IEventBus modEventBus){
        //System.out.println("Hello world NeoForge");
        LostArcanaNeoForgeKt.INSTANCE.initialize(modEventBus);
    }
}
