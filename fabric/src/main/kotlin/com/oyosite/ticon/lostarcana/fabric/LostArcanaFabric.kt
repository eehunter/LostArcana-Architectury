package com.oyosite.ticon.lostarcana.fabric

import com.oyosite.ticon.lostarcana.LostArcana.init
import net.fabricmc.api.ModInitializer

class LostArcanaFabric : ModInitializer {
    override fun onInitialize() {
        print("Hello world Fabric")
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.

        init()
    }
}
