package com.oyosite.ticon.lostarcana.item.focus

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.block.VIS_LIGHT
import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.core.Vec3i
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.level.GameType
import java.util.Optional
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

data class VisLightEffect(val optionalLightLevel: Optional<Byte>, val optionalColor: Optional<Int>): CastingFocusEffect {
    constructor(lightLevel: Byte? = null, color: Int? = null): this(Optional.ofNullable(lightLevel), Optional.ofNullable(color))
    val lightLevel: Byte = optionalLightLevel.getOrElse { 15 }
    override val color: UInt = optionalColor.getOrNull()?.toUInt()?:DEFAULT_COLOR


    override fun isValidForContext(
        ctx: CastingContext
    ): Boolean = true

    override fun use(
        ctx: CastingContext
    ): Boolean {
        val level = ctx.caster?.level()?:return false
        val pos = ctx.targetBlockPos?.offset(ctx.targetFace?.normal?: Vec3i.ZERO)?:return false
        val player = ctx.player
        if(player?.blockActionRestricted(level, pos, if(player.isCreative) GameType.CREATIVE else GameType.SURVIVAL)?: false) return false
        if(!level.getBlockState(pos).canBeReplaced())return false
        val castingItemStack = ctx.castingItem?:return false
        val castingItem = castingItemStack.item as? CastingItem ?: return false
        castingItem.consumeVis(castingItemStack, level, ctx.pos?:return false, 2f, player)
        level.setBlockAndUpdate(pos, (+VIS_LIGHT).defaultBlockState())
        return true
    }

    override val type: CastingFocusEffectType<*> = Serializer



    object Serializer: CastingFocusEffectType<VisLightEffect>(){
        override val codec: MapCodec<VisLightEffect> = MapCodec.assumeMapUnsafe(RecordCodecBuilder.create { it.group(
            Codec.BYTE.optionalFieldOf("light").forGetter(VisLightEffect::optionalLightLevel),
            Codec.INT.optionalFieldOf("color").forGetter(VisLightEffect::optionalColor)
        ).apply(it, ::VisLightEffect) })
        override val streamCodec: StreamCodec<RegistryFriendlyByteBuf, VisLightEffect>
            get() = StreamCodec.of({ buf, effect ->
                var mask = 0
                var a = false
                var b = false
                if(effect.lightLevel != 15.toByte()) {
                    mask = mask or 1
                    a = true
                }
                if(effect.color != DEFAULT_COLOR) {
                    mask = mask or 2
                    b = true
                }
                buf.writeByte(mask)
                if(a)buf.writeByte(effect.lightLevel.toInt())
                if(b)buf.writeInt(effect.color.toInt())
            }){ buf ->
                val mask = buf.readByte().toInt()
                val lightLevel = Optional.ofNullable(if (mask and 1 != 0)buf.readByte() else null)
                val color = Optional.ofNullable(if (mask and 2 != 0)buf.readInt() else null)
                VisLightEffect(lightLevel, color)
            }

    }

    companion object{
        val DEFAULT_COLOR = 0xFFFFFFFFu
    }
}