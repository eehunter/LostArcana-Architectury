package com.oyosite.ticon.lostarcana.client.fx

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.client.platformRegisterParticleFactory
import com.oyosite.ticon.lostarcana.util.platformRegisterParticleType
import dev.architectury.registry.client.particle.ParticleProviderRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.particles.SimpleParticleType

val WISP_PARTICLE = WispParticleType()


fun registerParticles(){
    platformRegisterParticleType(LostArcana.id("wisp"), WISP_PARTICLE)
}

@Environment(EnvType.CLIENT)
fun registerParticlesClient(){
    println("Registering Particle")
    platformRegisterParticleFactory(WISP_PARTICLE, ::WispParticleTypeFactory)

}