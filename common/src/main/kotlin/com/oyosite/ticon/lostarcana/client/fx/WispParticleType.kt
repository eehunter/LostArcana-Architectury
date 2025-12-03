package com.oyosite.ticon.lostarcana.client.fx

import com.mojang.serialization.MapCodec
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec


/*Based on Botania code*/
class WispParticleType() : ParticleType<WispParticleData>(false) {



    override fun codec(): MapCodec<WispParticleData> {
        return WispParticleData.CODEC
    }

    override fun streamCodec(): StreamCodec<in RegistryFriendlyByteBuf, WispParticleData> {
        return WispParticleData.STREAM_CODEC
    }

    companion object{

    }
}