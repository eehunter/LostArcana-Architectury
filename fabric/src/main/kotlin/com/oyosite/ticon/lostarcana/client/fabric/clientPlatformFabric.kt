@file:JvmName("ClientPlatformKtImpl")
package com.oyosite.ticon.lostarcana.client.fabric

import com.oyosite.ticon.lostarcana.mixin.BlockRenderDispatcherAccessor
import com.oyosite.ticon.lostarcana.mixin.LiquidBlockRendererAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.TextureAtlasSprite


@Environment(EnvType.CLIENT)
fun platformGetWaterSprite(): TextureAtlasSprite = ((Minecraft.getInstance().blockRenderer as BlockRenderDispatcherAccessor).liquidBlockRenderer as LiquidBlockRendererAccessor).waterIcons[0]