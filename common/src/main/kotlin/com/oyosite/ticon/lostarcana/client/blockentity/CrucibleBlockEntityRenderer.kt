package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.CrucibleBlockEntity
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.client.platformGetWaterSprite
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class CrucibleBlockEntityRenderer(val context: BlockEntityRendererProvider.Context): BlockEntityRenderer<CrucibleBlockEntity> {
    val stack by lazy { ItemStack(Items.CAULDRON) }
    val waterSprite get() = platformGetWaterSprite()
    val texture = LostArcana.id("minecraft:textures/block/water_still.png")
    val root = context.bakeLayer(LostArcanaClient.CRUCIBLE_CONTENTS_MODEL_LAYER)


    override fun render(
        blockEntity: CrucibleBlockEntity,
        f: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int
    ) {
        poseStack.pushPose()
        poseStack.translate(0f, blockEntity.fluidAmount/1000f, 0f)

        root.render(poseStack, waterSprite.wrap(multiBufferSource.getBuffer(RenderType.entityTranslucent(waterSprite.atlasLocation()))), i, j, -1)

        poseStack.popPose()
    }

    companion object{
        @JvmStatic
        fun getTexturedModelData(): LayerDefinition {
            val modelData = MeshDefinition()
            val modelPartData: PartDefinition = modelData.root
            modelPartData.addOrReplaceChild(
                "main",
                CubeListBuilder.create().texOffs(-32, 0).addBox(0f, 0f, 0f, 16f, .0001f, 16f, setOf(Direction.UP)),
                PartPose.rotation(0f, 0f, 0f)
            )
            return LayerDefinition.create(modelData, 16, 16)
        }
    }
}