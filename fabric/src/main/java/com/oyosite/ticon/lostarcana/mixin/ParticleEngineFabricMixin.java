package com.oyosite.ticon.lostarcana.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;

import com.oyosite.ticon.lostarcana.client.fx.FXWisp;
import com.oyosite.ticon.lostarcana.client.fx.LostArcanaParticleRenderType;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LightTexture;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;


/*
 * This class is copied almost verbatim from Botania's WIP 1.21.1 port
 *
 * https://github.com/VazkiiMods/Botania/blob/acb6e0a2d3ba839700724352370570cf65903587/Fabric/src/main/java/vazkii/botania/fabric/mixin/client/ParticleEngineFabricMixin.java
 * */
@Mixin(ParticleEngine.class)
public class ParticleEngineFabricMixin {
    @Mutable
    @Final
    @Shadow
    private static List<ParticleRenderType> RENDER_ORDER;

    @Inject(at = @At("RETURN"), method = "<clinit>")
    private static void addTypes(CallbackInfo ci) {
        RENDER_ORDER = ImmutableList.<ParticleRenderType>builder().addAll(RENDER_ORDER)
                .add(FXWisp.Companion.getNORMAL_RENDER(), FXWisp.Companion.getDIW_RENDER())
                .build();
    }

    // FIXME, HACK: ParticleRenderType no longer has an end() method, but our particle render types need to reset things
    // (presumably the render types should define shaders instead of doing things they need to reset afterwards)
    @Inject(
            method = "render",
            at = @At(value = "JUMP", opcode = Opcodes.GOTO),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferUploader;drawWithShader(Lcom/mojang/blaze3d/vertex/MeshData;)V"),
                    to = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;depthMask(Z)V")
            )
    )
    private void afterRenderedType(LightTexture lightTexture, Camera camera, float partialTick, CallbackInfo ci,
                                   @Local ParticleRenderType particleRenderType) {
        if (particleRenderType instanceof LostArcanaParticleRenderType lostArcanaParticleRenderType) {
            lostArcanaParticleRenderType.end();
        }
    }
}
