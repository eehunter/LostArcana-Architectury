package com.oyosite.ticon.lostarcana.entity

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.util.auraSources
import com.oyosite.ticon.lostarcana.util.releaseFluxAtLocation
import com.oyosite.ticon.lostarcana.util.triggerFluxEvent
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import kotlin.math.min

class AuraNodeEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level), AuraSource {

    override var vis: Float = 0f
    override var flux: Float =  0f
    private var visCapacity = 100f
    val maxVis = min(visCapacity-flux*.8f, 10f)

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

        level()?.auraSources?.add(this)
    }

    override fun onSyncedDataUpdated(list: List<SynchedEntityData.DataValue<*>>) {
        super.onSyncedDataUpdated(list)
        //list.forEach { if(it.id == VIS_DATA.id) vis = it.value as Float }
        level()?.auraSources?.add(this)
    }

    override fun onSyncedDataUpdated(entityDataAccessor: EntityDataAccessor<*>) {
        super.onSyncedDataUpdated(entityDataAccessor)
    }

    override fun addAdditionalSaveData(compoundTag: CompoundTag) {
        compoundTag.putFloat("visCap", visCapacity)
        compoundTag.putFloat("storedVis", vis)
        compoundTag.putFloat("flux", flux)
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
        vis += .01f
        if (vis > maxVis) vis = maxVis

        if(level().gameTime % 20L == 0L) {
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