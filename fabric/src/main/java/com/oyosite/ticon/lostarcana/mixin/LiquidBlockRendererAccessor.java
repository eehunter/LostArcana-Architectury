package com.oyosite.ticon.lostarcana.mixin;

import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LiquidBlockRenderer.class)
public interface LiquidBlockRendererAccessor {

    @Accessor
    TextureAtlasSprite[] getWaterIcons();
}
