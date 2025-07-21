package com.oyosite.ticon.lostarcana.advancement

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.advancement.ThaumometerScanCriterionTrigger.TriggerInstance
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.advancements.critereon.SimpleCriterionTrigger
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import java.util.Optional

class ThaumometerScanCriterionTrigger: SimpleCriterionTrigger<TriggerInstance>() {
    override fun codec(): Codec<TriggerInstance> = CODEC

    fun trigger(player: ServerPlayer, scanObject: ResourceLocation, scanObjectType: ResourceLocation){
        this.trigger(player){ it.scanObject == scanObject && it.scanObjectType == scanObjectType }
    }

    companion object{
        val CODEC = RecordCodecBuilder.create {
            it.group(
                Identifier.CODEC.fieldOf("scanObject").forGetter(TriggerInstance::scanObject),
                Identifier.CODEC.fieldOf("scanObjectType").forGetter(TriggerInstance::scanObjectType)
            ).apply(it, ::TriggerInstance)
        }

        fun scan(scanObject: ResourceLocation, scanObjectType: ResourceLocation) =
            THAUMOMETER_SCAN_TRIGGER.value().createCriterion(TriggerInstance(scanObject, scanObjectType))

    }

    @JvmRecord
    data class TriggerInstance(val scanObject: ResourceLocation, val scanObjectType: ResourceLocation): SimpleInstance{
        override fun player(): Optional<ContextAwarePredicate> = Optional.empty()
    }
}