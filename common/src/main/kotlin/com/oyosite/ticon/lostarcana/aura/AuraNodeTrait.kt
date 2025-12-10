package com.oyosite.ticon.lostarcana.aura

import com.mojang.serialization.Codec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.level.Level

interface AuraNodeTrait {

    fun onGenerateVis(level: Level, source: AuraSource, amount: Float) = amount
    fun onTick(level: Level, source: AuraSource) = Unit


    
    
    companion object{
        val CODEC: Codec<AuraNodeTrait> = NODE_TRAIT_REGISTRY_INTERNAL.byNameCodec()
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, AuraNodeTrait> = ByteBufCodecs.fromCodecWithRegistries(CODEC)
        
        val LIST_CODEC: Codec<List<AuraNodeTrait>> = Codec.list(CODEC)
    }
}