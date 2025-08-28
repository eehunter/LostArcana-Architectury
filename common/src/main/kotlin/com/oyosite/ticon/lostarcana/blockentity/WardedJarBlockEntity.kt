package com.oyosite.ticon.lostarcana.blockentity

import dev.architectury.fluid.FluidStack
import dev.architectury.hooks.fluid.FluidStackHooks
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import kotlin.jvm.optionals.getOrElse

class WardedJarBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(WARDED_JAR_BLOCK_ENTITY.value(), blockPos, blockState) {

    var fluidContents: FluidStack = FluidStack.create(Fluids.WATER, maxFluidAmount/2)

    val maxFluidAmount get() = CAPACITY * FluidStackHooks.bucketAmount()

    fun isFluidValid(fluid: FluidStack) = true

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        compoundTag.put("fluid", FluidStackHooks.write(provider, fluidContents, CompoundTag()))
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        fluidContents = FluidStack.read(provider, compoundTag.getCompound("fluid")).getOrElse(FluidStack::empty)
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockUpdatePacket(blockPos, blockState)
    }

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag? {
        return saveWithoutMetadata(provider)
    }

    companion object{
        val CAPACITY = 64L
    }
}