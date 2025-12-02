package com.oyosite.ticon.lostarcana

import com.oyosite.ticon.lostarcana.advancement.THAUMOMETER_SCAN_TRIGGER
import com.oyosite.ticon.lostarcana.aspect.registerAspectsForVanillaItems
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECTS
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.attribute.ATTRIBUTE_REGISTRY
import com.oyosite.ticon.lostarcana.block.BLOCK_REGISTRY
import com.oyosite.ticon.lostarcana.block.fluid.FLUID_REGISTRY
import com.oyosite.ticon.lostarcana.block.generator.VisGeneratorBlockEntity
import com.oyosite.ticon.lostarcana.block.scrubber.ScrubberBaseBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.MAGIC_BRICKS_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.entity.ENTITY_REGISTRY
import com.oyosite.ticon.lostarcana.item.ITEM_REGISTRY
import com.oyosite.ticon.lostarcana.loot.CopyDyedBlockColorFunction
import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import com.oyosite.ticon.lostarcana.recipe.BasicSalisMundisTransformRecipe
import com.oyosite.ticon.lostarcana.recipe.CastingItemModificationRecipe
import com.oyosite.ticon.lostarcana.recipe.CrucibleRecipe
import com.oyosite.ticon.lostarcana.recipe.SalisMundisTransformRecipe
import com.oyosite.ticon.lostarcana.recipe.SpecialCastingItemModificationRecipe
import com.oyosite.ticon.lostarcana.recipe.UniqueVisCrystalRecipe
import com.oyosite.ticon.lostarcana.util.invoke
import dev.architectury.platform.Platform
import dev.architectury.registry.level.entity.EntityAttributeRegistry
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import software.bernie.geckolib.loading.math.MolangQueries

object LostArcana {
    const val MOD_ID: String = "lostarcana"
    fun id(id: String): Identifier = Identifier.parse(if(id.contains(":"))id else "$MOD_ID:$id")


    @JvmStatic
    fun init() {
        println("Hello world")
        FLUID_REGISTRY
        registerAspectsForVanillaItems()
        THAUMOMETER_SCAN_TRIGGER
        if(Platform.isFabric()) ASPECTS.register()// NeoForge doesn't like calling Architectury DeferredRegisters from the mod's main entrypoint
        FLUID_REGISTRY.register()
        BLOCK_REGISTRY.register()
        ITEM_REGISTRY.register()
        if(Platform.isFabric())ATTRIBUTE_REGISTRY.register()
        ENTITY_REGISTRY.register()

        ArcaneWorkbenchRecipe.Type("arcane_workbench")
        ArcaneWorkbenchRecipe.Serializer("arcane_workbench")

        SalisMundisTransformRecipe.Type("salis_mundis_transform")
        BasicSalisMundisTransformRecipe.Serializer("basic_salis_mundis_transform")

        CastingItemModificationRecipe.Serializer("casting_item_modification")
        SpecialCastingItemModificationRecipe.Serializer("special_casting_item_modification")

        UniqueVisCrystalRecipe.Serializer("unique_vis_crystal_shapeless_crafting")

        CrucibleRecipe.Type("crucible")
        CrucibleRecipe.Serializer("crucible")

        CopyDyedBlockColorFunction.TYPE("copy_dyed_block_color")

        MAGIC_BRICKS_BLOCK_ENTITY

        (0 until 4).forEach{
            MolangQueries.setActorVariable<ScrubberBaseBlockEntity>("query.defluxer$it") { actor ->
                if (actor.animatable.items[it].isEmpty) 0.0 else 1.0
            }
            MolangQueries.setActorVariable<VisGeneratorBlockEntity>("query.virial_engine$it") { actor ->
                if (actor.animatable.items[it].isEmpty) 0.0 else 1.0
            }
        }
        if(Platform.isFabric())EntityAttributeRegistry.register({ EntityType.PLAYER }){Player.createAttributes().add(ARCANE_SIGHT).add(ARCANE_INSIGHT)}
    }
}
