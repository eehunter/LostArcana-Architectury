package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.ArcaneColumnBlockEntity
import net.minecraft.world.phys.Vec3
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer

class ArcaneColumnRenderer: GeoBlockRenderer<ArcaneColumnBlockEntity>(MODEL) {
    companion object{
        val MODEL = DefaultedBlockGeoModel<ArcaneColumnBlockEntity>(LostArcana.id("arcane_column"))
    }

    override fun shouldRenderOffScreen(blockEntity: ArcaneColumnBlockEntity): Boolean {
        return true
    }
}