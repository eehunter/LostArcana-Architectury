package com.oyosite.ticon.lostarcana.block.fluid

import com.oyosite.ticon.lostarcana.item.RAW_ASPECT_COMPONENT
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes
import dev.architectury.fluid.FluidStack
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState

class EssentiaFluidAttributes(flowingFluid: ()->Fluid, sourceFluid: ()->Fluid) : SimpleArchitecturyFluidAttributes(flowingFluid, sourceFluid) {
    override fun getColor(stack: FluidStack?): Int {
        return stack?.get(RAW_ASPECT_COMPONENT)?.color?.or(0xFF000000u)?.toInt()?:super.getColor(stack)
    }

    override fun getColor(state: FluidState?, level: BlockAndTintGetter?, pos: BlockPos?): Int {
        return level?.getBlockEntity(pos?:return super.getColor(state, level, pos))?.components()?.get(RAW_ASPECT_COMPONENT)?.color?.or(0xFF000000u)?.toInt() ?: super.getColor(state, level, pos)
    }

    companion object{
        fun of(flowingFluid: () -> () -> Fluid, sourceFluid: () -> () -> Fluid) = EssentiaFluidAttributes({flowingFluid()()}, {sourceFluid()()})
    }
}