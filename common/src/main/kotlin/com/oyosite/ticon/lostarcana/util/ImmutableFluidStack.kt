package com.oyosite.ticon.lostarcana.util

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.architectury.fluid.FluidStack
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.component.PatchedDataComponentMap
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.level.material.Fluid

interface ImmutableFluidStack {
    val copy: FluidStack

    operator fun <T> get(component: DataComponentType<T>): T?

    val components: PatchedDataComponentMap

    val amount: Long

    val isEmpty: Boolean

    val fluid: Fluid

    companion object{
        val FluidStack.immutableCopy: ImmutableFluidStack get() = object : ImmutableFluidStack {
            val stack = this@immutableCopy.copy()
            override val copy: FluidStack
                get() = stack.copy()

            override fun <T> get(component: DataComponentType<T>): T? = stack.get(component)

            override val components get() = stack.components

            override val amount: Long
                get() = stack.amount
            override val isEmpty: Boolean
                get() = stack.isEmpty
            override val fluid: Fluid
                get() = stack.fluid

            override fun equals(other: Any?): Boolean {
                if(other !is ImmutableFluidStack)return false
                return other.amount == amount && other.fluid == fluid && other.components == components
                //(other as? ImmutableFluidStack)?.copy?.equals(stack) ?: (other as? FluidStack)?.equals(stack) ?: false
            }

            override fun hashCode(): Int = stack.hashCode()

            override fun toString(): String = stack.components.toString()
        }

        val CODEC: Codec<ImmutableFluidStack> = FluidStack.CODEC.xmap({it.immutableCopy}, ImmutableFluidStack::copy)
            /*RecordCodecBuilder.create {
            it.group(
                FluidStack.CODEC.fieldOf("stack").forGetter(ImmutableFluidStack::copy)
            ).apply(it){ stack: FluidStack -> stack.immutableCopy }
        }*/

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ImmutableFluidStack> = StreamCodec.of<RegistryFriendlyByteBuf, ImmutableFluidStack>({ buf, immutableStack ->
            FluidStack.STREAM_CODEC.encode(buf, immutableStack.copy)
        }){ buf ->
            FluidStack.STREAM_CODEC.decode(buf).immutableCopy
        }
    }
}