package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.block.fluid.EssentiaFluidAttributes;
import com.oyosite.ticon.lostarcana.fabric.client.ClientUtilKt;
import com.oyosite.ticon.lostarcana.item.DataComponentsKt;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "dev.architectury.core.fluid.fabric.ArchitecturyFluidRenderingFabric")
public class ArchitecturyFluidRenderingFabricMixin {
    @Shadow
    @Final
    private ArchitecturyFluidAttributes attributes;

    @Inject(method = "getColor(Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I", at = @At("HEAD"), cancellable = true)
    void hookEssentiaColor(FluidVariant variant, BlockAndTintGetter view, BlockPos pos, CallbackInfoReturnable<Integer> cir){
        if(attributes instanceof EssentiaFluidAttributes) {
            var color = ClientUtilKt.getAspectColor(variant);
            cir.setReturnValue(color);
            //var optionalAspect = variant.getComponents().get(DataComponentsKt.getRAW_ASPECT_COMPONENT());
            //if(optionalAspect == null || optionalAspect.isEmpty())return;
            //var aspect = optionalAspect.get();
            //cir.setReturnValue(aspect)
        }
    }
}
