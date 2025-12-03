package com.oyosite.ticon.lostarcana.worldgen.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import com.oyosite.ticon.lostarcana.worldgen.feature.AuraNodeFeature.Config
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration

class AuraNodeFeature(codec: Codec<Config>) : Feature<Config>(codec) {
    override fun place(featurePlaceContext: FeaturePlaceContext<Config>): Boolean {
        val blockPos = featurePlaceContext.origin()
        val level = featurePlaceContext.level()
        val randomSource = featurePlaceContext.random()
        val cfg = featurePlaceContext.config()

        val p = blockPos.above(randomSource.nextIntBetweenInclusive(cfg.minHeight,cfg.minHeight))
        if(level.getBlockState(p).isAir){
            level.addFreshEntity(AuraNodeEntity(level.level).apply{moveTo(p.center); visCapacity = cfg.minCapacity+cfg.capRange*randomSource.nextFloat(); vis = visCapacity; fluxAffinityInternal = cfg.minFluxAffinity+randomSource.nextFloat()*cfg.fluxAffinityRange})
            return true
        }
        return false
    }


    data class Config(val minHeight: Int = 1, val maxHeight: Int = 5, val minCapacity: Float = 30f, val maxCapacity: Float = 150f, val minFluxAffinity: Float = 60f, val maxFluxAffinity: Float = 140f): FeatureConfiguration {

        val capRange = maxCapacity - minCapacity
        val fluxAffinityRange = maxFluxAffinity - minFluxAffinity

        companion object{
            val CODEC: Codec<Config> = RecordCodecBuilder.create { it.group(
                Codec.INT.optionalFieldOf("min_height",  1).forGetter(Config::minHeight),
                Codec.INT.optionalFieldOf("max_height",  1).forGetter(Config::maxHeight),
                Codec.FLOAT.optionalFieldOf("min_capacity",  30f).forGetter(Config::minCapacity),
                Codec.FLOAT.optionalFieldOf("max_capacity",  150f).forGetter(Config::maxCapacity),
                Codec.FLOAT.optionalFieldOf("min_flux_affinity",  60f).forGetter(Config::minFluxAffinity),
                Codec.FLOAT.optionalFieldOf("max_flux_affinity",  140f).forGetter(Config::maxFluxAffinity),
            ).apply(it, ::Config) }

            val STREAM_CODEC: StreamCodec<ByteBuf, Config> = StreamCodec.composite(
                ByteBufCodecs.INT, Config::minHeight,
                ByteBufCodecs.INT, Config::maxHeight,
                ByteBufCodecs.FLOAT, Config::minCapacity,
                ByteBufCodecs.FLOAT, Config::maxCapacity,
                ::Config)
        }
    }
}