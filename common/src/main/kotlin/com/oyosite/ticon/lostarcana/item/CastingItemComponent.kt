package com.oyosite.ticon.lostarcana.item

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

@JvmRecord
data class CastingItemComponent(val color: Int, val efficiency: Float = 1f, val storage: Float = 1f, val stack: ItemStack?){



    companion object{
        val CODEC = RecordCodecBuilder.create{
            it.group(
                Codec.INT.fieldOf("color").forGetter(CastingItemComponent::color),
                Codec.FLOAT.fieldOf("efficiency").forGetter(CastingItemComponent::efficiency),
                Codec.FLOAT.fieldOf("storage").forGetter(CastingItemComponent::storage),
                ItemStack.CODEC.fieldOf("stack").forGetter(CastingItemComponent::stack)
            ).apply(it, ::CastingItemComponent)
        }
        val STREAM_CODEC = StreamCodec.of({buf: ByteBuf, obj: CastingItemComponent ->
            buf.writeInt(obj.color)
            buf.writeFloat(obj.efficiency)
            buf.writeFloat(obj.storage)
        }){buf -> CastingItemComponent(buf.readInt(), buf.readFloat(), buf.readFloat(), null) }
    }
}
