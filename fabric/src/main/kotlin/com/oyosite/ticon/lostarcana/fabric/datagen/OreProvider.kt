package com.oyosite.ticon.lostarcana.fabric.datagen

import com.google.gson.JsonParser
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

class OreProvider(val dataOutput: FabricDataOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>): DataProvider {

    var future: CompletableFuture<*> = registryLookupFuture

    override fun run(cachedOutput: CachedOutput): CompletableFuture<*> = cachedOutput.run{

        val infusedStoneOreFeatureNames = mutableListOf<String>()
        INFUSED_STONES.forEach {
            val name = "${it.id.path}_ore_feature"
            infusedStoneOreFeatureNames+=name
            createOre(name, 12, it, it)
            placeOre(name, 20, -64, 128)
        }
        neoforgeBiomeModification("infused_stone_ore_features", "neoforge:add_features", "'#c:is_overworld'", infusedStoneOreFeatureNames.joinToString("', '", "['", "']", transform = { LostArcana.id(it).toString() }), "underground_ores")


        future
    }

    private fun CachedOutput.neoforgeBiomeModification(name: String, type: Any, biomes: Any?, features: Any?, step: Any?){
        future = future.thenCompose {
            var str = "{\n"
            str += "    \"type\": \"$type\""
            if(biomes!=null)str += ",\n    \"biomes\": $biomes"
            if(features!=null)str += ",\n    \"features\": $features"
            if(step!=null)str += ",\n    \"step\": \"$step\""
            str += "\n}"
            val json = JsonParser.parseString(str)

            DataProvider.saveStable(this, json, getNeoforgeBiomeModifierPath(name))
        }
    }

    private fun CachedOutput.placeOre(name: String, count: Int, minY: Int, maxY: Int){
        future = future.thenCompose {
            val json = JsonParser.parseString(
                    "    {\n" +
                    "      \"feature\": \"${LostArcana.id(name)}\",\n" +
                    "      \"placement\": [\n" +
                    "        {\n" +
                    "          \"type\": \"minecraft:count\",\n" +
                    "          \"count\": $count\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"type\": \"minecraft:in_square\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"type\": \"minecraft:height_range\",\n" +
                    "          \"height\": {\n" +
                    "            \"type\": \"minecraft:trapezoid\",\n" +
                    "            \"max_inclusive\": {\n" +
                    "              \"absolute\": $maxY\n" +
                    "            },\n" +
                    "            \"min_inclusive\": {\n" +
                    "              \"absolute\": $minY\n" +
                    "            }\n" +
                    "          }\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"type\": \"minecraft:biome\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "\n")

            DataProvider.saveStable(this, json, getPlacedFeaturePath(name))
        }
    }

    private fun CachedOutput.createOre(name: String, size: Int, stoneOre: RegistrySupplier<out Block>, deepslateOre: RegistrySupplier<out Block>, airDiscardChance: Float = 0f) {
        future = future.thenCompose {
            val json = JsonParser.parseString(
                "    {\n" +
                        "      \"type\": \"minecraft:ore\",\n" +
                        "      \"config\": {\n" +
                        "        \"discard_chance_on_air_exposure\": $airDiscardChance,\n" +
                        "        \"size\": $size,\n" +
                        "        \"targets\": [\n" +
                        "          {\n" +
                        "            \"state\": {\n" +
                        "              \"Name\": \"${stoneOre.id}\"\n" +
                        "            },\n" +
                        "            \"target\": {\n" +
                        "              \"predicate_type\": \"minecraft:tag_match\",\n" +
                        "              \"tag\": \"minecraft:stone_ore_replaceables\"\n" +
                        "            }\n" +
                        "          },\n" +
                        "          {\n" +
                        "            \"state\": {\n" +
                        "              \"Name\": \"${deepslateOre.id}\"\n" +
                        "            },\n" +
                        "            \"target\": {\n" +
                        "              \"predicate_type\": \"minecraft:tag_match\",\n" +
                        "              \"tag\": \"minecraft:deepslate_ore_replaceables\"\n" +
                        "            }\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    }\n" +
                        "\n"
            )

            DataProvider.saveStable(this, json, getConfiguredFeaturePath(name))
        }
    }

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

    private fun getNeoforgeBiomeModifierPath(name: String): Path {
        return dataOutput
            .createPathProvider(PackOutput.Target.DATA_PACK, "neoforge/biome_modifier")
            .json(ResourceLocation.fromNamespaceAndPath(dataOutput.modId, name))
    }

    override fun getName(): String = "Configured Feature Definitions"
}