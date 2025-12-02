package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.generator.VisGeneratorBlockEntity
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer

class VisGeneratorRenderer: GeoBlockRenderer<VisGeneratorBlockEntity>(MODEL)  {



    companion object{
        val MODEL = DefaultedBlockGeoModel<VisGeneratorBlockEntity>(LostArcana.id("vis_generator"))
    }
}