package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates

class ModelProvider(dataOutput: FabricDataOutput) : FabricModelProvider(dataOutput) {
    override fun generateBlockStateModels(bsmg: BlockModelGenerators) {
        INFUSED_STONES.forEach { bsmg.createGenericCube(+it) }
    }

    override fun generateItemModels(img: ItemModelGenerators) {
        img.generateFlatItem(+VIS_CRYSTAL, ModelTemplates.FLAT_ITEM)
    }

    inline operator fun <reified T> RegistrySupplier<T>.unaryPlus() = get()
}