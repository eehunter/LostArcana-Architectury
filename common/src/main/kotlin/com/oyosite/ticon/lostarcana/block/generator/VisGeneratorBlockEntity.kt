package com.oyosite.ticon.lostarcana.block.generator

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.blockentity.VIS_GENERATOR_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.AnimationState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil
import kotlin.random.Random

class VisGeneratorBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(VIS_GENERATOR_BLOCK_ENTITY.value(), blockPos, blockState), GeoBlockEntity {

    var attachedSource: AuraSource? = null

    val generateAmount = 1f

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) { anim: AnimationState<VisGeneratorBlockEntity> ->
            anim.setAndContinue(
                ANIMATION
            )
        })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    fun tryConnectSource() {
        if ((attachedSource?.pos?.distanceToSqr(blockPos.below().center) ?: 0.0) > 1.0) {
            attachedSource = null
        }
        val l = level ?: return
        if (l.gameTime % 20L != 0L) return
        if (attachedSource != null) return
        attachedSource = l.getBlockEntity(blockPos.below()) as? AuraSource
        if (attachedSource != null) return
        attachedSource =
            l.getEntities(null, AABB.encapsulatingFullBlocks(blockPos.below(), blockPos.below())) { it is AuraSource }
                .firstOrNull() as? AuraSource
    }

    fun onGenerate(){

    }

    companion object : BlockEntityTicker<VisGeneratorBlockEntity> {
        val ANIMATION = RawAnimation.begin().thenLoop("animation.model.idle")

        override fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: VisGeneratorBlockEntity
        ) {
            blockEntity.tryConnectSource()

            if (level.gameTime % 10L != 0L) return
            val source = blockEntity.attachedSource ?: return
            if (Random.nextFloat() > .25f) return
            (source as? AuraNodeEntity)?.let { node ->
                if (node.vis < 1.2f * node.maxVis) {
                    node.vis += blockEntity.generateAmount
                    blockEntity.onGenerate()
                }
            }
        }
    }
}