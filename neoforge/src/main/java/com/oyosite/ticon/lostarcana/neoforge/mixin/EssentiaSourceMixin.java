package com.oyosite.ticon.lostarcana.neoforge.mixin;

import com.oyosite.ticon.lostarcana.block.fluid.EssentiaSource;
import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EssentiaSource.class)
public abstract class EssentiaSourceMixin extends BaseFlowingFluid {
    protected EssentiaSourceMixin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull FluidType getFluidType() {
        return LostArcanaNeoForge.Companion.getESSENTIA_FLUID_TYPE().value();
    }
}
