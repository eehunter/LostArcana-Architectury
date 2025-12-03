package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.client.fabric.ExtendedTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;


/*
 * This class is copied almost verbatim from Botania's WIP 1.21.1 port
 *
 * https://github.com/VazkiiMods/Botania/blob/1.21.1-porting/Fabric/src/main/java/vazkii/botania/fabric/mixin/client/AbstractTextureFabricMixin.java
 * */
@Mixin(AbstractTexture.class)
public abstract class AbstractTextureFabricMixin implements ExtendedTexture {
    @Shadow
    protected boolean blur;

    @Shadow
    protected boolean mipmap;

    @Shadow
    public abstract void setFilter(boolean bilinear, boolean mipmap);

    @Unique
    private boolean lastBilinear;

    @Unique
    private boolean lastMipmap;

    @Override
    public void lostarcana$setFilterSave(boolean bilinear, boolean mipmap) {
        this.lastBilinear = this.blur;
        this.lastMipmap = this.mipmap;
        setFilter(bilinear, mipmap);
    }

    @Override
    public void lostarcana$restoreLastFilter() {
        setFilter(this.lastBilinear, this.lastMipmap);
    }
}
