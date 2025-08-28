package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import dev.architectury.hooks.fluid.FluidStackHooks
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.Direction
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f

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
        val level = blockEntity.level
        val color = FluidStackHooks.getColor(fluidStack)
        val sprite = FluidStackHooks.getStillTexture(blockEntity.fluidContents)?:return
        poseStack.pushPose()
        poseStack.translate(4.0/16, 1.0/16, 4.0/16)
        val buffer = multiBufferSource.getBuffer(RenderType.translucent())

        val height = fluidStack.amount.toFloat()/blockEntity.maxFluidAmount * (14f/16)
        val topUV = floatArrayOf(
            sprite.getU(4f/16),
            sprite.getV(4f/16),
            sprite.getU(12f/16),
            sprite.getV(12f/16)
        )

        drawFace(poseStack.last(), buffer, color, j, i, topUV[0], topUV[1], topUV[2], topUV[3], floatArrayOf(
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
        ))


        val sideUV = floatArrayOf(
            sprite.getU(4f/16),
            sprite.getV(1f/16),
            sprite.getU(12f/16),
            sprite.getV(1f/16 + height),
        )
        drawFace(poseStack.last(), buffer, color, j, i, sideUV[0], sideUV[1], sideUV[2], sideUV[3], floatArrayOf(
            0f, 0f, .5f,
            0f, height, .5f,
            0f, height, 0f,
            0f, 0f, 0f,
        ))
        drawFace(poseStack.last(), buffer, color, j, i, sideUV[0], sideUV[1], sideUV[2], sideUV[3], floatArrayOf(
            .5f, 0f, 0f,
            .5f, height, 0f,
            .5f, height, .5f,
            .5f, 0f, .5f,
        ))
        drawFace(poseStack.last(), buffer, color, j, i, sideUV[0], sideUV[1], sideUV[2], sideUV[3], floatArrayOf(
            0f, 0f, 0f,
            0f, height, 0f,
            .5f, height, 0f,
            .5f, 0f, 0f,
        ))
        drawFace(poseStack.last(), buffer, color, j, i, sideUV[0], sideUV[1], sideUV[2], sideUV[3], floatArrayOf(
            .5f, 0f, .5f,
            .5f, height, .5f,
            .0f, height, .5f,
            .0f, 0f, .5f,
        ))
        //buffer.putBulkData()

        poseStack.popPose()
    }

    fun drawFace(pose: PoseStack.Pose, buffer: VertexConsumer, color: Int, overlay: Int, light: Int, minU: Float, minV: Float, maxU: Float, maxV: Float, coords: FloatArray){
        buffer.addVertex(pose, coords[0], coords[1], coords[2])
            .setColor(color)
            .setUv(minU, maxV)
            .setOverlay(overlay)
            .setLight(light)
            .setNormal(1f,1f,1f)
        buffer.addVertex(pose, coords[3], coords[4], coords[5])
            .setColor(color)
            .setUv(minU, minV)
            .setOverlay(overlay)
            .setLight(light)
            .setNormal(1f,1f,1f)
        buffer.addVertex(pose, coords[6], coords[7], coords[8])
            .setColor(color)
            .setUv(maxU, minV)
            .setOverlay(overlay)
            .setLight(light)
            .setNormal(1f,1f,1f)
        buffer.addVertex(pose, coords[9], coords[10], coords[11])
            .setColor(color)
            .setUv(maxU, maxV)
            .setOverlay(overlay)
            .setLight(light)
            .setNormal(1f,1f,1f)

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