package com.oyosite.ticon.lostarcana.entity

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class AuraNodeEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level) {

    var vis: Float = 0f
    val maxVis = 100f

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {

        builder.define(VIS_DATA, vis)
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {
        vis = compoundTag.getFloat("storedVis")
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