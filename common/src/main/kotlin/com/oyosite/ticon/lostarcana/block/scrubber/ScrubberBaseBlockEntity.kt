package com.oyosite.ticon.lostarcana.block.scrubber

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.blockentity.FLUX_SCRUBBER_BLOCK_ENTITY
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.item.ItemStack
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
import software.bernie.geckolib.constant.dataticket.DataTicket
import software.bernie.geckolib.util.GeckoLibUtil
import kotlin.jvm.optionals.getOrNull
import kotlin.math.min
import kotlin.random.Random

class ScrubberBaseBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(FLUX_SCRUBBER_BLOCK_ENTITY.value(), blockPos, blockState), GeoBlockEntity {

    var attachedSource: AuraSource? = null

    val drainAmount = 1f

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    val items = Array(4){ ItemStack.EMPTY }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        items.forEachIndexed { i, stack ->
            if (stack.isEmpty)return@forEachIndexed
            compoundTag.put("slot$i", stack.save(provider))
        }
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        for(i in 0 until 4){
            if(!compoundTag.contains("slot$i"))continue
            ItemStack.parse(provider, compoundTag.get("slot$i")?:continue).getOrNull()?.let{ items[i] = it }
        }

        onUpdateDefluxers()
    }


    var animationController: AnimationController<ScrubberBaseBlockEntity>? = null

    val defluxerAnimationControllers = mutableListOf<AnimationController<ScrubberBaseBlockEntity>>()

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) { anim: AnimationState<ScrubberBaseBlockEntity> ->
            //DEFLUXER_DATA.forEachIndexed { i, dat -> anim.setData(dat, (if(items[i].isEmpty) 1 else 1) as Integer) }
            anim.resetCurrentAnimation()
            anim.setAndContinue(ANIMATION)
        }.also { animationController = it })
        //HIDE_DEFLUXER_ANIMATIONS.forEach { defluxerAnimationControllers.add(AnimationController(this) { anim: AnimationState<ScrubberBaseBlockEntity> -> anim.setAndContinue(it) } ) }
        //defluxerAnimationControllers.add(AnimationController(this) { anim: AnimationState<ScrubberBaseBlockEntity> -> anim.setAndContinue(HIDE_DEFLUXER_ANIMATIONS[0]) } )
        //defluxerAnimationControllers.forEach(controllers::add)
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

    fun onUpdateDefluxers(index: Int? = null){
        animationController?.forceAnimationReset()
        /*val a = animationController?:return
        (index?.let(::listOf)?:(0 until 4)).forEach {

        }*/

        //defluxerAnimationControllers.getOrNull(0)?.setAnimation((if (items[0].isEmpty) HIDE_DEFLUXER_ANIMATIONS else SHOW_DEFLUXER_ANIMATIONS)[0])
        /*if(defluxerAnimationControllers.size < 4)return
        if(index!=null) {
            defluxerAnimationControllers[index].setAnimation(
                (if (items[index].isEmpty) HIDE_DEFLUXER_ANIMATIONS else SHOW_DEFLUXER_ANIMATIONS)[index]
            )
        } else {
            (0 until 4).forEach {
                defluxerAnimationControllers[it].setAnimation(
                    (if (items[it].isEmpty) HIDE_DEFLUXER_ANIMATIONS else SHOW_DEFLUXER_ANIMATIONS)[it]
                )

            }
        }*/
        //DEFLUXER_ANIMATIONS[index].thenPlayAndHold("animation.flux_scrubber.${if(items[index].isEmpty) "hide" else "show"}_defluxer_$index")
    }

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag =
        CompoundTag().also { saveAdditional(it, provider) }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this)

    companion object: BlockEntityTicker<ScrubberBaseBlockEntity>{
        val ANIMATION = RawAnimation.begin().thenLoop("animation.flux_scrubber.idle")
        //val DEFLUXER_DATA = Array(4) { DataTicket("defluxer$it", Integer::class.java) }

        val HIDE_DEFLUXER_ANIMATIONS = Array(4){
            RawAnimation.begin().thenPlayAndHold("animation.flux_scrubber.hide_defluxer_$it")
        }
        val SHOW_DEFLUXER_ANIMATIONS = Array(4){
            RawAnimation.begin().thenPlayAndHold("animation.flux_scrubber.show_defluxer_$it")
        }

        override fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: ScrubberBaseBlockEntity
        ) {
            blockEntity.tryConnectSource()

            if (level.gameTime % 10L != 0L) return

            if(level.isClientSide){
                blockEntity.defluxerAnimationControllers.getOrNull(0)?.setAnimation(if((level.gameTime % 20L != 0L))HIDE_DEFLUXER_ANIMATIONS[0] else SHOW_DEFLUXER_ANIMATIONS[0])
            }

            val source = blockEntity.attachedSource?:return
            if (Random.nextFloat() > .25f) return
            val f = source.flux
            val d = min(blockEntity.drainAmount, f)
            source.flux -= d
            blockEntity.onDrain(d)
            blockEntity.setChanged()
            //if(level.isClientSide)blockEntity.defluxerAnimationControllers.getOrNull(0)?.setAnimation(if(Random.nextBoolean())HIDE_DEFLUXER_ANIMATIONS[0] else SHOW_DEFLUXER_ANIMATIONS[0])
        }
    }
}