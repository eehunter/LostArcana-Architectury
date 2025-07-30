package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.blockentity.MagicBricksBlockEntity
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

class ArcaneColumnBlockEntity(pos: BlockPos, state: BlockState):
    BlockEntity(ARCANE_COLUMN_BLOCK_ENTITY.value(), pos, state),
    GeoBlockEntity {

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) { anim: AnimationState<ArcaneColumnBlockEntity> -> anim.setAndContinue(ANIMATION) })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache


    companion object{
        val ANIMATION = RawAnimation.begin().thenLoop("animation.model.idle")
    }
}