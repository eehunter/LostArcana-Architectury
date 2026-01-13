package com.oyosite.ticon.lostarcana.fabric.client

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.NITOR
import com.oyosite.ticon.lostarcana.block.VIS_LIGHT
import com.oyosite.ticon.lostarcana.block.WARDED_JAR
import com.oyosite.ticon.lostarcana.blockentity.*
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.client.blockentity.*
import com.oyosite.ticon.lostarcana.item.*
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block

class LostArcanaFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        val infusedStoneBlocks: Array<Block> = INFUSED_STONES.map(RegistrySupplier<out Block>::get).toTypedArray()

        infusedStoneBlocks.forEach { println(it.name.toString()) }
        ColorProviderRegistry.BLOCK.register(LostArcanaClient.INFUSED_STONE_BLOCK_COLOR, *infusedStoneBlocks)
        ColorProviderRegistry.BLOCK.register(LostArcanaClient.NITOR_BLOCK_COLOR, +NITOR, +VIS_LIGHT)
        ColorProviderRegistry.ITEM.register(LostArcanaClient.VIS_CRYSTAL_ITEM_COLOR, +VIS_CRYSTAL, *infusedStoneBlocks)
        ColorProviderRegistry.ITEM.register(LostArcanaClient.THAUMOMETER_ITEM_COLOR, +THAUMOMETER, +GOGGLES_OF_REVEALING)
        ColorProviderRegistry.ITEM.register(LostArcanaClient.WAND_ITEM_COLOR, +WAND_ITEM)
        ColorProviderRegistry.ITEM.register(LostArcanaClient.NITOR_ITEM_COLOR, +NITOR)
        ColorProviderRegistry.ITEM.register(LostArcanaClient.RAW_ASPECTED_ITEM_COLOR, +ESSENTIA_BUCKET_ITEM)

        BlockEntityRendererRegistry.register(MAGIC_BRICKS_BLOCK_ENTITY.value()) { MagicBricksBlockEntityRenderer() }
        BlockEntityRendererRegistry.register(ARCANE_COLUMN_BLOCK_ENTITY.value()) { ArcaneColumnRenderer() }
        BlockEntityRendererRegistry.register(RECHARGE_PEDESTAL_BLOCK_ENTITY.value()) { PedestalRenderer<RechargePedestalBlockEntity>() }
        BlockEntityRendererRegistry.register(ARCANE_PEDESTAL_BLOCK_ENTITY.value()) { PedestalRenderer<ArcanePedestalBlockEntity>() }
        BlockEntityRendererRegistry.register(CRUCIBLE_BLOCK_ENTITY.value()) { CrucibleBlockEntityRenderer(it) }
        BlockEntityRendererRegistry.register(WARDED_JAR_BLOCK_ENTITY.value()) { WardedJarRenderer() }
        BlockEntityRendererRegistry.register(FLUX_SCRUBBER_BLOCK_ENTITY.value()) { ScrubberRenderer() }
        BlockEntityRendererRegistry.register(VIS_GENERATOR_BLOCK_ENTITY.value()) { VisGeneratorRenderer() }
        BlockEntityRendererRegistry.register(VIRIAL_NODE_BLOCK_ENTITY.value()) { VirialNodeRenderer() }
        BlockEntityRendererRegistry.register(DISSOLVER_BLOCK_ENTITY.value()) { DissolverRenderer() }

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), *INFUSED_STONES.map<RegistrySupplier<out Block>, Block>(RegistrySupplier<out Block>::get).toTypedArray())
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), WARDED_JAR.value())

        MenuScreens.register(ARCANE_WORKBENCH_MENU_SCREEN.value(), ::ArcaneWorkbenchScreen)



        LostArcanaClient.initClient()
    }
}
