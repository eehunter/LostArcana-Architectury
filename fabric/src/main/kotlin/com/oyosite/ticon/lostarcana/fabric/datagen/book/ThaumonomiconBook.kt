package com.oyosite.ticon.lostarcana.fabric.datagen.book

import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.fabric.datagen.book.categories.BasicsCategory

class ThaumonomiconBook(lang: ModonomiconLanguageProvider): SingleBookSubProvider(ID, LostArcana.MOD_ID, lang) {
    override fun registerDefaultMacros() {

    }

    override fun generateCategories() {
        add(BasicsCategory(this).generate())
    }

    override fun bookName(): String = "Thaumonomicon"

    override fun bookTooltip(): String = "Guidebook for Lost Arcana"


    companion object{
        val ID = "thaumonomicon"
    }
}