package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.tag.COMMON_ORES
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import java.util.concurrent.CompletableFuture

class BlockTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider<Block>(output, Registries.BLOCK, registriesFuture), LostArcanaTagProvider<Block> {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        val infusedStoneBlocks = INFUSED_STONES.map(RegistrySupplier<out Block>::get).toTypedArray()
        BlockTags.MINEABLE_WITH_PICKAXE(*infusedStoneBlocks)
        COMMON_ORES(*infusedStoneBlocks)

    }

    override fun getTagBuilder(key: TagKey<Block>): FabricTagProvider<Block>.FabricTagBuilder = getOrCreateTagBuilder(key)
}