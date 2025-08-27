package com.oyosite.ticon.lostarcana.fabric.client

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.NITOR
import com.oyosite.ticon.lostarcana.block.VIS_LIGHT
import com.oyosite.ticon.lostarcana.blockentity.*
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.client.blockentity.ArcaneColumnRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.ArcaneWorkbenchScreen
import com.oyosite.ticon.lostarcana.client.blockentity.CrucibleBlockEntityRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.MagicBricksBlockEntityRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.PedestalRenderer
import com.oyosite.ticon.lostarcana.item.GOGGLES_OF_REVEALING
import com.oyosite.ticon.lostarcana.item.THAUMOMETER
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.item.WAND_ITEM
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry
import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
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

        BlockEntityRendererRegistry.register(MAGIC_BRICKS_BLOCK_ENTITY.value()) { MagicBricksBlockEntityRenderer() as BlockEntityRenderer<MagicBricksBlockEntity> }
        BlockEntityRendererRegistry.register(ARCANE_COLUMN_BLOCK_ENTITY.value()) { ArcaneColumnRenderer() as BlockEntityRenderer<ArcaneColumnBlockEntity> }
        BlockEntityRendererRegistry.register(RECHARGE_PEDESTAL_BLOCK_ENTITY.value()) { PedestalRenderer<RechargePedestalBlockEntity>() }
        BlockEntityRendererRegistry.register(ARCANE_PEDESTAL_BLOCK_ENTITY.value()) { PedestalRenderer<ArcanePedestalBlockEntity>() }
        BlockEntityRendererRegistry.register(CRUCIBLE_BLOCK_ENTITY.value()) { CrucibleBlockEntityRenderer(it) }

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), *INFUSED_STONES.map<RegistrySupplier<out Block>, Block>(RegistrySupplier<out Block>::get).toTypedArray())

        MenuScreens.register(ARCANE_WORKBENCH_MENU_SCREEN.value(), ::ArcaneWorkbenchScreen)

        LostArcanaClient.initClient()
    }
}
