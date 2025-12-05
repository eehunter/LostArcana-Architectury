package com.oyosite.ticon.lostarcana.client.fx

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.codec.StreamCodec

/*Based on Botania code*/
class WispParticleData(
    val pSize: Float,
    val red: Float, val green: Float, val blue: Float, val alpha: Float,
    val maxAgeMultiplier: Float = 1f,
    val depthTest: Boolean = true,
    val noClip: Boolean = true,
    val needsRevealing: Boolean = false,
    val gravity: Float = 0f,
) : ParticleOptions {



    override fun getType(): ParticleType<*> = WISP_PARTICLE


    companion object{
        val CODEC: MapCodec<WispParticleData> =
            RecordCodecBuilder.mapCodec<WispParticleData>{
                it.group(
                    Codec.FLOAT.optionalFieldOf("size", 1f).forGetter(WispParticleData::pSize),
                    Codec.FLOAT.optionalFieldOf("r", 1f).forGetter(WispParticleData::red),
                    Codec.FLOAT.optionalFieldOf("g", 1f).forGetter(WispParticleData::green),
                    Codec.FLOAT.optionalFieldOf("b", 1f).forGetter(WispParticleData::blue),
                    Codec.FLOAT.optionalFieldOf("a", 1f).forGetter(WispParticleData::alpha),
                    Codec.FLOAT.optionalFieldOf("maxAgeMul", 2f).forGetter(WispParticleData::maxAgeMultiplier),
                    Codec.BOOL.optionalFieldOf("depthTest", true).forGetter(WispParticleData::depthTest),
                    Codec.BOOL.optionalFieldOf("noClip", true).forGetter(WispParticleData::noClip),
                    Codec.BOOL.optionalFieldOf("needsRevealing", false).forGetter(WispParticleData::needsRevealing),
                    Codec.FLOAT.optionalFieldOf("gravity", 0.0f).forGetter(WispParticleData::gravity)
                ).apply(it, ::WispParticleData)
            }

        val STREAM_CODEC: StreamCodec<ByteBuf, WispParticleData> = StreamCodec.of<ByteBuf, WispParticleData>(
            { buf: ByteBuf, d: WispParticleData ->
                buf.writeFloat(d.pSize)
                buf.writeFloat(d.red)
                buf.writeFloat(d.green)
                buf.writeFloat(d.blue)
                buf.writeFloat(d.alpha)
                buf.writeFloat(d.maxAgeMultiplier)
                buf.writeBoolean(d.depthTest)
                buf.writeBoolean(d.noClip)
                buf.writeBoolean(d.needsRevealing)
                buf.writeFloat(d.gravity)
            },
            { buf: ByteBuf ->
                WispParticleData(
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readFloat(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readBoolean(),
                    buf.readFloat()
                )
            }
        )
    }
}