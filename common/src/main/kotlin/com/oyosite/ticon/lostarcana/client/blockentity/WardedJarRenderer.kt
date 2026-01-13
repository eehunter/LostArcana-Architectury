package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import com.oyosite.ticon.lostarcana.client.drawArbitraryCuboid
import dev.architectury.fluid.FluidStack
import dev.architectury.hooks.fluid.FluidStackHooks
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer

class WardedJarRenderer: BlockEntityRenderer<WardedJarBlockEntity> {
    override fun render(
        blockEntity: WardedJarBlockEntity,
        f: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int
    ) {
        val fluidStack = blockEntity.fluidContents
        render(fluidStack, blockEntity.maxFluidAmount, f, poseStack, multiBufferSource, i, j)
    }

    companion object{
        fun render(
            fluidStack: FluidStack,
            maxFluidAmount: Long,
            f: Float,
            poseStack: PoseStack,
            multiBufferSource: MultiBufferSource,
            i: Int,
            j: Int
        ) {
            if(fluidStack.isEmpty)return
            val color = FluidStackHooks.getColor(fluidStack)
            val sprite = FluidStackHooks.getStillTexture(fluidStack)?:return
            val buffer = multiBufferSource.getBuffer(RenderType.translucent())

            val height = fluidStack.amount.toFloat()/maxFluidAmount * (14f/16)
            val topUV = floatArrayOf(
                sprite.getU(4f/16),
                sprite.getV(4f/16),
                sprite.getU(12f/16),
                sprite.getV(12f/16)
            )

            val sideUV = floatArrayOf(
                sprite.getU(4f/16),
                sprite.getV(1f/16),
                sprite.getU(12f/16),
                sprite.getV(1f/16 + height),
            )

            drawArbitraryCuboid(poseStack, buffer, color, i, j, topUV, topUV, sideUV, sideUV, sideUV, sideUV,
                4.0f/16, 1.0f/16, 4.0f/16,
                .5f, height, .5f
            )
        }
    }
}