package com.oyosite.ticon.lostarcana.item.focus

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.util.platformCreateCastingFocusEffectTypeRegistry
import net.minecraft.core.Registry
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceKey

abstract class CastingFocusEffectType<T: CastingFocusEffect> {

    abstract val codec: MapCodec<T>

    abstract val streamCodec: StreamCodec<RegistryFriendlyByteBuf, T>


    companion object{
        val REGISTRY_KEY: ResourceKey<Registry<CastingFocusEffectType<*>>> = ResourceKey.createRegistryKey(LostArcana.id("casting_focus_effects"))
        val REGISTRY: Registry<CastingFocusEffectType<*>> = platformCreateCastingFocusEffectTypeRegistry()
        
        val CODEC: Codec<CastingFocusEffectType<*>> = REGISTRY.byNameCodec()
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, CastingFocusEffectType<*>> = StreamCodec.composite(Identifier.STREAM_CODEC, REGISTRY::getKey, REGISTRY::get)
    }
}