package com.oyosite.ticon.lostarcana.fabric.client

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.MagicBricksBlock
import com.oyosite.ticon.lostarcana.blockentity.ARCANE_COLUMN_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.ArcaneColumnBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.MAGIC_BRICKS_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.MagicBricksBlockEntity
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.client.blockentity.ArcaneColumnRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.MagicBricksBlockEntityRenderer
import com.oyosite.ticon.lostarcana.item.GOGGLES_OF_REVEALING
import com.oyosite.ticon.lostarcana.item.THAUMOMETER
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.world.level.block.Block

class LostArcanaFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        val infusedStoneBlocks: Array<Block> = INFUSED_STONES.map(RegistrySupplier<out Block>::get).toTypedArray()
        println("Number of tinted infused stone blocks: ${infusedStoneBlocks.size}")
        infusedStoneBlocks.forEach { println(it.name.toString()) }
        ColorProviderRegistry.BLOCK.register(LostArcanaClient.INFUSED_STONE_BLOCK_COLOR, *infusedStoneBlocks)
        ColorProviderRegistry.ITEM.register(LostArcanaClient.VIS_CRYSTAL_ITEM_COLOR, +VIS_CRYSTAL, *infusedStoneBlocks)
        ColorProviderRegistry.ITEM.register(LostArcanaClient.THAUMOMETER_ITEM_COLOR, +THAUMOMETER, +GOGGLES_OF_REVEALING)

        BlockEntityRendererRegistry.register(MAGIC_BRICKS_BLOCK_ENTITY.value()) { MagicBricksBlockEntityRenderer() as BlockEntityRenderer<MagicBricksBlockEntity> }
        BlockEntityRendererRegistry.register(ARCANE_COLUMN_BLOCK_ENTITY.value()) { ArcaneColumnRenderer() as BlockEntityRenderer<ArcaneColumnBlockEntity> }

        LostArcanaClient.initClient()
    }
}
