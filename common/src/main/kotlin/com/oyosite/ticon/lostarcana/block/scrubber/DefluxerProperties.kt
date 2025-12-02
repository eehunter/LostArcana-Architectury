package com.oyosite.ticon.lostarcana.block.scrubber

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec

data class DefluxerProperties(val fluxRemoval: Double = 0.25, val clogChance: Double = 0.005, ) {


    companion object{
        val CODEC = RecordCodecBuilder.create {
            it.group(
                Codec.DOUBLE.fieldOf("fluxRemoval").forGetter(DefluxerProperties::fluxRemoval),
                Codec.DOUBLE.fieldOf("clogChance").forGetter(DefluxerProperties::clogChance),
            ).apply(it, ::DefluxerProperties)
        }

        val STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, DefluxerProperties::fluxRemoval, ByteBufCodecs.DOUBLE, DefluxerProperties::clogChance, ::DefluxerProperties)
    }
}