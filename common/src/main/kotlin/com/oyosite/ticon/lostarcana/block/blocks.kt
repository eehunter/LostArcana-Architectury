package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.BlockProperties
import com.oyosite.ticon.lostarcana.LostArcana.MOD_ID
import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.aspect.setStaticAspects
import com.oyosite.ticon.lostarcana.aspect.times
import com.oyosite.ticon.lostarcana.block.dissolver.DissolverBlock
import com.oyosite.ticon.lostarcana.block.dissolver.DissolverBlockItem
import com.oyosite.ticon.lostarcana.block.dissolver.DissolverPlaceholder
import com.oyosite.ticon.lostarcana.block.fluid.ESSENTIA_FLUID
import com.oyosite.ticon.lostarcana.block.fluid.EssentiaLiquidBlock
import com.oyosite.ticon.lostarcana.block.generator.VisGeneratorBlock
import com.oyosite.ticon.lostarcana.block.scrubber.ScrubberBaseBlock
import com.oyosite.ticon.lostarcana.block.virial.VirialNodeBlock
import com.oyosite.ticon.lostarcana.item.SINGLE_FLUID_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.item.TAB
import com.oyosite.ticon.lostarcana.item.times
import com.oyosite.ticon.lostarcana.unaryPlus
import com.oyosite.ticon.lostarcana.util.ImmutableFluidStack.Companion.immutableCopy
import dev.architectury.fluid.FluidStack
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor

val BLOCK_REGISTRY: DeferredRegister<Block> = DeferredRegister.create(MOD_ID, Registries.BLOCK)
/** Returns a new instance of BlockBehaviour.Properties each time it is referenced.*/
val prop: BlockProperties get() = BlockBehaviour.Properties.of()
val alwaysFalseStatePredicate: BlockBehaviour.StatePredicate = BlockBehaviour.StatePredicate{ _,_,_ -> false }

// Why do I make stuff so cursed?
val TEST_BLOCK = "test_block" % { Block(prop) } % {}

val INFUSED_STONES = PRIMAL_ASPECTS.map {
    "${it.id.path}_infused_stone" % { InfusedStoneBlock(BlockProperties.ofFullCopy(Blocks.STONE), it) } % Pair({addToTab}, {setStaticAspects(4*it)})
}

val ALCHEMICAL_BRASS_BLOCK = "alchemical_brass_block" % { Block(BlockProperties.ofFullCopy(Blocks.COPPER_BLOCK)) } % {addToTab}
val THAUMIUM_BLOCK = "thaumium_block" % { Block(BlockProperties.ofFullCopy(Blocks.IRON_BLOCK)) } % {addToTab}

val ARCANE_STONE = "arcane_stone" % { Block(BlockProperties.ofFullCopy(Blocks.STONE)) } % {addToTab}
val ARCANE_STONE_TILES = "arcane_stone_tiles" % { Block(BlockProperties.ofFullCopy(Blocks.STONE)) } % {addToTab}
val ARCANE_STONE_PILLAR = "arcane_stone_pillar" % { RotatedPillarBlock(BlockProperties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops()) } % {addToTab}

val ARCANE_STONE_SLAB = "arcane_stone_slab" % { SlabBlock(BlockProperties.ofFullCopy(Blocks.STONE_SLAB)) } % {addToTab}
val ARCANE_STONE_TILE_SLAB = "arcane_stone_tile_slab" % { SlabBlock(BlockProperties.ofFullCopy(Blocks.STONE_SLAB)) } % {addToTab}
val ARCANE_STONE_STAIRS = "arcane_stone_stairs" % { StairBlock((+ARCANE_STONE).defaultBlockState(), BlockProperties.ofFullCopy(Blocks.STONE_STAIRS)) } % {addToTab}
val ARCANE_STONE_TILE_STAIRS = "arcane_stone_tile_stairs" % { StairBlock((+ARCANE_STONE_TILES).defaultBlockState(), BlockProperties.ofFullCopy(Blocks.STONE_STAIRS)) } % {addToTab}

val RECHARGE_PEDESTAL = "recharge_pedestal" % { RechargePedestal(prop.noOcclusion()) } % {addToTab}
val MODULAR_RECHARGE_PEDESTAL = "modular_recharge_pedestal" % { RechargePedestal(prop.noOcclusion(), 2) } % {addToTab}

val MAGIC_BRICKS = "magic_bricks" % { MagicBricksBlock(prop.isSuffocating { _,_,_ -> false }.isViewBlocking { _,_,_ -> false }.noOcclusion()) } % {}
val ARCANE_COLUMN = "arcane_column" % { ArcaneColumn(prop.isSuffocating { _, _, _ -> false }.isViewBlocking { _, _, _ -> false }.noOcclusion()) } % {}

val MULTIBLOCK_PLACEHOLDER = "multiblock_placeholder" % { MultiblockPlaceholder(prop.noOcclusion()) }

val GREATWOOD_LOG = "greatwood_log" % { RotatedPillarBlock(BlockProperties.ofFullCopy(Blocks.OAK_LOG)) } % {addToTab}
val GREATWOOD_PLANKS = "greatwood_planks" % { Block(BlockProperties.ofFullCopy(Blocks.OAK_PLANKS)) } % {addToTab}
val GREATWOOD_LEAVES = "greatwood_leaves" % { LeavesBlock(BlockProperties.ofFullCopy(Blocks.OAK_LEAVES)) } % {addToTab}
val GREATWOOD_SAPLING = "greatwood_sapling" % { SaplingBlock(GREATWOOD_GROWER, BlockProperties.ofFullCopy(Blocks.OAK_SAPLING)) } % {addToTab}

val ARCANE_WORKBENCH = "arcane_workbench" % { ArcaneWorkbench(BlockProperties.ofFullCopy(Blocks.CRAFTING_TABLE)) } % {addToTab}
val CRUCIBLE = "crucible" % { Crucible(BlockProperties.ofFullCopy(Blocks.CAULDRON)) } % {addToTab}
val ARCANE_PEDESTAL = "arcane_pedestal" % { ArcanePedestal(BlockProperties.ofFullCopy(Blocks.STONE).noOcclusion()) } % {addToTab}

val ESSENTIA_SMELTERY = "essentia_smeltery" % { EssentiaSmeltery(BlockProperties.ofFullCopy(Blocks.FURNACE)) } % {addToTab}
val WARDED_JAR = "warded_jar" % { WardedJar(prop.noOcclusion().isSuffocating { _, _, _ -> false }.isViewBlocking { _, _, _ -> false }) } % {addToTab.component(SINGLE_FLUID_STORAGE_COMPONENT, FluidStack.empty().immutableCopy)}
val ESSENTIA_FLUID_BLOCK = "essentia_fluid" % { EssentiaLiquidBlock(ESSENTIA_FLUID::get, BlockProperties.ofFullCopy(Blocks.WATER)) }

val VIS_LIGHT = "vis_light" % { VisLight(prop.noCollission().noOcclusion().lightLevel { 15 }) }
val NITOR = ("nitor" % { VisLight(prop.noCollission().noOcclusion().lightLevel { 15 }) }) % {addToTab}//{component(DataComponents.DYED_COLOR, DyedItemColor(VisLight.DEFAULT_COLOR, false))}

val ELEMENTAL_GEODE_MAP_COLORS = listOf(MapColor.SAND, MapColor.FIRE, MapColor.COLOR_GRAY, MapColor.GRASS, MapColor.COLOR_LIGHT_BLUE, MapColor.CLAY)
val ELEMENTAL_GEODE_MATERIALS = List(PRIMAL_ASPECTS.size){
    CustomBuddingBlockCollection("${PRIMAL_ASPECTS[it].id.path}_crystal", ELEMENTAL_GEODE_MAP_COLORS[it])
}

val DISSOLVER_BLOCK = ("dissolver" % { DissolverBlock(prop.noOcclusion().isViewBlocking { _, _, _ -> false }) }).customItem<DissolverBlock>(::DissolverBlockItem){addToTab}
val DISSOLVER_PLACEHOLDER = ("dissolver_placeholder" % { DissolverPlaceholder(prop.noOcclusion().isViewBlocking { _, _, _ -> false }) })

val FLUX_SCRUBBER_BASE = ("flux_scrubber"  % { ScrubberBaseBlock(prop.noOcclusion().isViewBlocking(alwaysFalseStatePredicate)) }) % {addToTab}
val VIS_GENERATOR_BLOCK = "vis_generator" % { VisGeneratorBlock(prop.noOcclusion().isViewBlocking(alwaysFalseStatePredicate)) } % {addToTab}
val VIRIAL_NODE = "virial_node" % { VirialNodeBlock(prop.noOcclusion().isViewBlocking(alwaysFalseStatePredicate)) } % {addToTab}

inline operator fun <reified T: Block> String.rem(noinline blockSupplier: ()->T): RegistrySupplier<T> =
    BLOCK_REGISTRY.register(this, blockSupplier)

inline fun <reified T: Block> RegistrySupplier<T>.customItem(noinline blockItemSupplier: (Block, Item.Properties) -> BlockItem, noinline itemPropertiesConfig: Item.Properties.()->Unit)=
    this.also { it.id.path * { blockItemSupplier(+this, Item.Properties().apply(itemPropertiesConfig)) } }

inline operator fun <reified T: Block> RegistrySupplier<T>.rem(noinline itemPropertiesConfig: Item.Properties.()->Unit): RegistrySupplier<T> =
    this.also { this.id.path * { BlockItem(+this, Item.Properties().apply(itemPropertiesConfig)) } }

inline operator fun <reified T: Block> RegistrySupplier<T>.rem(configurators: Pair<Item.Properties.()->Unit, BlockItem.()->Unit>): RegistrySupplier<T> =
    this.also { this.id.path * { BlockItem(+this, Item.Properties().apply(configurators.first)).apply(configurators.second) } }

val Item.Properties.addToTab get() = `arch$tab`(TAB)