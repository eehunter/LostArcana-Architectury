@file:JvmName("ClientPlatformKtImpl")
package com.oyosite.ticon.lostarcana.client.neoforge

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent
import net.neoforged.neoforge.client.textures.FluidSpriteCache


fun setFilterSave(texture: AbstractTexture, filter: Boolean, mipmap: Boolean){
    texture.setBlurMipmap(filter, mipmap)
}

fun restoreLastFilter(texture: AbstractTexture){
    texture.restoreLastBlurMipmap()
}

fun <U: ParticleOptions, T: ParticleType<U>>particleFactoryCallback(particle: T, factory: (SpriteSet)-> ParticleProvider<U>): (RegisterParticleProvidersEvent)->Unit =
    { it.registerSpriteSet(particle, factory) }

fun <U: ParticleOptions, T: ParticleType<U>>platformRegisterParticleFactory(particle: T, factory: (SpriteSet)-> ParticleProvider<U>) {
    LostArcanaNeoForge.factoryRegistrationCallbacks.add(particleFactoryCallback(particle, factory))
}

fun platformGetWaterSprite(): TextureAtlasSprite = FluidSpriteCache.getSprite(Identifier.parse("block/water_still"))