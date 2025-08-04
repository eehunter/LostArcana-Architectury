package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.AspectStack
import com.oyosite.ticon.lostarcana.block.*
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

class BlockLootTableProvider(dataOutput: FabricDataOutput, registryLookup: CompletableFuture<HolderLookup.Provider>) : FabricBlockLootTableProvider(dataOutput, registryLookup) {
    override fun generate() {
        INFUSED_STONES.forEach {
            val block = +it
            val crystal = block.aspect.lootItem
            val crystals = uniformRolls(1,6).add(crystal)
            val explosionReduced = applyExplosionDecay(block, crystals)
            val lootTable = LootTable.lootTable().withPool(explosionReduced)
            add(block, lootTable)
        }
        listOf(
            ARCANE_STONE,
            ARCANE_STONE_TILES,
            ARCANE_STONE_PILLAR,
            ARCANE_STONE_STAIRS,
            ARCANE_STONE_TILE_STAIRS,
            ARCANE_WORKBENCH,
            RECHARGE_PEDESTAL,
            GREATWOOD_LOG,
            GREATWOOD_PLANKS,
        ).allDropSelf

        listOf(
            ARCANE_STONE_SLAB,
            ARCANE_STONE_TILE_SLAB
        ).asBlocks.forEach(::createSlabItemTable)

    }

    override fun generate(biConsumer: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {
        biConsumer.accept(ArcaneColumn.multiblockLootTable, dropsMultiplied(+ARCANE_STONE_PILLAR, 5))
        super.generate(biConsumer)
    }

    fun dropsMultiplied(item: ItemLike, amount: Int): LootTable.Builder =
        LootTable.lootTable().withPool(applyExplosionDecay(item, LootPool.lootPool().setRolls(ConstantValue(amount.toFloat())).add(LootItem.lootTableItem(item))))


    val Collection<RegistrySupplier<out Block>>.asBlocks get() = map<RegistrySupplier<out Block>, Block>(RegistrySupplier<out Block>::get)

    val Collection<RegistrySupplier<out Block>>.allDropSelf get() = map<RegistrySupplier<out Block>, Block>(RegistrySupplier<out Block>::get).forEach(::dropSelf)

    fun uniformRolls(min: Number, max: Number) = LootPool.lootPool().setRolls(UniformGenerator(ConstantValue(min.toFloat()), ConstantValue(max.toFloat())))
    fun applyAspectFunction(aspect: AspectStack) = SetComponentsFunction.setComponent(ASPECT_COMPONENT, aspect)

    val Aspect.lootItem get() = LootItem.lootTableItem(+VIS_CRYSTAL).apply(applyAspectFunction(+this))
}