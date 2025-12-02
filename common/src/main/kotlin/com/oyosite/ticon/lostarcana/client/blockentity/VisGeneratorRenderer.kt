package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.generator.VisGeneratorBlockEntity
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer

class VisGeneratorRenderer: GeoBlockRenderer<VisGeneratorBlockEntity>(MODEL)  {
    init {
        addRenderLayer(AutoGlowingGeoLayer(this))
    }


    companion object{
        val MODEL = DefaultedBlockGeoModel<VisGeneratorBlockEntity>(LostArcana.id("vis_generator"))
    }
}