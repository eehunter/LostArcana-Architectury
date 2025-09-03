package com.oyosite.ticon.lostarcana.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.LostArcana.init
import com.oyosite.ticon.lostarcana.aspect.AER
import com.oyosite.ticon.lostarcana.aspect.registerBuiltinAspects
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.block.fluid.ESSENTIA_FLUID
import com.oyosite.ticon.lostarcana.blockentity.WARDED_JAR_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.fabric.block.WardedJarFluidStorage
import com.oyosite.ticon.lostarcana.item.ASPECTS_COMPONENT
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.ESSENTIA_BUCKET_ITEM
import com.oyosite.ticon.lostarcana.item.FOCUS_COMPONENT
import com.oyosite.ticon.lostarcana.item.FOCUS_EFFECT
import com.oyosite.ticon.lostarcana.item.RAW_ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.RESONATOR
import com.oyosite.ticon.lostarcana.item.SINGLE_FLUID_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.item.VIS_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.item.WAND_CAP
import com.oyosite.ticon.lostarcana.item.WAND_CAP_2
import com.oyosite.ticon.lostarcana.item.WAND_CAP_3
import com.oyosite.ticon.lostarcana.item.WAND_CORE
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffectType
import com.oyosite.ticon.lostarcana.item.focus.registerBuiltinEffectTypes
import com.oyosite.ticon.lostarcana.unaryPlus
import com.oyosite.ticon.lostarcana.worldgen.feature.INFUSED_STONE_FEATURES
import dev.architectury.core.item.ArchitecturyBucketItem
import dev.architectury.registry.registries.DeferredRegister
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.Items
import net.minecraft.world.level.levelgen.GenerationStep

class LostArcanaFabric : ModInitializer {
    val DATA_COMPONENT_REGISTRAR: DeferredRegister<DataComponentType<*>> = DeferredRegister.create(LostArcana.MOD_ID, Registries.DATA_COMPONENT_TYPE)

    @Suppress("unchecked_cast")
    override fun onInitialize() {
        //AspectRegistry.platform_aspect_registry =


        CastingFocusEffectType.REGISTRY
        registerBuiltinEffectTypes()
        registerBuiltinAspects()

        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("aspect")) { ASPECT_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("aspects")) { ASPECTS_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("raw_aspect")) { RAW_ASPECT_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("vis_storage")) { VIS_STORAGE_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("wand_cap")) { WAND_CAP }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("wand_cap_2")) { WAND_CAP_2 }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("wand_cap_3")) { WAND_CAP_3 }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("wand_core")) { WAND_CORE }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("resonator")) { RESONATOR }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("focus_effect")) { FOCUS_EFFECT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("focus")) { FOCUS_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("single_fluid_stack")) { SINGLE_FLUID_STORAGE_COMPONENT }

        INFUSED_STONE_FEATURES.forEach{
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, it)
        }

        DATA_COMPONENT_REGISTRAR.register()
        init()

        FluidStorage.SIDED.registerForBlockEntity({be, dir -> WardedJarFluidStorage(be)}, WARDED_JAR_BLOCK_ENTITY.value())

        FluidStorage.ITEM.registerForItems({ stack, ctx -> //println("running storage for essentia bucket");
            FullItemFluidStorage(ctx, Items.BUCKET, FluidVariant.of(
            +ESSENTIA_FLUID, DataComponentPatch.builder().set(RAW_ASPECT_COMPONENT, stack.get(RAW_ASPECT_COMPONENT)?: AER).build()
        ), FluidConstants.BUCKET) }, +ESSENTIA_BUCKET_ITEM)
    }

    companion object{
    }
}
