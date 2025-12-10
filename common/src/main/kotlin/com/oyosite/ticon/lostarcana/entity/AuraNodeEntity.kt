package com.oyosite.ticon.lostarcana.entity

import com.google.gson.JsonObject
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.JsonOps
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aura.AuraNodeTrait
import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.aura.NODE_TRAIT_REGISTRY
import com.oyosite.ticon.lostarcana.aura.NODE_TRAIT_REGISTRY_INTERNAL
import com.oyosite.ticon.lostarcana.client.fx.FXWisp
import com.oyosite.ticon.lostarcana.client.fx.WispParticleData
import com.oyosite.ticon.lostarcana.util.auraSources
import com.oyosite.ticon.lostarcana.util.releaseFluxAtLocation
import com.oyosite.ticon.lostarcana.util.triggerFluxEvent
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtOps
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import kotlin.jvm.optionals.getOrNull
import kotlin.math.max
import kotlin.math.min

class AuraNodeEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level), AuraSource {
    constructor(level: Level): this(AURA_NODE.value(), level)

    override var vis: Float = 0f
    override var flux: Float =  0f
    var visCapacity = 100f
    val maxVis get() = max(visCapacity-flux*.8f, visCapacity * 0.1f)
    var fluxAffinityInternal: Float = 100f
    override val fluxAffinity: Float get() = fluxAffinityInternal

    val traits = mutableListOf<AuraNodeTrait>()

    override fun setLevel(level: Level) {
        if(level!=this.level()) {
            this.level()?.auraSources?.remove(this)
            level.auraSources.add(this)
        }
        super.setLevel(level)
    }

    override fun remove(removalReason: RemovalReason) {
        level().auraSources.remove(this)
        super.remove(removalReason)
    }

    override val pos: Vec3
        get() = eyePosition

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {

        builder.define(VIS_DATA, vis)
        builder.define(FLUX_DATA, flux)
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {
        flux = compoundTag.getFloat("flux")
        if(compoundTag.contains("visCap"))visCapacity = compoundTag.getFloat("visCap")
        vis = if(compoundTag.contains("storedVis")) compoundTag.getFloat("storedVis") else maxVis
        if(compoundTag.contains("fluxAffinity"))fluxAffinityInternal = compoundTag.getFloat("fluxAffinity")

        if(compoundTag.contains("traits")){
            traits.clear()
            compoundTag.getList("traits", Tag.TAG_STRING.toInt()).forEach { NODE_TRAIT_REGISTRY_INTERNAL.get(LostArcana.id(it.asString))?.run(traits::add) }
        }

        level()?.auraSources?.add(this)
    }

    override fun onSyncedDataUpdated(list: List<SynchedEntityData.DataValue<*>>) {
        super.onSyncedDataUpdated(list)
        level()?.auraSources?.add(this)
    }

    override fun onSyncedDataUpdated(entityDataAccessor: EntityDataAccessor<*>) {
        super.onSyncedDataUpdated(entityDataAccessor)
    }

    override fun addAdditionalSaveData(compoundTag: CompoundTag) {
        compoundTag.putFloat("visCap", visCapacity)
        compoundTag.putFloat("storedVis", vis)
        compoundTag.putFloat("flux", flux)
        compoundTag.putFloat("fluxAffinity", fluxAffinity)

        if(!traits.isEmpty()) {
            val traitsList = ListTag()
            traits.forEach { traitsList.add(StringTag.valueOf(NODE_TRAIT_REGISTRY_INTERNAL.getKey(it).toString())) }
            compoundTag.put("traits", traitsList)
            //val traitsTag = CompoundTag()
            //AuraNodeTrait.LIST_CODEC.encode(traits, NbtOps.INSTANCE, traitsTag)
        }
    }

    //TODO: Make aura nodes pickable
    override fun isPickable(): Boolean {
        return false
    }

    override fun tick() {
        super.tick()
        if(!level().isClientSide) {
            entityData.set(VIS_DATA, vis)
            entityData.set(FLUX_DATA, flux)
        }
        else {
            vis = entityData.get(VIS_DATA)
            flux = entityData.get(FLUX_DATA)
        }
        if(vis < maxVis)
            vis += traits.fold(max(0f, min(.01f, maxVis-vis))){ l, t -> t.onGenerateVis(level(), this, l) }

        traits.forEach { it.onTick(level(), this) }

        if(level().gameTime % 20L == 0L) {
            level().addParticle(WispParticleData(0.2f + visCapacity/150f, 1f, 1f, 1f, 0.25f, 4f, needsRevealing = true), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0)
            if (flux > 5 && random.nextFloat() < (.25f - 1f / flux) / 4)
                if (releaseFluxAtLocation(level(), pos, 1f, listOf(this)))
                    flux--
            else if(flux > 10 && random.nextFloat() < (.25f - 1f / flux) / 16)
                triggerFluxEvent(level(), pos, this)
        }
    }


    companion object{
        val VIS_DATA = SynchedEntityData.defineId(AuraNodeEntity::class.java, EntityDataSerializers.FLOAT)
        val FLUX_DATA = SynchedEntityData.defineId(AuraNodeEntity::class.java, EntityDataSerializers.FLOAT)
    }
}