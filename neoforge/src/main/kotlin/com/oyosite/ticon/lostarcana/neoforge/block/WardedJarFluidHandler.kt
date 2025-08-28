package com.oyosite.ticon.lostarcana.neoforge.block

import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.capability.IFluidHandler

class WardedJarFluidHandler(val be: WardedJarBlockEntity): IFluidHandler {
    override fun getTanks(): Int = 1

    override fun getFluidInTank(i: Int): FluidStack = FluidStackHooksForge.toForge(be.fluidContents)

    override fun getTankCapacity(i: Int): Int = be.maxFluidAmount.toInt()

    override fun isFluidValid(i: Int, fluidStack: FluidStack): Boolean = be.isFluidValid(FluidStackHooksForge.fromForge(fluidStack))

    override fun fill(
        fluidStack: FluidStack,
        fluidAction: IFluidHandler.FluidAction
    ): Int {
        val contents = FluidStackHooksForge.toForge(be.fluidContents)
        if(!contents.isEmpty && !FluidStack.isSameFluidSameComponents(fluidStack, contents))return 0
        val space = be.maxFluidAmount.toInt() - contents.amount
        if(space == 0) return 0
        val amt = fluidStack.amount
        TODO()
    }

    override fun drain(
        fluidStack: FluidStack,
        fluidAction: IFluidHandler.FluidAction
    ): FluidStack {
        TODO("Not yet implemented")
    }

    override fun drain(
        i: Int,
        fluidAction: IFluidHandler.FluidAction
    ): FluidStack {
        TODO("Not yet implemented")
    }
}