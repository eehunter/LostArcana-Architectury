package com.oyosite.ticon.lostarcana.util

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.component.DataComponentType
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

interface ImmutableItemStack {
    val copy: ItemStack

    operator fun <T> get(component: DataComponentType<T>): T?

    val count: Int

    val isEmpty: Boolean

    val item: Item

    companion object{
        val ItemStack.immutableCopy: ImmutableItemStack get() = object : ImmutableItemStack{
            val stack = this@immutableCopy.copy()
            override val copy: ItemStack
                get() = stack.copy()
            override fun <T> get(component: DataComponentType<T>) = stack[component]
            override val count get() = stack.count
            override val isEmpty: Boolean get() = stack.isEmpty
            override val item get() = stack.item
        }

        val CODEC: Codec<ImmutableItemStack> = RecordCodecBuilder.create {
            it.group(
                ItemStack.CODEC.fieldOf("stack").forGetter(ImmutableItemStack::copy)
            ).apply(it){ stack: ItemStack -> stack.immutableCopy }
        }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ImmutableItemStack> = StreamCodec.of<RegistryFriendlyByteBuf, ImmutableItemStack>({ buf, immutableStack ->
            ItemStack.STREAM_CODEC.encode(buf, immutableStack.copy)
        }){ buf ->
            ItemStack.STREAM_CODEC.decode(buf).immutableCopy
        }
    }
}