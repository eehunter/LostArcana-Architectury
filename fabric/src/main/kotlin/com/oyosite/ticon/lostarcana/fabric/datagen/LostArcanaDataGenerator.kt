package com.oyosite.ticon.lostarcana.fabric.datagen

import com.klikli_dev.modonomicon.api.datagen.FabricBookProvider
import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache
import com.klikli_dev.modonomicon.datagen.EnUsProvider
import com.oyosite.ticon.lostarcana.fabric.datagen.book.ThaumonomiconBook
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class LostArcanaDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(dataGenerator: FabricDataGenerator) {
        val pack: FabricDataGenerator.Pack = dataGenerator.createPack()

        val enUsCache = LanguageProviderCache("en_us")

        pack.addProvider(FabricBookProvider.of(ThaumonomiconBook(enUsCache)))
        pack.addProvider{ EnUsProvider(it, enUsCache) }
        pack.addProvider { output, registriesFuture -> EnglishLangProvider(output, registriesFuture, enUsCache) }
        pack.addProvider(::FusionModels)
        pack.addProvider(::ModelProvider)
        pack.addProvider(::BlockLootTableProvider)
        pack.addProvider(::BlockTagProvider)
        pack.addProvider(::ItemTagProvider)
        pack.addProvider(::RecipeProvider)
        pack.addProvider(::OreProvider)
        pack.addProvider(::FusionTextureMetadata)
        pack.addProvider(::FeatureProvider)
        pack.addProvider(::FluidTagProvider)
    }
}