package com.oyosite.ticon.lostarcana.client

import dev.architectury.injectables.annotations.ExpectPlatform
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType

@ExpectPlatform
fun setFilterSave(texture: AbstractTexture, filter: Boolean, mipmap: Boolean): Unit = throw AssertionError("No platform implementation")

@ExpectPlatform
fun restoreLastFilter(texture: AbstractTexture): Unit = throw AssertionError("No platform implementation")

@ExpectPlatform
fun <U: ParticleOptions, T: ParticleType<U>>platformRegisterParticleFactory(particle: T, factory: (SpriteSet)-> ParticleProvider<U>): Unit = throw AssertionError("No platform implementation.")

@ExpectPlatform
@Environment(EnvType.CLIENT)
fun platformGetWaterSprite(): TextureAtlasSprite = throw AssertionError("No platform implementation.")
