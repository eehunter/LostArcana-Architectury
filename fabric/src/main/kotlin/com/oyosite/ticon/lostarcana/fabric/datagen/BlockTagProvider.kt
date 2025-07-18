package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.tag.COMMON_ORES
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import java.util.concurrent.CompletableFuture

class BlockTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider<Block>(output, Registries.BLOCK, registriesFuture) {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        val infusedStoneBlocks = INFUSED_STONES.map(RegistrySupplier<out Block>::get).toTypedArray()
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(*infusedStoneBlocks)
        getOrCreateTagBuilder(COMMON_ORES).add(*infusedStoneBlocks)

    }
}