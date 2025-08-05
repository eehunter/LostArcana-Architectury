package com.oyosite.ticon.lostarcana.fabric.datagen.book.categories

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel
import com.klikli_dev.modonomicon.registry.DataComponentRegistry
import com.klikli_dev.modonomicon.registry.ItemRegistry
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.util.thaumonomiconStack
import net.minecraft.world.item.ItemStack

class BasicsCategory(parent: SingleBookSubProvider): CategoryProvider(parent) {

    override fun generateEntryMap(): Array<out String> = arrayOf(
        "____________",
        "____________",
        "____________",
        "____________",
        "____________",
        "____________",
        "____________",
        "____________",
    )

    override fun generateEntries() {

    }

    override fun categoryName(): String = "Basics"

    override fun categoryIcon(): BookIconModel = BookIconModel.create(thaumonomiconStack)

    override fun categoryId(): String = ID

    companion object{
        const val ID: String = "basics"
    }
}