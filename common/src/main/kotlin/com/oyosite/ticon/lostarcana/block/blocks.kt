package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.BlockProperties
import com.oyosite.ticon.lostarcana.LostArcana.MOD_ID
import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.aspect.setStaticAspects
import com.oyosite.ticon.lostarcana.aspect.times
import com.oyosite.ticon.lostarcana.item.times
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.state.BlockBehaviour

val BLOCK_REGISTRY: DeferredRegister<Block> = DeferredRegister.create(MOD_ID, Registries.BLOCK)
/** Returns a new instance of BlockBehaviour.Properties each time it is referenced.*/
val prop: BlockProperties get() = BlockBehaviour.Properties.of()

// Why do I make stuff so cursed?
val TEST_BLOCK = "test_block" % { Block(prop) } % {}

val INFUSED_STONES = PRIMAL_ASPECTS.map {
    "${it.id.path}_infused_stone" % { InfusedStoneBlock(BlockProperties.ofFullCopy(Blocks.STONE), it) } % Pair({}, {setStaticAspects(4*it)})
}

val ALCHEMICAL_BRASS_BLOCK = "alchemical_brass_block" % { Block(BlockProperties.ofFullCopy(Blocks.COPPER_BLOCK)) } % {}
val THAUMIUM_BLOCK = "thaumium_block" % { Block(BlockProperties.ofFullCopy(Blocks.IRON_BLOCK)) } % {}

val ARCANE_STONE = "arcane_stone" % { Block(BlockProperties.ofFullCopy(Blocks.STONE)) } % {}
val ARCANE_STONE_TILES = "arcane_stone_tiles" % { Block(BlockProperties.ofFullCopy(Blocks.STONE)) } % {}
val ARCANE_STONE_PILLAR = "arcane_stone_pillar" % { RotatedPillarBlock(BlockProperties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops()) } % {}

val ARCANE_STONE_SLAB = "arcane_stone_slab" % { SlabBlock(BlockProperties.ofFullCopy(Blocks.STONE_SLAB)) } % {}
val ARCANE_STONE_TILE_SLAB = "arcane_stone_tile_slab" % { SlabBlock(BlockProperties.ofFullCopy(Blocks.STONE_SLAB)) } % {}
val ARCANE_STONE_STAIRS = "arcane_stone_stairs" % { StairBlock((+ARCANE_STONE).defaultBlockState(), BlockProperties.ofFullCopy(Blocks.STONE_STAIRS)) } % {}
val ARCANE_STONE_TILE_STAIRS = "arcane_stone_tile_stairs" % { StairBlock((+ARCANE_STONE_TILES).defaultBlockState(), BlockProperties.ofFullCopy(Blocks.STONE_STAIRS)) } % {}

val RECHARGE_PEDESTAL = "recharge_pedestal" % { RechargePedestal(prop.noOcclusion()) } % {}
val MODULAR_RECHARGE_PEDESTAL = "modular_recharge_pedestal" % { RechargePedestal(prop.noOcclusion(), 2) } % {}

val MAGIC_BRICKS = "magic_bricks" % { MagicBricksBlock(prop.isSuffocating { _,_,_ -> false }.isViewBlocking { _,_,_ -> false }.noOcclusion()) } % {}
val ARCANE_COLUMN = "arcane_column" % { ArcaneColumn(prop.isSuffocating { _, _, _ -> false }.isViewBlocking { _, _, _ -> false }.noOcclusion()) } % {}

val MULTIBLOCK_PLACEHOLDER = "multiblock_placeholder" % { MultiblockPlaceholder(prop.noOcclusion()) }

val GREATWOOD_LOG = "greatwood_log" % { RotatedPillarBlock(BlockProperties.ofFullCopy(Blocks.OAK_LOG)) } % {}
val GREATWOOD_PLANKS = "greatwood_planks" % { Block(BlockProperties.ofFullCopy(Blocks.OAK_PLANKS)) } % {}
val GREATWOOD_LEAVES = "greatwood_leaves" % { LeavesBlock(BlockProperties.ofFullCopy(Blocks.OAK_LEAVES)) } % {}
val GREATWOOD_SAPLING = "greatwood_sapling" % { SaplingBlock(GREATWOOD_GROWER, BlockProperties.ofFullCopy(Blocks.OAK_SAPLING)) } % {}

val ARCANE_WORKBENCH = "arcane_workbench" % { ArcaneWorkbench(BlockProperties.ofFullCopy(Blocks.CRAFTING_TABLE)) } % {}
val CRUCIBLE = "crucible" % { Crucible(BlockProperties.ofFullCopy(Blocks.CAULDRON)) } % {}

val VIS_LIGHT = "vis_light" % { VisLight(prop.noOcclusion().lightLevel { 15 }) }

inline operator fun <reified T: Block> String.rem(noinline blockSupplier: ()->T): RegistrySupplier<T> =
    BLOCK_REGISTRY.register(this, blockSupplier)

inline operator fun <reified T: Block> RegistrySupplier<T>.rem(noinline itemPropertiesConfig: Item.Properties.()->Unit): RegistrySupplier<T> =
    this.also { this.id.path * { BlockItem(+this, Item.Properties().apply(itemPropertiesConfig)) } }

inline operator fun <reified T: Block> RegistrySupplier<T>.rem(configurators: Pair<Item.Properties.()->Unit, BlockItem.()->Unit>): RegistrySupplier<T> =
    this.also { this.id.path * { BlockItem(+this, Item.Properties().apply(configurators.first)).apply(configurators.second) } }
