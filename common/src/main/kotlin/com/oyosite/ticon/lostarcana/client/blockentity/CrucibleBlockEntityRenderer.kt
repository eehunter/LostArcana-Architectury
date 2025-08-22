package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.vertex.PoseStack
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.CrucibleBlockEntity
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.client.platformGetWaterSprite
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.BlockElementFace
import net.minecraft.client.renderer.block.model.BlockFaceUV
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.renderer.block.model.FaceBakery
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.resources.model.Material
import net.minecraft.client.resources.model.ModelState
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.material.Fluids
import org.joml.Vector3f

class CrucibleBlockEntityRenderer(val context: BlockEntityRendererProvider.Context): BlockEntityRenderer<CrucibleBlockEntity> {
    val stack by lazy { ItemStack(Items.CAULDRON) }
    val waterSprite get() = platformGetWaterSprite()
    /*val waterFaceUV = BlockFaceUV(floatArrayOf(waterSprite.u0, waterSprite.v0, waterSprite.u1, waterSprite.v1), 0)
    val waterElementFace = BlockElementFace(Direction.UP, 0, "", waterFaceUV)
    val waterFace = FACE_BAKERY.bakeQuad(Vector3f(0f,0f,0f), Vector3f(1f,0f,1f), waterElementFace, waterSprite,
        Direction.UP, ModelState)*/
    val texture = LostArcana.id("minecraft:textures/block/water_still.png")
    val renderType = RenderType.entityTranslucent(texture)
    val root = context.bakeLayer(LostArcanaClient.CRUCIBLE_CONTENTS_MODEL_LAYER)


    override fun render(
        blockEntity: CrucibleBlockEntity,
        f: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int
    ) {
        val level = blockEntity.level as? ClientLevel?: return
        val player = Minecraft.getInstance().player?: return
        poseStack.pushPose()
        //poseStack.scale(1f, .00001f, 1f)
        poseStack.translate(0f, blockEntity.fluidAmount/1000f, 0f)
        //val relativePos = blockEntity.blockPos.center.subtract(player.position())
        //poseStack.translate(relativePos.x, relativePos.y, relativePos.z)



        //val vertexConsumer = multiBufferSource.getBuffer(RenderType.translucent())



        /*val tx = waterSprite.u0
        val ty = waterSprite.v0
        val tw = waterSprite.u1
        val th = waterSprite.v1



        vertexConsumer.addVertex(0f, 1.5f, 0f).setUv(tx, ty).setColor(0xFFFFFFFFu.toInt()).setOverlay(i).setLight(j).setNormal(0.0F, 1.0F, 0.0F)
        vertexConsumer.addVertex(1f, 1.5f, 0f).setUv(tw, ty).setColor(0xFFFFFFFFu.toInt()).setOverlay(i).setLight(j).setNormal(0.0F, 1.0F, 0.0F)
        vertexConsumer.addVertex(0f, 1.5f, 1f).setUv(tw, th).setColor(0xFFFFFFFFu.toInt()).setOverlay(i).setLight(j).setNormal(0.0F, 1.0F, 0.0F)
        vertexConsumer.addVertex(1f, 1.5f, 1f).setUv(tx, th).setColor(0xFFFFFFFFu.toInt()).setOverlay(i).setLight(j).setNormal(0.0F, 1.0F, 0.0F)
        vertexConsumer*/

        //level
        //Minecraft.getInstance().blockRenderer
        Minecraft.getInstance()
        //WATER_TEXTURE.buffer(multiBufferSource, RenderType::entityTranslucent)
        //entityTranslucent(waterSprite.atlasLocation())
        //waterSprite.wrap(multiBufferSource.getBuffer(RenderType.waterMask()))
        //waterSprite.wrap(multiBufferSource.getBuffer(RenderType.entityTranslucent(waterSprite.atlasLocation())))
        root.render(poseStack, waterSprite.wrap(multiBufferSource.getBuffer(RenderType.entityTranslucent(waterSprite.atlasLocation()))), i, j, -1)

        //Minecraft.getInstance().itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, i, j, poseStack, multiBufferSource, level, blockEntity.blockPos.asLong().toInt())
        //vertexConsumer
        //Minecraft.getInstance().blockRenderer.renderLiquid(BlockPos.ZERO, level, multiBufferSource.getBuffer(RenderType.translucent()), level.getBlockState(blockEntity.blockPos), Fluids.WATER.defaultFluidState())
        poseStack.popPose()
    }

    companion object{
        val FACE_BAKERY = FaceBakery()
        val WATER_TEXTURE = Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("textures/block/water_still"))

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