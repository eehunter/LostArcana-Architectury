package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import com.oyosite.ticon.lostarcana.block.dissolver.DissolverBlockEntity
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.world.item.ItemDisplayContext

class DissolverRenderer: BlockEntityRenderer<DissolverBlockEntity> {
    override fun render(
        blockEntity: DissolverBlockEntity,
        f: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int
    ) {
        val jarStack = blockEntity.jar
        val crystalStack = blockEntity.crystal
        if(jarStack.isEmpty && crystalStack.isEmpty)return
        val level = blockEntity.level?:return

        val (x, z) = 0.5 to 0.5
        val y1 = 0.5
        val y2 = 1.3

        if(!jarStack.isEmpty) {
            poseStack.pushPose()
            poseStack.translate(x, y1, z)
            poseStack.scale(0.5f, 0.5f, 0.5f)
            poseStack.mulPose(Axis.YP.rotationDegrees((f + (level.gameTime % 360L).toFloat()) * 3f))

            Minecraft.getInstance().itemRenderer.renderStatic(
                jarStack,
                ItemDisplayContext.FIXED,
                i,
                j,
                poseStack,
                multiBufferSource,
                level,
                blockEntity.blockPos.asLong().toInt()
            )

            poseStack.popPose()
        }

        if(!crystalStack.isEmpty) {
            poseStack.pushPose()
            poseStack.translate(x, y2, z)
            poseStack.scale(0.5f, 0.5f, 0.5f)
            poseStack.mulPose(Axis.YP.rotationDegrees((f + (level.gameTime % 360L).toFloat()) * 3f))

            Minecraft.getInstance().itemRenderer.renderStatic(
                crystalStack,
                ItemDisplayContext.FIXED,
                i,
                j,
                poseStack,
                multiBufferSource,
                level,
                blockEntity.blockPos.asLong().toInt()
            )

            poseStack.popPose()
        }
    }
}