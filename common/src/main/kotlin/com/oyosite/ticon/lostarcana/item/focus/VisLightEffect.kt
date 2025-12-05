package com.oyosite.ticon.lostarcana.item.focus

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.block.VIS_LIGHT
import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.core.Vec3i
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
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
        override val streamCodec: StreamCodec<RegistryFriendlyByteBuf, VisLightEffect> =
            StreamCodec.composite(
                ByteBufCodecs.optional(ByteBufCodecs.BYTE), VisLightEffect::optionalLightLevel,
                ByteBufCodecs.optional(ByteBufCodecs.INT), VisLightEffect::optionalColor,
                ::VisLightEffect
            )

    }

    companion object{
        val DEFAULT_COLOR = 0xFFFFFF55u
    }
}