package com.oyosite.ticon.lostarcana.entity

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.util.auraSources
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

class AuraNodeEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level), AuraSource {

    override var vis: Float = 0f
    val maxVis = 100f

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
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {
        vis = compoundTag.getFloat("storedVis")
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
        compoundTag.putFloat("storedVis", vis)
    }

    //TODO: Make aura nodes pickable
    override fun isPickable(): Boolean {
        return false
    }

    override fun tick() {
        super.tick()
        if(!level().isClientSide) entityData.set(VIS_DATA, vis)
        else vis = entityData.get(VIS_DATA)
        vis += .01f
        if (vis > maxVis) vis = maxVis
    }


    companion object{
        val VIS_DATA = SynchedEntityData.defineId(AuraNodeEntity::class.java, EntityDataSerializers.FLOAT)
    }
}