package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.client.model.Model
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import java.util.Optional

class ModelProvider(dataOutput: FabricDataOutput) : FabricModelProvider(dataOutput) {

    val TINTED_CUBE_ALL = block("tinted_cube_all", TextureSlot.ALL)
    val TINTED_CUBE = block("tinted_cube", TextureSlot.UP, TextureSlot.DOWN, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST)

    override fun generateBlockStateModels(bsmg: BlockModelGenerators) {


        INFUSED_STONES.forEach {
            bsmg.createTrivialBlock(+it, TextureMapping.cube(ResourceLocation.parse("block/stone")), TINTED_CUBE_ALL)
        }
    }

    private fun block(parent: String, vararg requiredTextureKeys: TextureSlot): ModelTemplate = ModelTemplate(Optional.of(LostArcana.id("block/$parent")), Optional.empty(), *requiredTextureKeys)


    override fun generateItemModels(img: ItemModelGenerators) {
        img.generateFlatItem(+VIS_CRYSTAL, ModelTemplates.FLAT_ITEM)
    }

    inline operator fun <reified T> RegistrySupplier<T>.unaryPlus() = get()
}