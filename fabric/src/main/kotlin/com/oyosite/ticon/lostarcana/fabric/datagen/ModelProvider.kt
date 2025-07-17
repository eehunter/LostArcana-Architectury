package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.mixin.registry.sync.client.ItemModelsMixin
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates

class ModelProvider(dataOutput: FabricDataOutput) : FabricModelProvider(dataOutput) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators) {

    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerators) {
        itemModelGenerator.generateFlatItem(+VIS_CRYSTAL, ModelTemplates.FLAT_ITEM)
    }

    inline operator fun <reified T> RegistrySupplier<T>.unaryPlus() = get()
}