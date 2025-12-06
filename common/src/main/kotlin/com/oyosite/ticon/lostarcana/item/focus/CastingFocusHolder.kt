package com.oyosite.ticon.lostarcana.item.focus

import com.mojang.serialization.Codec
import com.oyosite.ticon.lostarcana.MCPair
import com.oyosite.ticon.lostarcana.util.ImmutableItemStack
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec

data class CastingFocusHolder(val stack: ImmutableItemStack, val effect: CastingFocusEffect){
    constructor(pair: MCPair<ImmutableItemStack, CastingFocusEffect>): this(pair.first, pair.second)

    val asPair = MCPair(stack, effect)

    companion object{
        val PAIR_CODEC: Codec<MCPair<ImmutableItemStack, CastingFocusEffect>> = Codec.pair(ImmutableItemStack.CODEC, CastingFocusEffect.CODEC.codec())
        val CODEC: Codec<CastingFocusHolder> = Codec.of(PAIR_CODEC.comap(CastingFocusHolder::asPair), PAIR_CODEC.map(::CastingFocusHolder))

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, CastingFocusHolder> = StreamCodec.composite(ImmutableItemStack.STREAM_CODEC, CastingFocusHolder::stack, CastingFocusEffect.STREAM_CODEC, CastingFocusHolder::effect, ::CastingFocusHolder)
    }
}
