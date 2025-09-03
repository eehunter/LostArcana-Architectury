package com.oyosite.ticon.lostarcana.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
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
    //Lnet/minecraft/core/component/PatchedDataComponentMap;asPatch()Lnet/minecraft/core/component/DataComponentPatch;

    /*@ModifyExpressionValue(method = "getPatch()Lnet/minecraft/core/component/DataComponentPatch;", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/component/PatchedDataComponentMap;asPatch()Lnet/minecraft/core/component/DataComponentPatch;"))
    DataComponentPatch fixPatch(DataComponentPatch original){
        //if(components.isEmpty())return original;
        var builder = DataComponentPatch.builder();
        components.forEach(builder::set);
        return builder.build();
    }*/

    @Inject(method = "getPatch()Lnet/minecraft/core/component/DataComponentPatch;", at = @At("RETURN"), cancellable = true)
    void captureComponentsDebug(CallbackInfoReturnable<DataComponentPatch> cir){
        //System.out.println("Patch: " + cir.getReturnValue());
        if(!components.isEmpty() && cir.getReturnValue().isEmpty()) {
            var builder = DataComponentPatch.builder();
            components.forEach(builder::set);
            cir.setReturnValue(builder.build());
        }
    }
}
