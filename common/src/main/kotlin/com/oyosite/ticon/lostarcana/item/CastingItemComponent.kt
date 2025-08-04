package com.oyosite.ticon.lostarcana.item

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.util.ImmutableItemStack
import com.oyosite.ticon.lostarcana.util.ImmutableItemStack.Companion.immutableCopy
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.*
import kotlin.jvm.optionals.getOrNull

@JvmRecord
data class CastingItemComponent(val color: Int, val efficiency: Float = 1f, val storage: Float = 1f, val stack: ImmutableItemStack?){
    constructor(color: Int, efficiency: Float, storage: Float, stack: ItemStack): this(color, efficiency, storage, stack.immutableCopy)
    constructor(color: Int, efficiency: Float, storage: Float, stack: Optional<ImmutableItemStack>): this(color, efficiency, storage, stack.getOrNull())

    val optionalStack get() = Optional.ofNullable(stack)

    val item: Item? get() = stack?.item

    val resonator: Resonator? get() = item as? Resonator

    companion object{
        val CODEC = RecordCodecBuilder.create{
            it.group(
                Codec.INT.fieldOf("color").forGetter(CastingItemComponent::color),
                Codec.FLOAT.fieldOf("efficiency").forGetter(CastingItemComponent::efficiency),
                Codec.FLOAT.fieldOf("storage").forGetter(CastingItemComponent::storage),
                ImmutableItemStack.CODEC.optionalFieldOf("stack").forGetter(CastingItemComponent::optionalStack)
            ).apply(it, ::CastingItemComponent)
        }
        val STREAM_CODEC = StreamCodec.of({buf: RegistryFriendlyByteBuf, obj: CastingItemComponent ->
            buf.writeInt(obj.color)
            buf.writeFloat(obj.efficiency)
            buf.writeFloat(obj.storage)
            buf.writeBoolean(obj.stack!=null)
            if(obj.stack!=null)ImmutableItemStack.STREAM_CODEC.encode(buf, obj.stack)
        }){buf -> CastingItemComponent(buf.readInt(), buf.readFloat(), buf.readFloat(), if(buf.readBoolean())ImmutableItemStack.STREAM_CODEC.decode(buf)else null) }
    }
}
