package com.oyosite.ticon.lostarcana.item

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

interface SpecialRenderingItem {
    fun render(
        itemStack: ItemStack,
        itemDisplayContext: ItemDisplayContext,
        bl: Boolean,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int,
        bakedModel: BakedModel?
    )
}