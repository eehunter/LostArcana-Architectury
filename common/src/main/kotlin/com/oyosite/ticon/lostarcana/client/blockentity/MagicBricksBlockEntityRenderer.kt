package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.MagicBricksBlockEntity
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer

class MagicBricksBlockEntityRenderer: GeoBlockRenderer<MagicBricksBlockEntity>(MODEL) {


    companion object{
        val MODEL = DefaultedBlockGeoModel<MagicBricksBlockEntity>(LostArcana.id("magic_bricks"))
    }

}