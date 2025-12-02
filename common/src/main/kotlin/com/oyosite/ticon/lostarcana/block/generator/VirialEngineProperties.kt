package com.oyosite.ticon.lostarcana.block.generator

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec

data class VirialEngineProperties(val visGeneration: Double = 0.25, val fluxLeakage: Double = 0.01, val breakdownChance: Double = 0.005) {


    companion object{
        val CODEC = RecordCodecBuilder.create { it.group(
            Codec.DOUBLE.fieldOf("visGeneration").forGetter(VirialEngineProperties::visGeneration),
            Codec.DOUBLE.fieldOf("fluxLeakage").forGetter(VirialEngineProperties::fluxLeakage),
            Codec.DOUBLE.fieldOf("breakdownChance").forGetter(VirialEngineProperties::breakdownChance),
        ).apply(it, ::VirialEngineProperties) }

        val STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, VirialEngineProperties::visGeneration,
            ByteBufCodecs.DOUBLE, VirialEngineProperties::fluxLeakage,
            ByteBufCodecs.DOUBLE, VirialEngineProperties::breakdownChance,
            ::VirialEngineProperties
        )
    }
}