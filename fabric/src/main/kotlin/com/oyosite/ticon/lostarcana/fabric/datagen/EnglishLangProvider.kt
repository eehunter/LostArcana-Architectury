package com.oyosite.ticon.lostarcana.fabric.datagen

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.block.*
import com.oyosite.ticon.lostarcana.emi.LostArcanaEmiPlugin
import com.oyosite.ticon.lostarcana.item.*
import com.oyosite.ticon.lostarcana.tag.GREATWOOD_LOGS
import com.oyosite.ticon.lostarcana.tag.WAND_CAPS
import com.oyosite.ticon.lostarcana.tag.WAND_CORES
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.Locale.getDefault
import java.util.concurrent.CompletableFuture

class EnglishLangProvider( dataOutput: FabricDataOutput,  registryLookup: CompletableFuture<HolderLookup.Provider>, val cache: LanguageProviderCache) : FabricLanguageProvider(dataOutput, "en_us", registryLookup){
    override fun generateTranslations(
        registryLookup: HolderLookup.Provider,
        translationBuilder: TranslationBuilder
    ) = translationBuilder.run{

        add(TALLOW, "Magic Tallow")

        add(ALCHEMICAL_BRASS_INGOT)
        add(ALCHEMICAL_BRASS_NUGGET)
        add(ALCHEMICAL_BRASS_BLOCK)
        add(THAUMIUM_INGOT)
        add(THAUMIUM_NUGGET)
        add(THAUMIUM_BLOCK)

        add(NITOR)
        add(VIS_LIGHT)

        add(WARDED_JAR)
        add(ESSENTIA_BUCKET_ITEM)

        add(VIS_CRYSTAL, "Vis Crystal")
        add(WAND_ITEM, "Wand")
        add(WandItem.STORED_VIS_TOOLTIP, $$"%1$s Vis")
        add(CastingItem.VIS_EFFICIENCY_TOOLTIP, $$"%1$s%%")
        add(CRUDE_CASTER_GAUNTLET, "Crude Caster Gauntlet")
        add(SALIS_MUNDIS, "Salis Mundis")
        add(THAUMOMETER, "Thaumometer")
        add(GOGGLES_OF_REVEALING, "Goggles of Revealing")

        add(FLUXER)

        add(ARCANE_STONE)
        add(ARCANE_STONE_TILES)
        add(ARCANE_STONE_PILLAR)
        add(ARCANE_STONE_SLAB)
        add(ARCANE_STONE_TILE_SLAB)
        add(ARCANE_STONE_STAIRS)
        add(ARCANE_STONE_TILE_STAIRS)

        add(GREATWOOD_PLANKS)
        add(GREATWOOD_LOG)
        add(GREATWOOD_LEAVES)
        add(GREATWOOD_SAPLING)

        add(WOOD_WAND_CORE)
        add(GREATWOOD_WAND_CORE)

        add(IRON_WAND_CAP)
        add(GOLD_WAND_CAP)
        add(THAUMIUM_WAND_CAP)

        add(CASTER_GAUNTLET)
        add(FOCUS)

        add(ARCANE_WORKBENCH)
        add(ArcaneWorkbench.TITLE_KEY, "Arcane Workbench")
        add(CRUCIBLE)
        add(ESSENTIA_SMELTERY)
        add(ARCANE_PEDESTAL)
        add(RECHARGE_PEDESTAL)

        add(ThaumometerItem.AURA_LEVEL_TRANSLATION_KEY, $$"%1$s μv")
        add(ThaumometerItem.FLUX_LEVEL_TRANSLATION_KEY, $$"%1$s μv")
        //add(ThaumometerItem.FLUX_LEVEL_TRANSLATION_KEY, $$"{content: {text: '%1$s μv'}, color: 'dark_purple'}")
        add(ThaumometerItem.NO_AURA_TRANSLATION_KEY, "No Aura detected.")
        AspectRegistry.ASPECTS.registrar.entrySet().forEach { (key, value) ->
            add(value.translationKey, value.id.path.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() })
        }
        INFUSED_STONES.forEach {
            add(it, it.id.path.split("_").joinToString(" ") { s -> s.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase(getDefault()) else c.toString() } })
        }

        add(ARCANE_SIGHT, "Arcane Sight")
        add(ARCANE_INSIGHT, "Arcane Insight")


        add(GREATWOOD_LOGS)
        add(WAND_CAPS)
        add(WAND_CORES)

        add(LostArcanaEmiPlugin.UNIQUE_VIS_CRYSTAL_TOOLTIP, "All crystals must be different aspects.")

        add(LostArcanaEmiPlugin.MODIFIED_WAND_TOOLTIP_1, "This replaces one or more parts of your wand.")
        add(LostArcanaEmiPlugin.MODIFIED_WAND_TOOLTIP_2, "Old parts will be left behind in the crafting grid.")
        add(LostArcanaEmiPlugin.MODIFIED_WAND_TOOLTIP_3, "You do not need to replace all three parts at once, as is shown here.")
        add(LostArcanaEmiPlugin.MODIFIED_WAND_TOOLTIP_4, "Any Vis stored in the wand will be lost.")

        ELEMENTAL_GEODE_MATERIALS.forEach { mat ->
            arrayOf(mat.block, mat.cluster, mat.largeBud, mat.mediumBud, mat.smallBud, mat.buddingBlock).forEach { add(it) }
        }

        add("emi.category.lostarcana.arcane_workbench", "Arcane Workbench")

        add(PRISTINE_DEFLUXER)
        add(INTACT_DEFLUXER)
        add(PRISTINE_VIRIAL_ENGINE)
        add(INTACT_VIRIAL_ENGINE)

        add(FLUX_SCRUBBER_BASE)
        add(VIS_GENERATOR_BLOCK)
        add(VIRIAL_NODE)

        cache.data(::add)
    }

    fun <T> TranslationBuilder.add(tag: TagKey<T>) = add(tag, tag.location.path.split("_").joinToString(" ") { s -> s.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase(getDefault()) else c.toString() } })

    fun <T> TranslationBuilder.add(holder: RegistrySupplier<T>) = add(holder, holder.id.path.split("_").joinToString(" ") { s -> s.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase(getDefault()) else c.toString() } })

    fun <T> TranslationBuilder.add(holder: RegistrySupplier<T>, name: String) = holder.get().let {
        when (it) {
            is Item -> add(it, name)
            is Block -> add(it, name)
            is Aspect -> add(it.translationKey, name)
            else -> add(holder.id, name)
        }
    }

}