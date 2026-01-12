package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.*
import com.oyosite.ticon.lostarcana.item.*
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.core.Holder
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.blockstates.BlockStateGenerator
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.blockstates.PropertyDispatch
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.model.*
import net.minecraft.data.models.model.TextureSlot.*
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import java.util.*

class ModelProvider(dataOutput: FabricDataOutput) : FabricModelProvider(dataOutput) {

    val TINTED_CUBE_ALL = block("tinted_cube_all", TextureSlot.ALL)
    val TINTED_CUBE = block("tinted_cube", TextureSlot.UP, TextureSlot.DOWN, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST)
    val INFUSED_STONE= block("infused_stone")

    val arcaneWorkbenchTextureMap = TextureMapping()(TOP, ARCANE_WORKBENCH, "_top")(BOTTOM, Blocks.OAK_PLANKS)(SIDE, ARCANE_WORKBENCH, "_side")

    operator fun TextureMapping.invoke(slot: TextureSlot, block: Holder<out Block>, suffix: String? = null) = invoke(slot, block.value(), suffix)
    operator fun TextureMapping.invoke(slot: TextureSlot, block: Block, suffix: String? = null) = apply{
        put(slot, suffix?.let { TextureMapping.getBlockTexture(block, it) }?: TextureMapping.getBlockTexture(block))
    }

    override fun generateBlockStateModels(bsmg: BlockModelGenerators) = bsmg.run{

        ELEMENTAL_GEODE_MATERIALS.forEach { geode ->
            //bsmg.createTrivialCube(+geode.block)
            //bsmg.createTrivialCube(+geode.buddingBlock)
            //bsmg.createAmethystCluster(+geode.smallBud)
            //bsmg.createAmethystCluster(+geode.mediumBud)
            //bsmg.createAmethystCluster(+geode.largeBud)
            //bsmg.createAmethystCluster(+geode.cluster)
            blockStateOutput.accept(MultiVariantGenerator.multiVariant(+geode.smallBud, Variant.variant().with(VariantProperties.MODEL, geode.smallBud.id.withPrefix("block/"))).with(createColumnWithFacing()))
            blockStateOutput.accept(MultiVariantGenerator.multiVariant(+geode.mediumBud, Variant.variant().with(VariantProperties.MODEL, geode.mediumBud.id.withPrefix("block/"))).with(createColumnWithFacing()))
            blockStateOutput.accept(MultiVariantGenerator.multiVariant(+geode.largeBud, Variant.variant().with(VariantProperties.MODEL, geode.largeBud.id.withPrefix("block/"))).with(createColumnWithFacing()))
            blockStateOutput.accept(MultiVariantGenerator.multiVariant(+geode.cluster, Variant.variant().with(VariantProperties.MODEL, geode.cluster.id.withPrefix("block/"))).with(createColumnWithFacing()))

        }

        bsmg.createTrivialBlock(+ARCANE_WORKBENCH, arcaneWorkbenchTextureMap, ModelTemplates.CUBE_BOTTOM_TOP)

        bsmg.createTrivialCube(+ALCHEMICAL_BRASS_BLOCK)
        bsmg.createTrivialCube(+THAUMIUM_BLOCK)

        bsmg.createTrivialBlock(+NITOR, TextureMapping.particle(LostArcana.id("item/nitor_flame")), ModelTemplate(Optional.of(LostArcana.id("item/nitor")), Optional.empty()))
        bsmg.createTrivialBlock(+VIS_LIGHT, TextureMapping.particle(LostArcana.id("item/nitor_flame")), ModelTemplate(Optional.of(LostArcana.id("item/nitor")), Optional.empty()))

        listOf(*INFUSED_STONES.toTypedArray(), RECHARGE_PEDESTAL,
            *ELEMENTAL_GEODE_MATERIALS.map(CustomBuddingBlockCollection::block).toTypedArray(),
            *ELEMENTAL_GEODE_MATERIALS.map(CustomBuddingBlockCollection::buddingBlock).toTypedArray()).forEach {
            val v = Variant.variant().with(VariantProperties.MODEL, it.id.withPrefix("block/"))
            val bsg = MultiVariantGenerator.multiVariant(it.get(), v)
            bsmg.blockStateOutput.accept(bsg)
        }

        bsmg.createTrivialCube(+ARCANE_STONE)
        bsmg.createTrivialCube(+ARCANE_STONE_TILES)
        bsmg.createAxisAlignedPillarBlock(+ARCANE_STONE_PILLAR, TexturedModel.COLUMN)

        bsmg.createAxisAlignedPillarBlock(+GREATWOOD_LOG, TexturedModel.COLUMN)
        bsmg.createTrivialCube(+GREATWOOD_PLANKS)
        bsmg.createTrivialCube(+GREATWOOD_LEAVES)
        // Creating the sapling blockstate here overwrites my item model, so I'm just adding the blockstate manually now.
        //bsmg.createCrossBlockWithDefaultItem(+GREATWOOD_SAPLING, BlockModelGenerators.TintState.NOT_TINTED)
        //bsmg.blockStateOutput.accept(MultiVariantGenerator.multiVariant(+GREATWOOD_SAPLING, Variant.variant().with(VariantProperties.MODEL, GREATWOOD_SAPLING.id.withPrefix("block/"))))//.with(PropertyDispatch()))
        //bsmg.blockStateOutput.accept(Variant.variant().with(VariantProperties.MODEL, GREATWOOD_SAPLING.id.withPrefix("block/")))

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

        img.generateFlatItem(+TALLOW, ModelTemplates.FLAT_ITEM)

        img.generateFlatItem(+ALCHEMICAL_BRASS_INGOT, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+ALCHEMICAL_BRASS_NUGGET, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+THAUMIUM_INGOT, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+THAUMIUM_NUGGET, ModelTemplates.FLAT_ITEM)

        img.generateFlatItem(+FLUXER, ModelTemplates.FLAT_ITEM)

        img.generateFlatItem(+PRISTINE_DEFLUXER, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+INTACT_DEFLUXER, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+PRISTINE_VIRIAL_ENGINE, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+INTACT_VIRIAL_ENGINE, ModelTemplates.FLAT_ITEM)

        img.generateFlatItem((+GREATWOOD_SAPLING).asItem(), ModelTemplates.FLAT_ITEM)
        ELEMENTAL_GEODE_MATERIALS.forEach { mat ->
            arrayOf(mat.smallBud, mat.mediumBud, mat.largeBud, mat.cluster).forEach {
                img.generateFlatItem((+it).asItem(), ModelTemplates.FLAT_ITEM)
            }
        }

        img.register((+NITOR).asItem(), ModelTemplates.TWO_LAYERED_ITEM, "_flame", "_dot")
        img.register(+ESSENTIA_BUCKET_ITEM, ModelTemplates.TWO_LAYERED_ITEM, "", "_liquid")

        img.generateFlatItem(+SALIS_MUNDIS, ModelTemplates.FLAT_ITEM)
        //img.generateFlatItem(+WAND_ITEM, ModelTemplates.FLAT_ITEM)
        img.register(+THAUMOMETER, ModelTemplates.TWO_LAYERED_ITEM, "_frame", "_lens")
        img.register(+GOGGLES_OF_REVEALING, ModelTemplates.TWO_LAYERED_ITEM, "_frame", "_lenses")

        img.generateFlatItem(+IRON_WAND_CAP, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+GOLD_WAND_CAP, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+WOOD_WAND_CORE, ModelTemplates.FLAT_ITEM)
        img.generateFlatItem(+GREATWOOD_WAND_CORE, ModelTemplates.FLAT_ITEM)

        img.register(+RECHARGE_PEDESTAL, ModelTemplate(Optional.of(LostArcana.id("block/recharge_pedestal")), Optional.empty()))
        img.register(+MODULAR_RECHARGE_PEDESTAL, ModelTemplate(Optional.of(LostArcana.id("block/recharge_pedestal")), Optional.empty()))
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