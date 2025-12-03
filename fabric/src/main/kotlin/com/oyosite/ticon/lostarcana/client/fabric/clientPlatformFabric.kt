@file:JvmName("ClientPlatformKtImpl")
package com.oyosite.ticon.lostarcana.client.fabric

import com.oyosite.ticon.lostarcana.mixin.BlockRenderDispatcherAccessor
import com.oyosite.ticon.lostarcana.mixin.LiquidBlockRendererAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType


fun setFilterSave(texture: AbstractTexture, filter: Boolean, mipmap: Boolean){
    (texture as ExtendedTexture).`lostarcana$setFilterSave`(filter, mipmap)
}

fun restoreLastFilter(texture: AbstractTexture){
    (texture as ExtendedTexture).`lostarcana$restoreLastFilter`()
}

fun <U: ParticleOptions, T: ParticleType<U>>platformRegisterParticleFactory(particle: T, factory: (SpriteSet)-> ParticleProvider<U>) =
    ParticleFactoryRegistry.getInstance().register(particle, ParticleFactoryRegistry.PendingParticleFactory(factory))

@Environment(EnvType.CLIENT)
fun platformGetWaterSprite(): TextureAtlasSprite = ((Minecraft.getInstance().blockRenderer as BlockRenderDispatcherAccessor).liquidBlockRenderer as LiquidBlockRendererAccessor).waterIcons[0]