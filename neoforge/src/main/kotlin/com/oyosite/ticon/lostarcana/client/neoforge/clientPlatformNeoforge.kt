@file:JvmName("ClientPlatformKtImpl")
package com.oyosite.ticon.lostarcana.client.neoforge

import com.oyosite.ticon.lostarcana.Identifier
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.neoforged.neoforge.client.textures.FluidSpriteCache


fun platformGetWaterSprite(): TextureAtlasSprite = FluidSpriteCache.getSprite(Identifier.parse("block/water_still"))