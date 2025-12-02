package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.scrubber.ScrubberBaseBlockEntity
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer

class ScrubberRenderer: GeoBlockRenderer<ScrubberBaseBlockEntity>(MODEL) {
      init {
          addRenderLayer(AutoGlowingGeoLayer(this))

      }

    companion object{
        val MODEL = DefaultedBlockGeoModel<ScrubberBaseBlockEntity>(LostArcana.id("flux_scrubber"))
    }
}