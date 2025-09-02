package com.oyosite.ticon.lostarcana.block.fluid

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.LostArcana.MOD_ID
import com.oyosite.ticon.lostarcana.block.ESSENTIA_FLUID_BLOCK
import com.oyosite.ticon.lostarcana.item.ESSENTIA_BUCKET_ITEM
import dev.architectury.core.fluid.ArchitecturyFlowingFluid
import dev.architectury.core.fluid.ArchitecturyFluidAttributes
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import net.minecraft.world.level.material.Fluid

val FLUID_REGISTRY: DeferredRegister<Fluid> = DeferredRegister.create(MOD_ID, Registries.FLUID)

val ESSENTIA_FLUID_ATTRIBUTES: ArchitecturyFluidAttributes = EssentiaFluidAttributes.of({ESSENTIA_FLUID_FLOWING::value}, {ESSENTIA_FLUID::value}).blockSupplier { ESSENTIA_FLUID_BLOCK }.bucketItemSupplier { ESSENTIA_BUCKET_ITEM as RegistrySupplier<Item> }
    .sourceTexture(LostArcana.id("minecraft:block/water_still"))
    .flowingTexture(LostArcana.id("minecraft:block/water_flow"))
    .overlayTexture(LostArcana.id("minecraft:block/water_overlay"))
val ESSENTIA_FLUID = "essentia_fluid" % { EssentiaSource(ESSENTIA_FLUID_ATTRIBUTES) }
val ESSENTIA_FLUID_FLOWING = "essentia_fluid_flowing" % { EssentiaFlow(ESSENTIA_FLUID_ATTRIBUTES) }



inline operator fun <reified T: Fluid> String.rem(noinline fluidSupplier: ()->T): RegistrySupplier<T> =
    FLUID_REGISTRY.register(this, fluidSupplier)