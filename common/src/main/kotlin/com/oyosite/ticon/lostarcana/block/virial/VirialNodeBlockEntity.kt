package com.oyosite.ticon.lostarcana.block.virial

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.block.generator.VisGeneratorBlockEntity
import com.oyosite.ticon.lostarcana.block.scrubber.ScrubberBaseBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.VIRIAL_NODE_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.client.fx.WispParticleData
import com.oyosite.ticon.lostarcana.util.auraSources
import com.oyosite.ticon.lostarcana.util.releaseFluxAtLocation
import com.oyosite.ticon.lostarcana.util.triggerFluxEvent
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.AnimationState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil
import kotlin.math.max

class VirialNodeBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(VIRIAL_NODE_BLOCK_ENTITY.value(), blockPos, blockState), GeoBlockEntity, AuraSource {
    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    var visCapacity = 200f
    val maxVis get() = max(visCapacity-flux*.4f, visCapacity * 0.1f)

    override var vis: Float = 0f
    override var flux: Float = 0f
    override val pos: Vec3 get() = blockPos.center

    override fun setLevel(level: Level) {
        if(level!=this.level) {
            this.level?.auraSources?.remove(this)
            level.auraSources.add(this)
        }
        super.setLevel(level)
    }

    override fun setRemoved() {
        super.setRemoved()
        this.level?.auraSources?.remove(this)
    }

    override fun clearRemoved() {
        super.clearRemoved()
        this.level?.auraSources?.add(this)
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        compoundTag.putFloat("visCap", visCapacity)
        compoundTag.putFloat("storedVis", vis)
        compoundTag.putFloat("flux", flux)
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        flux = compoundTag.getFloat("flux")
        if(compoundTag.contains("visCap"))visCapacity = compoundTag.getFloat("visCap")
        vis = if(compoundTag.contains("storedVis")) compoundTag.getFloat("storedVis") else maxVis

        level?.auraSources?.add(this)
    }

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) { anim: AnimationState<VirialNodeBlockEntity> -> anim.setAndContinue(ANIMATION) })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag =
        CompoundTag().also { saveAdditional(it, provider) }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this)



    companion object: BlockEntityTicker<VirialNodeBlockEntity>{
        val ANIMATION = RawAnimation.begin().thenLoop("animation.virial_node.idle")

        override fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: VirialNodeBlockEntity
        ) {
            if(level.gameTime % 20L == 0L) {
                val pos = blockEntity.pos
                if(blockEntity.vis >= 1f){
                    level.addParticle(WispParticleData(0.05f + blockEntity.vis/300f, 1f, 1f, 1f, 0.25f, 4f, needsRevealing = true), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0)
                }
                if (blockEntity.flux > 5 && level.random.nextFloat() < (.25f - 1f / blockEntity.flux) / 4)
                    if (releaseFluxAtLocation(level, pos, 1f, listOf(blockEntity)))
                        blockEntity.flux--
                    else if(blockEntity.flux > 10 && level.random.nextFloat() < (.25f - 1f / blockEntity.flux) / 16)
                        triggerFluxEvent(level, pos, blockEntity)

            }
        }
    }
}