package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.supermartijn642.fusion.api.provider.FusionTextureMetadataProvider
import com.supermartijn642.fusion.api.texture.DefaultTextureTypes
import com.supermartijn642.fusion.api.texture.data.BaseTextureData
import com.supermartijn642.fusion.api.texture.data.ConnectingTextureData
import com.supermartijn642.fusion.api.texture.data.ConnectingTextureLayout
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput

class FusionTextureMetadata(output: FabricDataOutput) : FusionTextureMetadataProvider(LostArcana.MOD_ID, output) {
    override fun generate() {
        val texData = ConnectingTextureData.builder().layout(ConnectingTextureLayout.OVERLAY).renderType(BaseTextureData.RenderType.CUTOUT).build()
        INFUSED_STONES.forEachIndexed { i, holder ->
            addTextureMetadata(LostArcana.id("block/infused_stone_edge_overlay_${holder.get().aspect.id.path}"),
                DefaultTextureTypes.CONNECTING, texData)
        }
    }
}