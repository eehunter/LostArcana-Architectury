package com.oyosite.ticon.lostarcana.client

import dev.architectury.injectables.annotations.ExpectPlatform
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.texture.TextureAtlasSprite


@ExpectPlatform
@Environment(EnvType.CLIENT)
fun platformGetWaterSprite(): TextureAtlasSprite = throw AssertionError("No platform implementation.")
