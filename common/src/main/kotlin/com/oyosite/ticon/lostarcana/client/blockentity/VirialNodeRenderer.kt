package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.virial.VirialNodeBlockEntity
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer

class VirialNodeRenderer: GeoBlockRenderer<VirialNodeBlockEntity>(MODEL)  {
    init {
        addRenderLayer(AutoGlowingGeoLayer(this))
    }

    companion object{
        val MODEL = DefaultedBlockGeoModel<VirialNodeBlockEntity>(LostArcana.id("virial_node"))
    }
}