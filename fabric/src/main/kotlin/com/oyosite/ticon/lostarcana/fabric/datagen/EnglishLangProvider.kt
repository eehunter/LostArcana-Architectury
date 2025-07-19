package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_TILES
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.item.*
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.Locale.getDefault
import java.util.concurrent.CompletableFuture

class EnglishLangProvider( dataOutput: FabricDataOutput,  registryLookup: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(dataOutput, "en_us", registryLookup){
    override fun generateTranslations(
        registryLookup: HolderLookup.Provider,
        translationBuilder: TranslationBuilder
    ) = translationBuilder.run{
        add(VIS_CRYSTAL, "Vis Crystal")
        add(WAND_ITEM, "Wand")
        add(CRUDE_CASTER_GAUNTLET, "Crude Caster Gauntlet")
        add(SALIS_MUNDIS, "Salis Mundis")
        add(THAUMOMETER, "Thaumometer")
        add(GOGGLES_OF_REVEALING, "Goggles of Revealing")
        add(ThaumometerItem.AURA_LEVEL_TRANSLATION_KEY, $$"%1$s μv")
        add(ThaumometerItem.NO_AURA_TRANSLATION_KEY, "No Aura detected.")
        AspectRegistry.ASPECTS.registrar.entrySet().forEach { (key, value) ->
            add(value.translationKey, value.id.path.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() })
        }
        INFUSED_STONES.forEach {
            add(it, it.id.path.split("_").joinToString(" ") { s -> s.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase(getDefault()) else c.toString() } })
        }

        add(ARCANE_SIGHT, "Arcane Sight")
        add(ARCANE_INSIGHT, "Arcane Insight")

        add(ARCANE_STONE)
        add(ARCANE_STONE_TILES)
    }


    inline fun <reified T> TranslationBuilder.add(holder: RegistrySupplier<T>) = add(holder, holder.id.path.split("_").joinToString(" ") { s -> s.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase(getDefault()) else c.toString() } })

    inline fun <reified T> TranslationBuilder.add(holder: RegistrySupplier<T>, name: String) = holder.get().let {
        when (it) {
            is Item -> add(it, name)
            is Block -> add(it, name)
            is Aspect -> add(it.translationKey, name)
            else -> add(holder.id, name)
        }
    }

}