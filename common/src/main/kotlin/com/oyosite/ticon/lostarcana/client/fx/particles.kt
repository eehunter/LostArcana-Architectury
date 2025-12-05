package com.oyosite.ticon.lostarcana.client.fx

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.client.platformRegisterParticleFactory
import com.oyosite.ticon.lostarcana.util.platformRegisterParticleType
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

val WISP_PARTICLE = WispParticleType()


fun registerParticles(){
    platformRegisterParticleType(LostArcana.id("wisp"), WISP_PARTICLE)
}

@Environment(EnvType.CLIENT)
fun registerParticlesClient(){
    platformRegisterParticleFactory(WISP_PARTICLE, ::WispParticleTypeFactory)

}