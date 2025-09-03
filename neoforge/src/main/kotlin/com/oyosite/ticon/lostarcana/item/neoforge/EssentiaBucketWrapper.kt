package com.oyosite.ticon.lostarcana.item.neoforge

import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper

class EssentiaBucketWrapper(container: ItemStack) : FluidBucketWrapper(container) {
    override fun getFluid(): FluidStack = super.getFluid().apply{ applyComponents(container.components); println("applied components") }
}