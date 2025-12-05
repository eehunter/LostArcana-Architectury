package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.item.SINGLE_FLUID_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.util.ImmutableFluidStack.Companion.immutableCopy
import dev.architectury.fluid.FluidStack
import dev.architectury.hooks.fluid.FluidStackHooks
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class WardedJarBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(WARDED_JAR_BLOCK_ENTITY.value(), blockPos, blockState) {

    var fluidContents: FluidStack
        get() = components().get(SINGLE_FLUID_STORAGE_COMPONENT)?.copy?: FluidStack.empty()
        set(value) {
            applyComponents(components(), DataComponentPatch.builder().set(SINGLE_FLUID_STORAGE_COMPONENT, value.immutableCopy).build())

            //println(value.patch)
            //println(FluidStack.CODEC.encode(value, JsonOps.INSTANCE, JsonObject()))
            //println(components())
            setChanged()
        }

    var virtualContents: FluidStack? = null

    val maxFluidAmount get() = CAPACITY * FluidStackHooks.bucketAmount()

    fun isFluidValid(fluid: FluidStack) = true


    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
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