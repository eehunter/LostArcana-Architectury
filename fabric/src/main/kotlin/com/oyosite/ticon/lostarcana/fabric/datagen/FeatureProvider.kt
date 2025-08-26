package com.oyosite.ticon.lostarcana.fabric.datagen

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.serialization.JsonOps
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.GREATWOOD_LEAVES
import com.oyosite.ticon.lostarcana.block.GREATWOOD_LOG
import com.oyosite.ticon.lostarcana.block.GREATWOOD_SAPLING
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.core.Vec3i
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter
import net.minecraft.world.level.levelgen.placement.PlacementModifier
import java.nio.file.Path
import java.util.concurrent.CompletableFuture


class FeatureProvider(val dataOutput: FabricDataOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>): DataProvider {
    var future: CompletableFuture<*> = registryLookupFuture

    override fun run(cachedOutput: CachedOutput): CompletableFuture<*> = cachedOutput.run {
        createTree(
            "greatwood",
            giantTreeTrunk(32, 8, 0),
            coniferFoliagePlacer(1, 5, 1, 20),
            GREATWOOD_LEAVES.get().defaultBlockState(),
            GREATWOOD_LOG.get().defaultBlockState(),
            GREATWOOD_SAPLING.get().defaultBlockState(),
        )

        future
    }

    fun CachedOutput.createTree(name: String, trunk: JsonElement, foliage: JsonElement, leaves: BlockState, log: BlockState, sapling: BlockState, dirtProvider: JsonElement = DIRT_PROVIDER){
        future = future.thenCompose{
            DataProvider.saveStable(
                this,
                getTree(trunk, foliage, leaves, log, dirtProvider),
                getConfiguredFeaturePath(name)
            )
        }.thenCompose {
            val placement = PlacementModifier.CODEC.encodeStart(
                JsonOps.INSTANCE, BlockPredicateFilter.forPredicate(
                    BlockPredicate.wouldSurvive(sapling, Vec3i.ZERO)
                )
            ).orThrow
            val placeArr = JsonArray()
            placeArr.add(placement)

            val obj = JsonObject()
            obj.addProperty("feature", LostArcana.id(name).toString())
            obj.add("placement", placeArr)

            DataProvider.saveStable(this, obj, getPlacedFeaturePath(name))
        }
    }

    fun getTree(trunk: JsonElement, foliage: JsonElement, leaves: BlockState, log: BlockState, dirtProvider: JsonElement = DIRT_PROVIDER, decorators: JsonArray = JsonArray()): JsonObject{
        val cfg = JsonObject()
        cfg.add("trunk_placer", trunk)
        cfg.add("trunk_provider", BlockStateProvider.CODEC.encodeStart(JsonOps.INSTANCE, SimpleStateProvider.simple(log)).orThrow)
        cfg.add("foliage_placer", foliage)
        cfg.add("foliage_provider", BlockStateProvider.CODEC.encodeStart(JsonOps.INSTANCE, SimpleStateProvider.simple(leaves)).orThrow)
        cfg.add("dirt_provider", dirtProvider)
        cfg.add("minimum_size", FeatureSize.CODEC.encodeStart(JsonOps.INSTANCE, TwoLayersFeatureSize(1, 0, 1)).orThrow)
        cfg.addProperty("force_dirt", false)
        cfg.addProperty("ignore_vines", true)
        cfg.add("decorators", decorators)

        val jsonObject = JsonObject()
        jsonObject.addProperty("type", "minecraft:tree")
        jsonObject.add("config", cfg)

        return jsonObject
    }

    private val DIRT_PROVIDER: JsonElement = BlockStateProvider.CODEC.encodeStart(JsonOps.INSTANCE, SimpleStateProvider.simple(Blocks.DIRT)).getOrThrow()

    fun basicTreeFoliage(radius: Int, height: Int): JsonElement = FoliagePlacer.CODEC.encodeStart(JsonOps.INSTANCE,
        BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), height)).orThrow

    fun coniferFoliagePlacer(minRadius: Int, maxRadius: Int, int2: Int, int3: Int): JsonElement = FoliagePlacer.CODEC.encodeStart(JsonOps.INSTANCE,
        MegaPineFoliagePlacer(UniformInt.of(minRadius, maxRadius), ConstantInt.of(int2), ConstantInt.of(int3))).orThrow

    fun straightTreeTrunk(height: Int, rand1: Int, rand2: Int): JsonElement = TrunkPlacer.CODEC.encodeStart(JsonOps.INSTANCE,
        StraightTrunkPlacer(height, rand1, rand2)).orThrow

    fun giantTreeTrunk(height: Int, rand1: Int, rand2: Int): JsonElement = TrunkPlacer.CODEC.encodeStart(JsonOps.INSTANCE,
        GiantTrunkPlacer(height, rand1, rand2)).orThrow

    private fun getConfiguredFeaturePath(name: String): Path {
        return dataOutput
            .createPathProvider(PackOutput.Target.DATA_PACK, "worldgen/configured_feature")
            .json(ResourceLocation.fromNamespaceAndPath(dataOutput.modId, name))
    }

    private fun getPlacedFeaturePath(name: String): Path {
        return dataOutput
            .createPathProvider(PackOutput.Target.DATA_PACK, "worldgen/placed_feature")
            .json(ResourceLocation.fromNamespaceAndPath(dataOutput.modId, name))
    }

    override fun getName(): String = "Lost Arcana Feature Generator"
}