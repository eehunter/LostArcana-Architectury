package com.oyosite.ticon.lostarcana.fabric.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class LostArcanaDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(dataGenerator: FabricDataGenerator) {
        val pack: FabricDataGenerator.Pack = dataGenerator.createPack()

        pack.addProvider(::EnglishLangProvider)
        pack.addProvider(::ModelProvider)
        pack.addProvider(::BlockLootTableProvider)
        pack.addProvider(::BlockTagProvider)
        pack.addProvider(::ItemTagProvider)
        pack.addProvider(::RecipeProvider)
    }
}