package com.oyosite.ticon.lostarcana.block.dissolver

import com.oyosite.ticon.lostarcana.blockentity.DISSOLVER_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.item.SINGLE_FLUID_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.util.ImmutableFluidStack.Companion.immutableCopy
import dev.architectury.fluid.FluidStack
import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class DissolverBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(DISSOLVER_BLOCK_ENTITY.value(), blockPos, blockState) {

    var fluidContents: FluidStack
    get() = components().get(SINGLE_FLUID_STORAGE_COMPONENT)?.copy?: FluidStack.empty()
        set(value) {
            applyComponents(components(), DataComponentPatch.builder().set(SINGLE_FLUID_STORAGE_COMPONENT, value.immutableCopy).build())
            setChanged()
        }
}