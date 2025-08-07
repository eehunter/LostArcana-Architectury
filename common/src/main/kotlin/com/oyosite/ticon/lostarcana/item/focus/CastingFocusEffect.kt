package com.oyosite.ticon.lostarcana.item.focus

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec

interface CastingFocusEffect {
    fun isValidForContext(ctx: CastingContext): Boolean
    fun use(ctx: CastingContext): Boolean

    val color: UInt get() = 0xFFFFFFFFu

    val type: CastingFocusEffectType<*>

    companion object{
        val NONE: CastingFocusEffect = object : CastingFocusEffect{
            override fun isValidForContext(ctx: CastingContext): Boolean = false
            override fun use(ctx: CastingContext): Boolean = false

            override val color: UInt = 0u

            override val type: CastingFocusEffectType<*> = object : CastingFocusEffectType<CastingFocusEffect>() {
                override val codec: MapCodec<CastingFocusEffect> = MapCodec.unit { NONE }
                override val streamCodec: StreamCodec<RegistryFriendlyByteBuf, CastingFocusEffect> by lazy { StreamCodec.unit ( NONE ) }
            }
        }

        val CODEC: Codec<CastingFocusEffect> = CastingFocusEffectType.CODEC.dispatch<CastingFocusEffect>("type", {it.type}, {it.codec})

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, CastingFocusEffect> = CastingFocusEffectType.STREAM_CODEC.dispatch<CastingFocusEffect>( {it.type} ){it.streamCodec}

    }

}