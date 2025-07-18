package com.oyosite.ticon.lostarcana.fabric.client

import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry

class LostArcanaFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ColorProviderRegistry.ITEM.register(LostArcanaClient.VIS_CRYSTAL_ITEM_COLOR, VIS_CRYSTAL.get())
    }
}
