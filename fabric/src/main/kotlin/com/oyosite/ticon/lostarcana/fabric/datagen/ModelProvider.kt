package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_PILLAR
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_SLAB
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_STAIRS
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_TILES
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_TILE_SLAB
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_TILE_STAIRS
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.item.GOGGLES_OF_REVEALING
import com.oyosite.ticon.lostarcana.item.SALIS_MUNDIS
import com.oyosite.ticon.lostarcana.item.THAUMOMETER
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.item.WAND_ITEM
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.client.model.Model
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.blockstates.BlockStateGenerator
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.data.models.model.TexturedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import java.util.Optional

class ModelProvider(dataOutput: FabricDataOutput) : FabricModelProvider(dataOutput) {

    val TINTED_CUBE_ALL = block("tinted_cube_all", TextureSlot.ALL)
    val TINTED_CUBE = block("tinted_cube", TextureSlot.UP, TextureSlot.DOWN, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST)

    override fun generateBlockStateModels(bsmg: BlockModelGenerators) = bsmg.run{


        INFUSED_STONES.forEach {
            bsmg.createTrivialBlock(+it, TextureMapping.cube(ResourceLocation.parse("block/stone")), TINTED_CUBE_ALL)
        }

        bsmg.createTrivialCube(+ARCANE_STONE)
        bsmg.createTrivialCube(+ARCANE_STONE_TILES)
        bsmg.createAxisAlignedPillarBlock(+ARCANE_STONE_PILLAR, TexturedModel.COLUMN)

        bsmg.blockStateOutput.accept(ARCANE_STONE_SLAB.makeSlabOf(ARCANE_STONE))
        bsmg.blockStateOutput.accept(ARCANE_STONE_TILE_SLAB.makeSlabOf(ARCANE_STONE_TILES))

        bsmg.blockStateOutput.accept(ARCANE_STONE_STAIRS.makeStairOf(ARCANE_STONE))
        bsmg.blockStateOutput.accept(ARCANE_STONE_TILE_STAIRS.makeStairOf(ARCANE_STONE_TILES))
    }

    context(bsmg: BlockModelGenerators)
    private fun RegistrySupplier<out StairBlock>.makeStairOf(stairOf: RegistrySupplier<out Block>): BlockStateGenerator {
        val texturedModel = TexturedModel.CUBE.get(stairOf.get());
        val r1 = ModelTemplates.STAIRS_INNER.create(this.get(), texturedModel.mapping, bsmg.modelOutput)
        val r2 = ModelTemplates.STAIRS_STRAIGHT.create(this.get(), texturedModel.mapping, bsmg.modelOutput)
        val r3 = ModelTemplates.STAIRS_OUTER.create(this.get(), texturedModel.mapping, bsmg.modelOutput)
        //bsmg.BlockFamilyProvider(TexturedModel.CUBE.get(stairOf.get()).mapping).stairs(this.get()).generateFor()

        return BlockModelGenerators.createStairs(this.get(), r1, r2, r3)
    }

    context(bsmg: BlockModelGenerators)
    private fun RegistrySupplier<out SlabBlock>.makeSlabOf(slabOf: RegistrySupplier<out Block>): BlockStateGenerator {
        val r1 = slabOf.id.withPrefix("block/")
        val texturedModel = TexturedModel.CUBE.get(slabOf.get());
        val r2 = ModelTemplates.SLAB_BOTTOM.create(this.get(), texturedModel.mapping, bsmg.modelOutput)
        val r3 = ModelTemplates.SLAB_TOP.create(this.get(), texturedModel.mapping, bsmg.modelOutput)
        return BlockModelGenerators.createSlab(this.get(), r2, r3, r1)
    }

    private fun block(parent: String, vararg requiredTextureKeys: TextureSlot): ModelTemplate = ModelTemplate(Optional.of(LostArcana.id("block/$parent")), Optional.empty(), *requiredTextureKeys)


    override fun generateItemModels(img: ItemModelGenerators) {
        img.generateFlatItem(+VIS_CRYSTAL, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+SALIS_MUNDIS, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+WAND_ITEM, ModelTemplates.FLAT_ITEM)
        img.register(+THAUMOMETER, ModelTemplates.TWO_LAYERED_ITEM, "_frame", "_lens")
        img.register(+GOGGLES_OF_REVEALING, ModelTemplates.TWO_LAYERED_ITEM, "_frame", "_lenses")
    }

    fun ItemModelGenerators.register(item: ItemLike, model: ModelTemplate, vararg suffixes: String = arrayOf("")){
        if(suffixes.size !in 1..3) throw IllegalArgumentException("Item model needs between 1 and 3 layers, inclusively.")
        val textureMap = TextureMapping().apply{
            suffixes.forEachIndexed { index, s -> put(TEXTURE_LAYERS[index], TextureMapping.getItemTexture(item.asItem(), s)) }
        }
        model.create(ModelLocationUtils.getModelLocation(item.asItem()), textureMap, this.output)
    }

    companion object{
        val TEXTURE_LAYERS = listOf(TextureSlot.LAYER0, TextureSlot.LAYER1, TextureSlot.LAYER2)
        val TEX_ZERO = TextureSlot.create("0")
        val PARTICLE = TextureSlot.create("particle")
    }

    inline operator fun <reified T> RegistrySupplier<T>.unaryPlus() = get()
}