package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.scrubber.ScrubberBaseBlockEntity
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer

class ScrubberRenderer: GeoBlockRenderer<ScrubberBaseBlockEntity>(MODEL) {

    
    companion object{
        val MODEL = DefaultedBlockGeoModel<ScrubberBaseBlockEntity>(LostArcana.id("flux_scrubber"))
    }
}