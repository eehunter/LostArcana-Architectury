package com.oyosite.ticon.lostarcana.neoforge;

import com.oyosite.ticon.lostarcana.LostArcana;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(LostArcana.MOD_ID)
public class LostArcanaNeoForge {
    public LostArcanaNeoForge(IEventBus modEventBus){
        System.out.println("Hello world NeoForge");
        LostArcana.init();
    }
}
