package com.oyosite.ticon.lostarcana.client.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.canSeeAuraNode
import com.oyosite.ticon.lostarcana.client.LostArcanaClient.AURA_NODE_MODEL_LAYER
import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation


class AuraNodeEntityRenderer(context: EntityRendererProvider.Context) : EntityRenderer<AuraNodeEntity>(context) {
    val TEXTURE_LOCATION = Identifier.parse("textures/block/white_concrete.png")
    val RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION)

    val root = context.bakeLayer(AURA_NODE_MODEL_LAYER)

    override fun getTextureLocation(entity: AuraNodeEntity): ResourceLocation {
        return TEXTURE_LOCATION
    }

    override fun render(
        entity: AuraNodeEntity,
        f: Float,
        g: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int
    ) {
        /*val player = Minecraft.getInstance().player?:return
        if (!player.canSeeAuraNode) return
        poseStack {
            root.render(poseStack,multiBufferSource.getBuffer(RENDER_TYPE), i, OverlayTexture.NO_OVERLAY)
        }*/
    }

    companion object{

        @JvmStatic
        fun getTexturedModelData(): LayerDefinition {
            val modelData = MeshDefinition()
            val modelPartData: PartDefinition = modelData.root
            modelPartData.addOrReplaceChild(
                "cube",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3f, 6f, -3f, 6f, 6f, 6f),
                PartPose.rotation(0f, 0f, 0f)
            )
            return LayerDefinition.create(modelData, 64, 64)
        }
    }
}