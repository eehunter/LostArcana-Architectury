package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.AspectStack
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.unaryPlus
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import java.util.concurrent.CompletableFuture

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
    }


    fun uniformRolls(min: Number, max: Number) = LootPool.lootPool().setRolls(UniformGenerator(ConstantValue(min.toFloat()), ConstantValue(max.toFloat())))
    fun applyAspectFunction(aspect: AspectStack) = SetComponentsFunction.setComponent(ASPECT_COMPONENT, aspect)

    val Aspect.lootItem get() = LootItem.lootTableItem(+VIS_CRYSTAL).apply(applyAspectFunction(+this))
}