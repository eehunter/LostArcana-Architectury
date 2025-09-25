package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.ELEMENTAL_GEODE_MATERIALS
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.unaryPlus
import com.supermartijn642.fusion.api.model.DefaultModelTypes
import com.supermartijn642.fusion.api.model.ModelInstance
import com.supermartijn642.fusion.api.model.data.BaseModelData
import com.supermartijn642.fusion.api.model.data.ConnectingModelData
import com.supermartijn642.fusion.api.predicate.DefaultConnectionPredicates
import com.supermartijn642.fusion.api.provider.FusionModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput

class FusionModels(output: FabricDataOutput) : FusionModelProvider(LostArcana.MOD_ID, output, ) {
    override fun generate() {

        INFUSED_STONES.forEachIndexed { i, holder ->
            val data = ConnectingModelData.builder()
                .parent(LostArcana.id("block/stone_overlay"))
                .texture("overlay", LostArcana.id("block/infused_stone_edge_overlay_${holder.get().aspect.id.path}"))
                .connection(DefaultConnectionPredicates.matchBlock(+holder))
                .build()
            val instance = ModelInstance.of(DefaultModelTypes.CONNECTING, data)
            addModel(holder.id.withPrefix("block/").withSuffix("_overlay"), instance)
            val data2 = BaseModelData.builder()
                .parent(Identifier.parse("block/stone"))
                .parent(LostArcana.id("block/infused_stone_fusion"))
                .build()
            val instance2 = ModelInstance.of(DefaultModelTypes.BASE, data2)
            addModel(holder.id.withPrefix("block/"), instance2)
            val data3 = BaseModelData.builder()
                .parent(LostArcana.id("item/infused_stone"))
                //.parent(LostArcana.id("block/infused_stone_fusion"))
                .build()
            val instance3 = ModelInstance.of(DefaultModelTypes.BASE, data3)
            addModel(holder.id.withPrefix("item/"), instance3)
        }

        ELEMENTAL_GEODE_MATERIALS.forEach { geode ->
            val data = ConnectingModelData.builder()
                .parent(LostArcana.id("block/stone_overlay"))
                .texture("overlay", LostArcana.id("block/infused_edge_overlay_${geode.block.id.path}"))
                .connection(DefaultConnectionPredicates.matchBlock(+geode.block).or(DefaultConnectionPredicates.matchBlock(+geode.buddingBlock)))
                .build()
            val instance = ModelInstance.of(DefaultModelTypes.CONNECTING, data)
            addModel(geode.block.id.withPrefix("block/").withSuffix("_overlay"), instance)

            val data2 = BaseModelData.builder()
                //.parent(Identifier.parse("block/cube_all"))
                .parent(LostArcana.id("block/infused_crystal_block"))
                .texture("all", geode.block.id.withPrefix("block/"))
                .build()
            val instance2 = ModelInstance.of(DefaultModelTypes.BASE, data2)
            addModel(geode.block.id.withPrefix("block/"), instance2)
            val data3 = BaseModelData.builder()
                .parent(geode.block.id.withPrefix("block/"))
                .build()
            val instance3 = ModelInstance.of(DefaultModelTypes.BASE, data3)
            addModel(geode.block.id.withPrefix("item/"), instance3)
        }

        //val visCrystalData = BaseModelData.builder().parent(Identifier.parse("item/generated"))
        //    .texture("layer0", Identifier.parse("lostarcana:item/vis_crystal"))
        //    .build()

        //addModel(VIS_CRYSTAL.id.withPrefix("item/"), ModelInstance.of(DefaultModelTypes.BASE, visCrystalData))

        //val data = ConnectingModelData.builder().parent(LostArcana.id("minecraft:block/cube_all")).texture("all", Identifier.parse("block/stone")).texture("particle", Identifier.parse("block/stone_bricks"))
        //    .connection("all", DefaultConnectionPredicates.isSameBlock()).connection("particle", DefaultConnectionPredicates.matchBlock(+ARCANE_STONE)).build()
        //val instance = ModelInstance.of(DefaultModelTypes.CONNECTING, data)
        //addModel(TEST_BLOCK.id, instance)
    }
}