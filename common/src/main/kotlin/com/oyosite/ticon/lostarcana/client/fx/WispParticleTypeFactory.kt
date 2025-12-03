package com.oyosite.ticon.lostarcana.client.fx

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet

class WispParticleTypeFactory(val sprite: SpriteSet) : ParticleProvider<WispParticleData>{
    override fun createParticle(
        particleOptions: WispParticleData,
        clientLevel: ClientLevel,
        x: Double,
        y: Double,
        z: Double,
        mx: Double,
        my: Double,
        mz: Double
    ): Particle {
        println("Particle factory called")
        val otpt = FXWisp(clientLevel, x, y, z, mx, my, mz, particleOptions.pSize, particleOptions.red, particleOptions.green, particleOptions.blue, particleOptions.depthTest, particleOptions.maxAgeMultiplier, particleOptions.noClip, particleOptions.gravity, particleOptions.needsRevealing)
        otpt.pickSprite(sprite)
        return otpt
    }


}