package com.oyosite.ticon.lostarcana.client

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.aspects
import com.oyosite.ticon.lostarcana.block.InfusedStoneBlock
import com.oyosite.ticon.lostarcana.block.VisLight
import com.oyosite.ticon.lostarcana.block.scrubber.ScrubberBaseBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.VisLightBlockEntity
import com.oyosite.ticon.lostarcana.client.blockentity.CrucibleBlockEntityRenderer
import com.oyosite.ticon.lostarcana.client.entity.AuraNodeEntityRenderer
import com.oyosite.ticon.lostarcana.client.fx.registerParticlesClient
import com.oyosite.ticon.lostarcana.entity.AURA_NODE
import com.oyosite.ticon.lostarcana.item.RAW_ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.WandItem
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry
import dev.architectury.registry.client.level.entity.EntityRendererRegistry
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.color.item.ItemColors
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.component.DyedItemColor
import net.minecraft.world.level.block.entity.BlockEntity
import software.bernie.geckolib.loading.math.MolangQueries

object LostArcanaClient {
    val VIS_CRYSTAL_ITEM_COLOR = ItemColor{ stack, _ -> (stack.aspects[0].aspect.color or 0xFF000000u).toInt()  }
    val RAW_ASPECTED_ITEM_COLOR = ItemColor{ stack, i -> (if(i==1) (stack[RAW_ASPECT_COMPONENT]?.color?:0xFFFFFFFFu) or 0xFF000000u else 0xFFFFFFFFu).toInt()  }
    val THAUMOMETER_ITEM_COLOR = ItemColor{ stack, tintIndex -> (if(tintIndex==1) 0xFFdb00ffu else 0xFFffffffu).toInt() }
    val WAND_ITEM_COLOR = ItemColor(WandItem::getTintColor)
    val NITOR_ITEM_COLOR = ItemColor{ stack, i -> if(i==0) DyedItemColor.getOrDefault(stack, VisLight.DEFAULT_COLOR) else -1 }

    val INFUSED_STONE_BLOCK_COLOR = BlockColor{ state, getter, pos, i -> ((state.block as InfusedStoneBlock).aspect.color or 0xFF000000u).toInt() }
    val NITOR_BLOCK_COLOR = BlockColor{ state, getter, pos, i -> (getter?.getBlockEntity(pos ?: return@BlockColor -1) as? VisLightBlockEntity)?.components()?.get(DataComponents.DYED_COLOR)?.rgb ?: -1}

    val AURA_NODE_MODEL_LAYER = ModelLayerLocation(LostArcana.id("aura_node"), "cube")
    val CRUCIBLE_CONTENTS_MODEL_LAYER = ModelLayerLocation(LostArcana.id("crucible_contents"), "main")

    fun initClient(){
        EntityModelLayerRegistry.register(AURA_NODE_MODEL_LAYER, AuraNodeEntityRenderer::getTexturedModelData)
        EntityModelLayerRegistry.register(CRUCIBLE_CONTENTS_MODEL_LAYER, CrucibleBlockEntityRenderer::getTexturedModelData)
        EntityRendererRegistry.register(AURA_NODE, ::AuraNodeEntityRenderer)

        registerParticlesClient()

        /*(0 until 4).forEach {
            MolangQueries.setActorVariable<ScrubberBaseBlockEntity>("query.defluxer$it") { actor ->
                if (actor.animatable.items[it].isEmpty) 0.0 else 1.0
                1.0
            }
        }*/
    }
}