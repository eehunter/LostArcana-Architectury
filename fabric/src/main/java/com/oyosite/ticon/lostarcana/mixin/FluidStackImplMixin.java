package com.oyosite.ticon.lostarcana.mixin;

import dev.architectury.fluid.fabric.FluidStackImpl;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidStackImpl.Pair.class)
public class FluidStackImplMixin {

    @Shadow
    public PatchedDataComponentMap components;

    @Inject(method = "getPatch()Lnet/minecraft/core/component/DataComponentPatch;", at = @At("RETURN"), cancellable = true)
    void captureComponentsDebug(CallbackInfoReturnable<DataComponentPatch> cir){
        //System.out.println("Patch: " + cir.getReturnValue());
        if(cir.getReturnValue().isEmpty()) {
            var builder = DataComponentPatch.builder();
            components.forEach(builder::set);
            cir.setReturnValue(builder.build());
        }
    }
}
