package com.oyosite.ticon.lostarcana.neoforge.mixin;

import com.oyosite.ticon.lostarcana.aspect.AspectsKt;
import com.oyosite.ticon.lostarcana.block.fluid.FluidsKt;
import com.oyosite.ticon.lostarcana.item.DataComponentsKt;
import com.oyosite.ticon.lostarcana.item.EssentiaBucketItem;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(FluidBucketWrapper.class)
public class FluidBucketWrapperMixin {
    @Shadow
    protected ItemStack container;

    @Inject(method = "Lnet/neoforged/neoforge/fluids/capability/wrappers/FluidBucketWrapper;getFluid()Lnet/neoforged/neoforge/fluids/FluidStack;", at = @At(value = "INVOKE_ASSIGN", shift = At.Shift.AFTER, target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    void getEssentiaFluid(CallbackInfoReturnable<FluidStack> cir, Item item){
        if(item instanceof EssentiaBucketItem){
            cir.setReturnValue(new FluidStack((Holder<Fluid>)((Holder<?>)FluidsKt.getESSENTIA_FLUID()), FluidType.BUCKET_VOLUME, DataComponentPatch.builder().set(DataComponentsKt.getRAW_ASPECT_COMPONENT(),
                    Objects.requireNonNullElse(
                            container.get(DataComponentsKt.getRAW_ASPECT_COMPONENT()),
                            AspectsKt.getAER()
                    )
            ).build()));
        }
    }
}
