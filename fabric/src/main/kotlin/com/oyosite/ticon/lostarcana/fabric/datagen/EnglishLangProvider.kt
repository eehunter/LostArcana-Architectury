package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.Locale
import java.util.Locale.getDefault
import java.util.concurrent.CompletableFuture

class EnglishLangProvider( dataOutput: FabricDataOutput,  registryLookup: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(dataOutput, "en_us", registryLookup){
    override fun generateTranslations(
        registryLookup: HolderLookup.Provider,
        translationBuilder: TranslationBuilder
    ) = translationBuilder.run{
        add(VIS_CRYSTAL, "Vis Crystal")
        AspectRegistry.ASPECTS.registrar.entrySet().forEach { (key, value) ->
            add(value.translationKey, value.id.path.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() })
        }
    }

    inline fun <reified T> TranslationBuilder.add(holder: RegistrySupplier<T>, name: String) = holder.get().let {
        when (it) {
            is Item -> add(it, name)
            is Block -> add(it, name)
            is Aspect -> add(it.translationKey, name)
            else -> add(holder.id, name)
        }
    }

}