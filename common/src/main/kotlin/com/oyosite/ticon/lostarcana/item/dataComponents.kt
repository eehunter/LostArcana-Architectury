package com.oyosite.ticon.lostarcana.item

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.AspectStacks
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.aspect.ASPECT_REGISTRY
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.AspectStack
import com.oyosite.ticon.lostarcana.block.generator.VirialEngineProperties
import com.oyosite.ticon.lostarcana.block.scrubber.DefluxerProperties
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffect
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusHolder
import com.oyosite.ticon.lostarcana.util.ImmutableFluidStack
import io.netty.buffer.ByteBuf
import net.minecraft.core.component.DataComponentType
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec


val RAW_ASPECT_CODEC: Codec<Aspect> = ASPECT_REGISTRY.byNameCodec()
val RAW_ASPECT_STREAM_CODEC: StreamCodec<ByteBuf, Aspect> = StreamCodec.composite(Identifier.STREAM_CODEC, ASPECT_REGISTRY::getKey, ASPECT_REGISTRY::get)
val RAW_ASPECT_COMPONENT: DataComponentType<Aspect> = dataComponentType(RAW_ASPECT_CODEC, RAW_ASPECT_STREAM_CODEC)


val ASPECT_CODEC: Codec<AspectStack> = RecordCodecBuilder.create { b -> b.group(
    Codec.STRING.fieldOf("aspect").forGetter{ it.aspect.id.toString() },
        Codec.INT.fieldOf("amount").forGetter(AspectStack::amount),
    ).apply(b, ::AspectStack) }

val ASPECTS_CODEC: Codec<List<AspectStack>> = Codec.list(ASPECT_CODEC)

val ASPECT_STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, AspectStack> = StreamCodec.composite(
    RAW_ASPECT_STREAM_CODEC, AspectStack::aspect,
    ByteBufCodecs.INT, AspectStack::amount,
    ::AspectStack
)

val ASPECTS_STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, List<AspectStack>> = ASPECT_STREAM_CODEC.apply(ByteBufCodecs.list())

val ASPECT_COMPONENT: DataComponentType<AspectStack> = dataComponentType(ASPECT_CODEC, ASPECT_STREAM_CODEC)
val ASPECTS_COMPONENT: DataComponentType<AspectStacks> = dataComponentType(ASPECTS_CODEC, ASPECTS_STREAM_CODEC)

val VIS_STORAGE_COMPONENT: DataComponentType<Float> = dataComponentType(Codec.FLOAT, ByteBufCodecs.FLOAT)

val WAND_CAP: DataComponentType<CastingItemComponent> = dataComponentType(CastingItemComponent.CODEC, CastingItemComponent.STREAM_CODEC)
val WAND_CAP_2: DataComponentType<CastingItemComponent> = dataComponentType(CastingItemComponent.CODEC, CastingItemComponent.STREAM_CODEC)
val WAND_CAP_3: DataComponentType<CastingItemComponent> = dataComponentType(CastingItemComponent.CODEC, CastingItemComponent.STREAM_CODEC)
val WAND_CORE: DataComponentType<CastingItemComponent> = dataComponentType(CastingItemComponent.CODEC, CastingItemComponent.STREAM_CODEC)

val RESONATOR: DataComponentType<CastingItemComponent> = dataComponentType(CastingItemComponent.CODEC, CastingItemComponent.STREAM_CODEC)

val FOCUS_EFFECT: DataComponentType<CastingFocusEffect> = dataComponentType(CastingFocusEffect.CODEC.codec(), CastingFocusEffect.STREAM_CODEC)

val FOCUS_COMPONENT: DataComponentType<CastingFocusHolder> = dataComponentType(CastingFocusHolder.CODEC, CastingFocusHolder.STREAM_CODEC)

val SINGLE_FLUID_STORAGE_COMPONENT: DataComponentType<ImmutableFluidStack> = dataComponentType(ImmutableFluidStack.CODEC, ImmutableFluidStack.STREAM_CODEC)

val DEFLUXER_PROPERTIES: DataComponentType<DefluxerProperties> = dataComponentType(DefluxerProperties.CODEC, DefluxerProperties.STREAM_CODEC)
val VIRIAL_ENGINE_PROPERTIES: DataComponentType<VirialEngineProperties> = dataComponentType(VirialEngineProperties.CODEC, VirialEngineProperties.STREAM_CODEC)

inline fun <reified T>dataComponentType(codec: Codec<T>? = null, streamCodec: StreamCodec<in RegistryFriendlyByteBuf, T>? = null): DataComponentType<T>{
    val t: DataComponentType.Builder<T> = DataComponentType.builder()
    codec?.let(t::persistent)
    streamCodec?.let(t::networkSynchronized)
    return t.build()
}