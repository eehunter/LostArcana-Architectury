package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.TEST_BLOCK
import com.oyosite.ticon.lostarcana.unaryPlus
import com.supermartijn642.fusion.api.model.DefaultModelTypes
import com.supermartijn642.fusion.api.model.ModelInstance
import com.supermartijn642.fusion.api.model.data.ConnectingModelData
import com.supermartijn642.fusion.api.predicate.DefaultConnectionPredicates
import com.supermartijn642.fusion.api.provider.FusionModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput

class FusionModels(output: FabricDataOutput) : FusionModelProvider(LostArcana.MOD_ID, output, ) {
    override fun generate() {

        INFUSED_STONES.forEachIndexed { i, holder ->
            val data = ConnectingModelData.builder()
                .parent(Identifier.parse("block/cube_all"))
                .texture("overlay", LostArcana.id("block/infused_stone_edge_overlay_${holder.get().aspect.id.path}"))
                .connection(DefaultConnectionPredicates.matchBlock(+holder))
                .build()
            val instance = ModelInstance.of(DefaultModelTypes.CONNECTING, data)
            addModel(holder.id.withPrefix("block/"), instance)
        }

        //val data = ConnectingModelData.builder().parent(LostArcana.id("minecraft:block/cube_all")).texture("all", Identifier.parse("block/stone")).texture("particle", Identifier.parse("block/stone_bricks"))
        //    .connection("all", DefaultConnectionPredicates.isSameBlock()).connection("particle", DefaultConnectionPredicates.matchBlock(+ARCANE_STONE)).build()
        //val instance = ModelInstance.of(DefaultModelTypes.CONNECTING, data)
        //addModel(TEST_BLOCK.id, instance)
    }
}