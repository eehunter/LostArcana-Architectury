package com.oyosite.ticon.lostarcana.fabric.datagen.book.basics

import com.klikli_dev.modonomicon.ModonomiconFabric
import com.klikli_dev.modonomicon.api.ModonomiconAPI
import com.klikli_dev.modonomicon.api.ModonomiconConstants
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase
import com.klikli_dev.modonomicon.api.datagen.EntryProvider
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel
import com.klikli_dev.modonomicon.api.events.ModonomiconEvent
import com.klikli_dev.modonomicon.book.Book
import com.klikli_dev.modonomicon.client.gui.BookGuiManager
import com.klikli_dev.modonomicon.events.ModonomiconEvents
import com.klikli_dev.modonomicon.item.ModonomiconItem
import com.klikli_dev.modonomicon.registry.ItemRegistry
import com.mojang.datafixers.util.Pair
import com.oyosite.ticon.lostarcana.MCPair
import com.oyosite.ticon.lostarcana.fabric.datagen.book.ThaumonomiconBook
import com.oyosite.ticon.lostarcana.util.thaumonomiconStack
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.ItemStack

class GettingStarted(parent: CategoryProviderBase) : EntryProvider(parent) {
    override fun generatePages() {
        this.page("start"){
            BookTextPageModel.create()
                .withTitle("Getting Started")
                .withText("")
        }


    }

    override fun entryName(): String = "Getting Started"

    override fun entryDescription(): String = "The Beginning"

    override fun entryBackground(): Pair<Int, Int> = MCPair.of(1, 0)

    override fun entryIcon(): BookIconModel = BookIconModel.create(thaumonomiconStack)

    override fun entryId(): String = "getting_started"


}