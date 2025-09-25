package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.ELEMENTAL_GEODE_MATERIALS
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.supermartijn642.fusion.api.provider.FusionTextureMetadataProvider
import com.supermartijn642.fusion.api.texture.DefaultTextureTypes
import com.supermartijn642.fusion.api.texture.data.BaseTextureData
import com.supermartijn642.fusion.api.texture.data.ConnectingTextureData
import com.supermartijn642.fusion.api.texture.data.ConnectingTextureLayout
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput

class FusionTextureMetadata(output: FabricDataOutput) : FusionTextureMetadataProvider(LostArcana.MOD_ID, output) {
    override fun generate() {
        val infusedStoneTexData = ConnectingTextureData.builder().layout(ConnectingTextureLayout.OVERLAY).renderType(BaseTextureData.RenderType.CUTOUT).build()
        INFUSED_STONES.forEachIndexed { i, holder ->
            addTextureMetadata(LostArcana.id("block/infused_stone_edge_overlay_${holder.get().aspect.id.path}"),
                DefaultTextureTypes.CONNECTING, infusedStoneTexData)
        }
        val visCrystalTexData = BaseTextureData.builder().emissive(true).build()
        addTextureMetadata(VIS_CRYSTAL.id.withPrefix("item/"), DefaultTextureTypes.BASE,visCrystalTexData)

        val infusedCrystalBorderTexData = ConnectingTextureData.builder().layout(ConnectingTextureLayout.OVERLAY).renderType(BaseTextureData.RenderType.CUTOUT).build()
        ELEMENTAL_GEODE_MATERIALS.forEach { geode ->
            addTextureMetadata(LostArcana.id("block/infused_edge_overlay_${geode.block.id.path}"),
                DefaultTextureTypes.CONNECTING, infusedCrystalBorderTexData)
        }
    }
}