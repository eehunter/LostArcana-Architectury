package com.oyosite.ticon.lostarcana.block.scrubber

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.blockentity.FLUX_SCRUBBER_BLOCK_ENTITY
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
import kotlin.math.min
import kotlin.random.Random

class ScrubberBaseBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(FLUX_SCRUBBER_BLOCK_ENTITY.value(), blockPos, blockState), GeoBlockEntity {

    var attachedSource: AuraSource? = null

    val drainAmount = 1f

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) { anim: AnimationState<ScrubberBaseBlockEntity> -> anim.setAndContinue(ANIMATION) })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    fun tryConnectSource(){
        if((attachedSource?.pos?.distanceToSqr(blockPos.above().center) ?: 0.0) > 1.0){
            attachedSource = null
        }
        val l = level?:return
        if (l.gameTime % 20L != 0L) return
        if (attachedSource!=null) return
        attachedSource = l.getBlockEntity(blockPos.above()) as? AuraSource
        if (attachedSource!=null) return
        attachedSource = l.getEntities(null, AABB.encapsulatingFullBlocks(blockPos.above(), blockPos.above())) { it is AuraSource }.firstOrNull() as? AuraSource
    }

    fun onDrain(amount: Float){
        //TODO
    }

    companion object: BlockEntityTicker<ScrubberBaseBlockEntity>{
        val ANIMATION = RawAnimation.begin().thenLoop("animation.model.idle")

        override fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: ScrubberBaseBlockEntity
        ) {
            blockEntity.tryConnectSource()

            if (level.gameTime % 10L != 0L) return
            val source = blockEntity.attachedSource?:return
            if (Random.nextFloat() > .25f) return
            val f = source.flux
            val d = min(blockEntity.drainAmount, f)
            source.flux -= d
            blockEntity.onDrain(d)
        }
    }
}