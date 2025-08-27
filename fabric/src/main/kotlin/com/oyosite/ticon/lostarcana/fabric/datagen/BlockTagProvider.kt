package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.block.ALCHEMICAL_BRASS_BLOCK
import com.oyosite.ticon.lostarcana.block.ARCANE_PEDESTAL
import com.oyosite.ticon.lostarcana.block.CRUCIBLE
import com.oyosite.ticon.lostarcana.block.GREATWOOD_LOG
import com.oyosite.ticon.lostarcana.block.GREATWOOD_PLANKS
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.NITOR
import com.oyosite.ticon.lostarcana.block.RECHARGE_PEDESTAL
import com.oyosite.ticon.lostarcana.block.THAUMIUM_BLOCK
import com.oyosite.ticon.lostarcana.tag.COMMON_ORES
import com.oyosite.ticon.lostarcana.tag.CRUCIBLE_HEAT_SOURCES
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import java.util.concurrent.CompletableFuture

class BlockTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider<Block>(output, Registries.BLOCK, registriesFuture), LostArcanaTagProvider<Block> {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        val infusedStoneBlocks = INFUSED_STONES.map(RegistrySupplier<out Block>::get).toTypedArray()
        BlockTags.MINEABLE_WITH_PICKAXE(
            *infusedStoneBlocks,
            +CRUCIBLE,
            +ALCHEMICAL_BRASS_BLOCK,
            +THAUMIUM_BLOCK,
            +ARCANE_PEDESTAL,
            +RECHARGE_PEDESTAL,
        )
        COMMON_ORES(*infusedStoneBlocks)
        BlockTags.MINEABLE_WITH_AXE(+GREATWOOD_LOG, +GREATWOOD_PLANKS)
        BlockTags.PLANKS(+GREATWOOD_PLANKS)
        BlockTags.LOGS(+GREATWOOD_LOG)
        CRUCIBLE_HEAT_SOURCES(+NITOR, Blocks.LAVA)
    }

    override fun getTagBuilder(key: TagKey<Block>): FabricTagProvider<Block>.FabricTagBuilder = getOrCreateTagBuilder(key)
}