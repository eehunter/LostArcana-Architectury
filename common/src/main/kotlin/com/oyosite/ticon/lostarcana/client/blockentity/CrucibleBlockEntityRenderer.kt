package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.blockentity.CrucibleBlockEntity
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.client.platformGetWaterSprite
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.BiomeColors
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
        val level = blockEntity.level as? ClientLevel ?: return
        val baseColor = BiomeColors.getAverageWaterColor(level, blockEntity.blockPos).toUInt()

        val br = baseColor shr 16 and 0xFFu
        val bg = baseColor shr 8 and 0xFFu
        val bb = baseColor shr 0 and 0xFFu

        val aspects = blockEntity.aspects
        val colors = aspects.keys.map(Aspect::color)
        val stacking = aspects.values.toList()

        val r = if(colors.isEmpty()) br else (weightedMeanColor(colors, 16, stacking::get) + br) / 2U
        val g = if(colors.isEmpty()) bg else (weightedMeanColor(colors, 8, stacking::get) + bg) / 2U
        val b = if(colors.isEmpty()) bb else (weightedMeanColor(colors, 0, stacking::get) + bb) / 2U

        val color = (
            0xFF000000u or
            (r shl 16) or
            (g shl 8) or
            (b)
        ).toInt()

        poseStack.pushPose()
        poseStack.translate(0f, blockEntity.fluidAmount/1000f, 0f)

        root.render(poseStack, waterSprite.wrap(multiBufferSource.getBuffer(RenderType.entityTranslucent(waterSprite.atlasLocation()))), i, j, color)

        poseStack.popPose()
    }

    companion object{
        fun weightedMeanColor(colors: List<UInt>, bitShift: Int, weightGetter: (Int)->Int, mask: UInt = 0xFFu): UInt{
            val comps = colors.map { (it shr bitShift) and mask }
            var cumulative = 0u
            var cumulativeWeight = 0u
            comps.forEachIndexed { i, col ->
                val w = weightGetter(i).toUInt()
                cumulative += col * w
                cumulativeWeight += w
            }
            return cumulative/cumulativeWeight
        }

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