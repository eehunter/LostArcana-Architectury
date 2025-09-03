package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.block.fluid.EssentiaSource;
import com.oyosite.ticon.lostarcana.fabric.block.BucketUtilsKt;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.impl.transfer.fluid.EmptyBucketStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnstableApiUsage")
@Mixin(EmptyBucketStorage.class)
public class EmptyBucketStorageMixin {
    @Shadow
    @Final
    private ContainerItemContext context;

    @Inject(method = "insert(Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/material/Fluid;getBucket()Lnet/minecraft/world/item/Item;", shift = At.Shift.BEFORE), cancellable = true)
    void hookEssentiaBucket(FluidVariant resource, long maxAmount, TransactionContext transaction, CallbackInfoReturnable<Long> cir){
        if(resource.getFluid() instanceof EssentiaSource){
            cir.setReturnValue(BucketUtilsKt.fillEssentiaBucket(resource, maxAmount, transaction, context));
        }
    }
}
