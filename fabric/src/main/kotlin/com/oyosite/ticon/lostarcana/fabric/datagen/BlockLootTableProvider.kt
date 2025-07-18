package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.InfusedStoneBlock
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.minecraft.world.level.storage.loot.entries.SequentialEntry
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider
import java.util.concurrent.CompletableFuture

class BlockLootTableProvider(dataOutput: FabricDataOutput, registryLookup: CompletableFuture<HolderLookup.Provider>) : FabricBlockLootTableProvider(dataOutput, registryLookup) {
    override fun generate() {
        /*val oneToSix: NumberProvider = UniformInt.of(1, 1) as NumberProvider
        val pool = LootPool.lootPool().setRolls(oneToSix)
        val pool2: (RegistrySupplier<InfusedStoneBlock>)-> LootPool.Builder = { pool.with() }
        val explosionCondition: (RegistrySupplier<InfusedStoneBlock>)-> ConditionUserBuilder<*> = { applyExplosionCondition(+it, pool2(it)) }
        INFUSED_STONES.forEach {
            val block = +it
            val crystal = ItemStack(+VIS_CRYSTAL)
            crystal.applyComponents(DataComponentPatch.builder().set(ASPECT_COMPONENT, +block.aspect).build())
            explosionCondition(it).unwrap()
            createSilkTouchDispatchTable(+it, LootItem.lootTableItem((+it).aspect)))
        }*/
    }
}