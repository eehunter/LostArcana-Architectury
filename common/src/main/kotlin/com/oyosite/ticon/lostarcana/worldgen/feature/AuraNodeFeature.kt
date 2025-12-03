package com.oyosite.ticon.lostarcana.worldgen.feature

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import com.oyosite.ticon.lostarcana.worldgen.feature.AuraNodeFeature.Config
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration

class AuraNodeFeature(codec: Codec<Config>) : Feature<AuraNodeFeature.Config>(codec) {
    override fun place(featurePlaceContext: FeaturePlaceContext<Config>): Boolean {
        val blockPos = featurePlaceContext.origin()
        val level = featurePlaceContext.level()
        val randomSource = featurePlaceContext.random()

        val p = blockPos.above(randomSource.nextIntBetweenInclusive(1,5))
        if(level.getBlockState(p).isAir){
            level.addFreshEntity(AuraNodeEntity(level.level).apply{moveTo(p.center); visCapacity = 30+120*random.nextFloat(); vis = visCapacity})
            return true
        }
        return false
    }


    data class Config(val minHeight: Int = 1, val maxHeight: Int = 1): FeatureConfiguration {


        companion object{
            val CODEC = RecordCodecBuilder.create { it.group(
                Codec.INT.optionalFieldOf("min_height",  1).forGetter(Config::minHeight),
                Codec.INT.optionalFieldOf("max_height",  1).forGetter(Config::maxHeight),
            ).apply(it, ::Config) }

            val STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, Config::minHeight, ByteBufCodecs.INT, Config::maxHeight, ::Config)
        }
    }
}