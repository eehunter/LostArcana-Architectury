package com.oyosite.ticon.lostarcana.block.generator

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.block.virial.VirialNodeBlock
import com.oyosite.ticon.lostarcana.block.virial.VirialNodeBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.VIS_GENERATOR_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import com.oyosite.ticon.lostarcana.item.DEFLUXER_PROPERTIES
import com.oyosite.ticon.lostarcana.item.VIRIAL_ENGINE_PROPERTIES
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
import software.bernie.geckolib.util.GeckoLibUtil
import kotlin.jvm.optionals.getOrNull
import kotlin.math.min
import kotlin.random.Random

class VisGeneratorBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(VIS_GENERATOR_BLOCK_ENTITY.value(), blockPos, blockState), GeoBlockEntity {

    var attachedSource: AuraSource? = null

    val generateAmount: Float get() {
        var s = 0f
        val d0 = items[0].get(VIRIAL_ENGINE_PROPERTIES)
        val d1 = items[1].get(VIRIAL_ENGINE_PROPERTIES)
        if(d0 != null && d1 != null) s+= (d0.visGeneration + d1.visGeneration).toFloat()
        val d2 = items[2].get(VIRIAL_ENGINE_PROPERTIES)
        val d3 = items[3].get(VIRIAL_ENGINE_PROPERTIES)
        if(d2 != null && d3 != null) s+= (d2.visGeneration + d3.visGeneration).toFloat()
        return s
    }

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    val items = Array(4){ ItemStack.EMPTY }

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

    fun onUpdateGenerators(index: Int? = null){

    }

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
    }

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag =
        CompoundTag().also { saveAdditional(it, provider) }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this)

    companion object : BlockEntityTicker<VisGeneratorBlockEntity> {
        val ANIMATION = RawAnimation.begin().thenLoop("animation.vis_generator.idle")

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
            (source as? VirialNodeBlockEntity)?.let { node ->
                if (node.vis < node.maxVis){
                    node.vis = min(node.maxVis, node.vis+blockEntity.generateAmount)
                    blockEntity.onGenerate()
                }
            }
        }
    }
}