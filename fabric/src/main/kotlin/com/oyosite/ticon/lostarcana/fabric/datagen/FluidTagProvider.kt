package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.block.fluid.ESSENTIA_FLUID
import com.oyosite.ticon.lostarcana.block.fluid.ESSENTIA_FLUID_FLOWING
import com.oyosite.ticon.lostarcana.unaryPlus
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.FluidTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import java.util.concurrent.CompletableFuture

class FluidTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider<Fluid>(output, Registries.FLUID, registriesFuture), LostArcanaTagProvider<Fluid> {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        FluidTags.WATER(+ESSENTIA_FLUID, +ESSENTIA_FLUID_FLOWING)
    }

    override fun getTagBuilder(key: TagKey<Fluid>): FabricTagProvider<Fluid>.FabricTagBuilder = getOrCreateTagBuilder(key)
}