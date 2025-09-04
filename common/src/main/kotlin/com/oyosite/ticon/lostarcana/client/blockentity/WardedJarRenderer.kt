package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import com.oyosite.ticon.lostarcana.client.drawArbitraryCuboid
import dev.architectury.hooks.fluid.FluidStackHooks
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.core.Direction

class WardedJarRenderer: BlockEntityRenderer<WardedJarBlockEntity> {
    var t = 0

    override fun render(
        blockEntity: WardedJarBlockEntity,
        f: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int
    ) {
        val fluidStack = blockEntity.fluidContents
        if(fluidStack.isEmpty)return
        val level = blockEntity.level
        val color = FluidStackHooks.getColor(fluidStack)
        val sprite = FluidStackHooks.getStillTexture(blockEntity.fluidContents)?:return
        val buffer = multiBufferSource.getBuffer(RenderType.translucent())

        t++
        t%=60
        if(t==0)println("color:  $color")

        val height = fluidStack.amount.toFloat()/blockEntity.maxFluidAmount * (14f/16)
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

        poseStack.pushPose()
        poseStack.translate(4.0/16, 1.0/16, 4.0/16)
        /*drawFace(poseStack.last(), buffer, color, j, i, topUV[0], topUV[1], topUV[2], topUV[3], floatArrayOf(
            0f,height,0f,
            0f,height,.5f,
            .5f,height,.5f,
            .5f,height,0f,
        ))
        drawFace(poseStack.last(), buffer, color, j, i, topUV[0], topUV[1], topUV[2], topUV[3], floatArrayOf(
            .5f,0f,0f,
            .5f,0f,.5f,
            0f,0f,.5f,
            0f,0f,0f,
        ))*/




        //buffer.putBulkData()

        poseStack.popPose()
    }



    //class FluidRenderData(val dimensions: Vector3f, val sprite: TextureAtlasSprite, val minU: Float, val minV: Float, val maxU: Float, val maxV: Float){
    //    fun drawFace(buffer: VertexConsumer, )
    //}

    companion object{
        @JvmStatic
        fun getTexturedModelData(): LayerDefinition {
            val modelData = MeshDefinition()
            val modelPartData: PartDefinition = modelData.root
            modelPartData.addOrReplaceChild(
                "main",
                CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 16f, .0001f, 16f, setOf(Direction.UP)),
                PartPose.rotation(0f, 0f, 0f)
            )
            return LayerDefinition.create(modelData, 16, 16)
        }
    }
}