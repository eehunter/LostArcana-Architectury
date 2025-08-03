package com.oyosite.ticon.lostarcana.item

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.AspectStacks
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.aspect.AspectStack
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.aspect.times
import io.netty.buffer.ByteBuf
import net.minecraft.core.component.DataComponentType
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec

val ASPECT_CODEC: Codec<AspectStack> = RecordCodecBuilder.create { it.group(
    Codec.STRING.fieldOf("aspect").forGetter{ it.aspect.id.toString() },
        Codec.INT.fieldOf("amount").forGetter{ it.amount },
    ).apply(it, ::AspectStack) }

val ASPECTS_CODEC = Codec.list(ASPECT_CODEC)

val ASPECT_STREAM_CODEC = StreamCodec.of<RegistryFriendlyByteBuf, AspectStack>({ buf, aspectStack ->
    buf.writeInt(aspectStack.amount)
    Identifier.STREAM_CODEC.encode(buf, aspectStack.aspect.id)
}){ buf ->
    val amount = buf.readInt()
    val id = Identifier.STREAM_CODEC.decode(buf)
    amount * buf.registryAccess().registry(AspectRegistry.ASPECT_REGISTRY_KEY).get().get(id)!!
}

val ASPECTS_STREAM_CODEC = StreamCodec.of<RegistryFriendlyByteBuf, AspectStacks>({ buf, aspects ->
    buf.writeInt(aspects.size)
    for(aspect in aspects){
        ASPECT_STREAM_CODEC.encode(buf, aspect)
    }
}){ buf ->
    val aspects = mutableListOf<AspectStack>()
    for(i in 0 until buf.readInt()){
        aspects += ASPECT_STREAM_CODEC.decode(buf)
    }
    aspects
}

val ASPECT_COMPONENT: DataComponentType<AspectStack> = DataComponentType.builder<AspectStack>().networkSynchronized(ASPECT_STREAM_CODEC).persistent(ASPECT_CODEC).build()
val ASPECTS_COMPONENT: DataComponentType<AspectStacks> = DataComponentType.builder<AspectStacks>().networkSynchronized(ASPECTS_STREAM_CODEC).persistent(ASPECTS_CODEC).build()

val VIS_STORAGE_COMPONENT: DataComponentType<Float> = DataComponentType.builder<Float>().persistent(Codec.FLOAT).networkSynchronized(StreamCodec.of(ByteBuf::writeFloat, ByteBuf::readFloat)).build()

val WAND_CAP: DataComponentType<CastingItemComponent> = DataComponentType.builder<CastingItemComponent>().persistent(CastingItemComponent.CODEC).networkSynchronized(CastingItemComponent.STREAM_CODEC).build()
val WAND_CAP_2: DataComponentType<CastingItemComponent> = DataComponentType.builder<CastingItemComponent>().persistent(CastingItemComponent.CODEC).networkSynchronized(CastingItemComponent.STREAM_CODEC).build()
val WAND_CAP_3: DataComponentType<CastingItemComponent> = DataComponentType.builder<CastingItemComponent>().persistent(CastingItemComponent.CODEC).networkSynchronized(CastingItemComponent.STREAM_CODEC).build()

val WAND_CORE: DataComponentType<CastingItemComponent> = DataComponentType.builder<CastingItemComponent>().persistent(CastingItemComponent.CODEC).networkSynchronized(CastingItemComponent.STREAM_CODEC).build()

//val RESONATOR: DataComponentType<CastingItemComponent> = DataComponentType.builder<CastingItemComponent>().persistent(CastingItemComponent.CODEC).networkSynchronized(CastingItemComponent.STREAM_CODEC).build()
