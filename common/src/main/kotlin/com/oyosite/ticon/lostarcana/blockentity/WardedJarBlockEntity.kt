package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.item.SINGLE_FLUID_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.util.ImmutableFluidStack.Companion.immutableCopy
import dev.architectury.fluid.FluidStack
import dev.architectury.hooks.fluid.FluidStackHooks
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids

class WardedJarBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(WARDED_JAR_BLOCK_ENTITY.value(), blockPos, blockState) {

    var fluidContents: FluidStack// = FluidStack.create(Fluids.WATER, maxFluidAmount/2)
        get() = components().get(SINGLE_FLUID_STORAGE_COMPONENT)?.copy?: FluidStack.empty()
        set(value) {
            //field = value
            applyComponents(components(), DataComponentPatch.builder().set(SINGLE_FLUID_STORAGE_COMPONENT, value.immutableCopy).build())
            setChanged()
        }

    var virtualContents: FluidStack? = null

    val maxFluidAmount get() = CAPACITY * FluidStackHooks.bucketAmount()

    fun isFluidValid(fluid: FluidStack) = true

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        //applyImplicitComponents()
        //compoundTag.put("fluid", FluidStackHooks.write(provider, fluidContents, CompoundTag()))
        //setComponents(components())
        //applyComponents(components(), DataComponentPatch.builder().set(SINGLE_FLUID_STORAGE_COMPONENT, fluidContents.immutableCopy).build())
    }

    override fun collectImplicitComponents(builder: DataComponentMap.Builder) {
        //builder.set(SINGLE_FLUID_STORAGE_COMPONENT, fluidContents.immutableCopy)
    }

    override fun applyImplicitComponents(dataComponentInput: DataComponentInput) {
        //fluidContents = dataComponentInput.get(SINGLE_FLUID_STORAGE_COMPONENT)?.copy ?: FluidStack.empty()
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {

        //fluidContents = components().get(SINGLE_FLUID_STORAGE_COMPONENT)?.copy?: FluidStack.empty()
        //fluidContents = FluidStack.read(provider, compoundTag.getCompound("fluid")).getOrNull()?:fluidContents//.getOrElse(FluidStack::empty)
        setChanged()

    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag? {
        return saveWithoutMetadata(provider)
    }

    companion object{
        val CAPACITY = 64L
    }
}