package com.oyosite.ticon.lostarcana.client

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.aspects
import com.oyosite.ticon.lostarcana.block.InfusedStoneBlock
import com.oyosite.ticon.lostarcana.client.entity.AuraNodeEntityRenderer
import com.oyosite.ticon.lostarcana.entity.AURA_NODE
import com.oyosite.ticon.lostarcana.item.FOCUS_COMPONENT
import com.oyosite.ticon.lostarcana.item.WandItem
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry
import dev.architectury.registry.client.level.entity.EntityRendererRegistry
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.model.geom.ModelLayerLocation

object LostArcanaClient {
    val VIS_CRYSTAL_ITEM_COLOR = ItemColor{ stack, _ -> (stack.aspects[0].aspect.color or 0xFF000000u).toInt()  }
    val THAUMOMETER_ITEM_COLOR = ItemColor{ stack, tintIndex -> (if(tintIndex==1) 0xFFdb00ffu else 0xFFffffffu).toInt() }
    val WAND_ITEM_COLOR = ItemColor(WandItem::getTintColor)

    val INFUSED_STONE_BLOCK_COLOR = BlockColor{ state, getter, pos, i -> ((state.block as InfusedStoneBlock).aspect.color or 0xFF000000u).toInt() }

    val AURA_NODE_MODEL_LAYER = ModelLayerLocation(LostArcana.id("aura_node"), "cube")


    fun initClient(){
        EntityModelLayerRegistry.register(AURA_NODE_MODEL_LAYER, AuraNodeEntityRenderer::getTexturedModelData)
        EntityRendererRegistry.register(AURA_NODE, ::AuraNodeEntityRenderer)
    }
}