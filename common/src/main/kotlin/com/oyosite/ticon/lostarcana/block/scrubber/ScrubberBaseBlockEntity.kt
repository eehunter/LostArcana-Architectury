package com.oyosite.ticon.lostarcana.block.scrubber

import com.oyosite.ticon.lostarcana.blockentity.FLUX_SCRUBBER_BLOCK_ENTITY
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.AnimationState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil

class ScrubberBaseBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(FLUX_SCRUBBER_BLOCK_ENTITY.value(), blockPos, blockState), GeoBlockEntity {

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) { anim: AnimationState<ScrubberBaseBlockEntity> -> anim.setAndContinue(ANIMATION) })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    companion object{
        val ANIMATION = RawAnimation.begin().thenLoop("animation.model.idle")
    }
}