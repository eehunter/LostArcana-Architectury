package com.oyosite.ticon.lostarcana.client.blockentity

import com.mojang.blaze3d.systems.RenderSystem
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.ArcaneWorkbenchMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ArcaneWorkbenchScreen(menu: ArcaneWorkbenchMenu, inventory: Inventory, name: Component) : AbstractContainerScreen<ArcaneWorkbenchMenu>(menu, inventory, name) {


    override fun renderBg(
        guiGraphics: GuiGraphics,
        f: Float,
        i: Int,
        j: Int
    ) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        val x: Int = (width - imageWidth) / 2
        val y: Int = (height - imageWidth) / 2 + 5
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    override fun init() {
        super.init()
        //titleLabelX = (imageWidth - title)
    }

    companion object{
        val TEXTURE = LostArcana.id("textures/gui/container/arcane_workbench.png")
    }
}