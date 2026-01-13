package com.oyosite.ticon.lostarcana.item

import com.mojang.blaze3d.vertex.PoseStack
import com.oyosite.ticon.lostarcana.BlockProperties
import com.oyosite.ticon.lostarcana.block.dissolver.DissolverBlockEntity
import com.oyosite.ticon.lostarcana.block.dissolver.DissolverBlockEntity.Companion.jarStorageCapacity
import com.oyosite.ticon.lostarcana.client.blockentity.WardedJarRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

class WardedJarBlockItem(block: Block, properties: Properties): BlockItem(block, properties), SpecialRenderingItem {
    override fun render(
        itemStack: ItemStack,
        itemDisplayContext: ItemDisplayContext,
        bl: Boolean,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int,
        bakedModel: BakedModel?
    ) {
        WardedJarRenderer.render(
            itemStack[SINGLE_FLUID_STORAGE_COMPONENT]?.copy?:return,
            itemStack.jarStorageCapacity,
            0f,
            poseStack,
            multiBufferSource,
            i,
            j
        )
    }


}