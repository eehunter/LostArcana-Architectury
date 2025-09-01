package com.oyosite.ticon.lostarcana.neoforge.block

import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.capability.IFluidHandler
import kotlin.math.min

@JvmInline
value class WardedJarFluidHandler(val be: WardedJarBlockEntity): IFluidHandler {
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
        if(space >= amt){
            if(fluidAction == IFluidHandler.FluidAction.EXECUTE) {
                if(!contents.isEmpty) {
                    contents.grow(amt)
                    be.fluidContents = FluidStackHooksForge.fromForge(contents)
                } else {
                    be.fluidContents = FluidStackHooksForge.fromForge(fluidStack.copyWithAmount(amt))
                }
            }
            return amt
        } else {
            if(fluidAction == IFluidHandler.FluidAction.EXECUTE) {
                if(!contents.isEmpty) {
                    contents.grow(amt)
                    be.fluidContents = FluidStackHooksForge.fromForge(contents)
                } else {
                    be.fluidContents = FluidStackHooksForge.fromForge(fluidStack.copyWithAmount(amt))
                }
            }
            return space
        }
    }

    override fun drain(
        fluidStack: FluidStack,
        fluidAction: IFluidHandler.FluidAction
    ): FluidStack {
        val contents = FluidStackHooksForge.toForge(be.fluidContents)
        if(contents.isEmpty || !FluidStack.isSameFluidSameComponents(fluidStack, contents))return FluidStack.EMPTY
        val amt = min(contents.amount, fluidStack.amount)
        if(amt == 0) return FluidStack.EMPTY
        val output = contents.copyWithAmount(amt)
        if(fluidAction.execute()){
            be.fluidContents = FluidStackHooksForge.fromForge(contents.copyWithAmount(contents.amount - amt))
        }
        return output
    }

    override fun drain(
        i: Int,
        fluidAction: IFluidHandler.FluidAction
    ): FluidStack {
        val contents = FluidStackHooksForge.toForge(be.fluidContents)
        if(contents.isEmpty)return FluidStack.EMPTY
        val output = contents.split(i)
        if(fluidAction.execute()){
            be.fluidContents = FluidStackHooksForge.fromForge(contents)
        }
        return output
    }
}