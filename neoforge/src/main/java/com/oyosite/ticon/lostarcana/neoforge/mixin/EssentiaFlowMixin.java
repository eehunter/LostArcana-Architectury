package com.oyosite.ticon.lostarcana.neoforge.mixin;

import com.oyosite.ticon.lostarcana.block.fluid.EssentiaFlow;
import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EssentiaFlow.class)
public abstract class EssentiaFlowMixin extends BaseFlowingFluid {
    protected EssentiaFlowMixin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull FluidType getFluidType() {
        return LostArcanaNeoForge.Companion.getESSENTIA_FLUID_TYPE().value();
    }
}
