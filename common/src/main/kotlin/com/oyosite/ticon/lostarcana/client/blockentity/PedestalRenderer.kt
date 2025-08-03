package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.oyosite.ticon.lostarcana.blockentity.AbstractPedestalBlockEntity
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.world.item.ItemDisplayContext

class PedestalRenderer<T: AbstractPedestalBlockEntity>: BlockEntityRenderer<T> {
    override fun render(
        blockEntity: T,
        f: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int
    ) {
        val stack = blockEntity.item
        if(stack.isEmpty)return
        val level = blockEntity.level?:return
        val offset = blockEntity.itemRendererOffset
        val (x,y,z) = offset
        poseStack.pushPose()
        poseStack.translate(x,y,z)
        poseStack.scale(0.5f,0.5f,0.5f)
        poseStack.mulPose(Axis.YP.rotationDegrees((f + (level.gameTime % 360L).toFloat()) * 3f))

        Minecraft.getInstance().itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, i, j, poseStack, multiBufferSource, level, blockEntity.blockPos.asLong().toInt())

        poseStack.popPose()
    }
}