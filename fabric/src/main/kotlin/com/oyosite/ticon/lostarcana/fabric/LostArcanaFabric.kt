package com.oyosite.ticon.lostarcana.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.LostArcana.init
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.item.ASPECTS_COMPONENT
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.VIS_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.worldgen.feature.INFUSED_STONE_FEATURES
import dev.architectury.registry.registries.DeferredRegister
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.DefaultAttributes
import net.minecraft.world.level.levelgen.GenerationStep

class LostArcanaFabric : ModInitializer {
    val DATA_COMPONENT_REGISTRAR: DeferredRegister<DataComponentType<*>> = DeferredRegister.create(LostArcana.MOD_ID, Registries.DATA_COMPONENT_TYPE)

    @Suppress("unchecked_cast")
    override fun onInitialize() {
        print("Hello world Fabric")
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.

        AspectRegistry.platform_aspect_registry =
            FabricRegistryBuilder.createSimple(AspectRegistry.ASPECT_REGISTRY_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister()

        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("aspect")) { ASPECT_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("aspects")) { ASPECTS_COMPONENT }
        DATA_COMPONENT_REGISTRAR.register(LostArcana.id("vis_storage")) { VIS_STORAGE_COMPONENT }
        //Registry.register(Registries.DATA_COMPONENT_TYPE, "${LostArcana.MOD_ID}:aspect", ASPECT_COMPONENT)
        //Registry.register(Registries.DATA_COMPONENT_TYPE, LostArcana.id("aspects"), ASPECTS_COMPONENT)


        INFUSED_STONE_FEATURES.forEach{
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, it)
        }

        DATA_COMPONENT_REGISTRAR.register()
        init()

        //val playerAttributes = DefaultAttributes.getSupplier(EntityType.PLAYER)

        //AttributeSupplier.builder().

        //FabricDefaultAttributeRegistry.register(EntityType.PLAYER, AttributeSupplier.builder().add(ARCANE_SIGHT as Holder<Attribute>))
        //FabricDefaultAttributeRegistry.register(EntityType.PLAYER, AttributeSupplier.builder().add(ARCANE_INSIGHT as Holder<Attribute>))
    }
}
