package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.block.ARCANE_STONE
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_TILES
import com.oyosite.ticon.lostarcana.item.IRON_WAND_CAP
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.item.WAND_ITEM
import com.oyosite.ticon.lostarcana.unaryPlus
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder.shaped
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import java.util.concurrent.CompletableFuture

class RecipeProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>): FabricRecipeProvider(output, registriesFuture) {
    override fun buildRecipes(exporter: RecipeOutput) {
        shaped(RecipeCategory.BUILDING_BLOCKS, +ARCANE_STONE, 8)
            .pattern("SSS")
            .pattern("SCS")
            .pattern("SSS")
            .define('S', Blocks.STONE)
            .define('C', +VIS_CRYSTAL)
            .unlockedBy("got_vis_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(+VIS_CRYSTAL))
            .save(exporter)
        shaped(RecipeCategory.BUILDING_BLOCKS, +ARCANE_STONE_TILES, 4)
            .pattern("AA")
            .pattern("AA")
            .define('A', +ARCANE_STONE)
            .unlockedBy("got_vis_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(+VIS_CRYSTAL))
            .save(exporter)
        shaped(RecipeCategory.TOOLS, +IRON_WAND_CAP)
            .pattern("NNN")
            .pattern("NCN")
            .define('N', Items.IRON_NUGGET)
            .define('C', +VIS_CRYSTAL)
            .unlockedBy("got_vis_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(+VIS_CRYSTAL))
            .save(exporter)
        shaped(RecipeCategory.TOOLS, +WAND_ITEM)
            .pattern("  C")
            .pattern(" S ")
            .pattern("C  ")
            .define('C', +IRON_WAND_CAP)
            .define('S', Items.STICK)
            .unlockedBy("got_wand_cap", InventoryChangeTrigger.TriggerInstance.hasItems(+IRON_WAND_CAP))
            .save(exporter)
    }
}