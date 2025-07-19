package com.oyosite.ticon.lostarcana.entity

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class AuraNodeEntity(entityType: EntityType<*>, level: Level) : Entity(entityType, level) {
    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        TODO("Not yet implemented")
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {
        TODO("Not yet implemented")
    }

    override fun addAdditionalSaveData(compoundTag: CompoundTag) {
        TODO("Not yet implemented")
    }
}