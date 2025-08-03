package com.oyosite.ticon.lostarcana.client.blockentity

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.ArcaneColumnBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.phys.AABB
import software.bernie.geckolib.model.DefaultedBlockGeoModel
import software.bernie.geckolib.renderer.GeoBlockRenderer

class ArcaneColumnRenderer: GeoBlockRenderer<ArcaneColumnBlockEntity>(MODEL) {
    companion object{
        val MODEL = DefaultedBlockGeoModel<ArcaneColumnBlockEntity>(LostArcana.id("arcane_column"))

        val BOX = AABB.encapsulatingFullBlocks(BlockPos(0,-2,0), BlockPos(0,2,0))
    }

    override fun shouldRenderOffScreen(blockEntity: ArcaneColumnBlockEntity): Boolean {
        return true
    }

    //@Override
    //@JvmName("getRenderBoundingBox")
    //fun getRenderBoundingBox(be: ArcaneColumnBlockEntity): AABB = AABB.encapsulatingFullBlocks(be.blockPos.below(2),be.blockPos.above(2))

}