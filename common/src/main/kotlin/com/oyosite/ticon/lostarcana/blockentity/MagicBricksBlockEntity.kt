package com.oyosite.ticon.lostarcana.blockentity

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



class MagicBricksBlockEntity(pos: BlockPos, state: BlockState):
    BlockEntity(MAGIC_BRICKS_BLOCK_ENTITY.value(), pos, state),
    GeoBlockEntity {

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) { anim: AnimationState<MagicBricksBlockEntity> -> anim.setAndContinue(ANIMATION) })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache


    companion object{
        val ANIMATION = RawAnimation.begin().thenPlay("animation.model.activate").thenLoop("animation.model.idle")
    }
}