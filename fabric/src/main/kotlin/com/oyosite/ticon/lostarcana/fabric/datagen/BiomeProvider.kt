package com.oyosite.ticon.lostarcana.fabric.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import java.util.concurrent.CompletableFuture

class BiomeProvider(val dataOutput: FabricDataOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>): DataProvider {
    var future: CompletableFuture<*> = registryLookupFuture

    override fun run(cachedOutput: CachedOutput): CompletableFuture<*> = cachedOutput.run {


        future
    }

    fun CachedOutput.createBiome(){

    }

    


    override fun getName(): String = "Lost Arcana Biome Generator"
}