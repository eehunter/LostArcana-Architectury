package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.advancement.ThaumometerScanCriterionTrigger
import com.oyosite.ticon.lostarcana.block.*
import com.oyosite.ticon.lostarcana.entity.AURA_NODE
import com.oyosite.ticon.lostarcana.fabric.datagen.recipe.builder.BasicSalisMundisRecipeBuilder
import com.oyosite.ticon.lostarcana.item.*
import com.oyosite.ticon.lostarcana.recipe.CastingItemModificationRecipe
import com.oyosite.ticon.lostarcana.recipe.SpecialCastingItemModificationRecipe
import com.oyosite.ticon.lostarcana.tag.*
import com.oyosite.ticon.lostarcana.unaryPlus
import com.oyosite.ticon.lostarcana.util.thaumonomiconStack
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder.shaped
import net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Blocks
import java.util.Optional
import java.util.concurrent.CompletableFuture

class RecipeProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>): FabricRecipeProvider(output, registriesFuture) {
    override fun buildRecipes(exporter: RecipeOutput) {
        shapeless(RecipeCategory.MISC, +SALIS_MUNDIS)
            .requires(+VIS_CRYSTAL, 3)
            .requires(COMMON_REDSTONE_DUSTS)
            .unlockedBy("has_vis_crystal", hasItems(+VIS_CRYSTAL))
            .uniqueVisCrystal
            .save(exporter)
        shapeless(RecipeCategory.BUILDING_BLOCKS, +GREATWOOD_PLANKS, 4)
            .requires(GREATWOOD_LOGS)
            .unlockedBy("has_greatwood", hasItems(GREATWOOD_LOGS))
            .save(exporter)
        shaped(RecipeCategory.TOOLS, +CRUDE_CASTER_GAUNTLET, 1)
            .pattern("NVN")
            .pattern("SQS")
            .pattern("SPS")
            .define('N', COMMON_IRON_NUGGETS)
            .define('V', +VIS_CRYSTAL)
            .define('S', COMMON_STICKS)
            .define('Q', COMMON_QUARTZ)
            .define('P', WOOD_PLANKS)
            .unlockedBy("obtained_quartz", hasItems(COMMON_QUARTZ))
            .unlockedBy("obtained_vis_crystal", hasItems(+VIS_CRYSTAL))
            .unlockedBy("obtained_iron", hasItems(COMMON_IRON_INGOTS, COMMON_IRON_NUGGETS))
            .save(exporter)
        shaped(RecipeCategory.TOOLS, +GOGGLES_OF_REVEALING, 1)
            .pattern(" L ")
            .pattern("TGT")
            .define('L', COMMON_LEATHERS)
            .define('T', +THAUMOMETER)
            .define('G', COMMON_GOLD_INGOTS)
            .unlockedBy("scanned_aura_node", ThaumometerScanCriterionTrigger.scan(AURA_NODE))
            .save(exporter)
        shaped(RecipeCategory.BUILDING_BLOCKS, +ARCANE_STONE, 8)
            .pattern("SSS")
            .pattern("SCS")
            .pattern("SSS")
            .define('S', Blocks.STONE)
            .define('C', +VIS_CRYSTAL)
            .unlockedBy("got_vis_crystal", hasItems(+VIS_CRYSTAL))
            .save(exporter)
        shaped(RecipeCategory.BUILDING_BLOCKS, +ARCANE_STONE_TILES, 4)
            .pattern("AA")
            .pattern("AA")
            .define('A', +ARCANE_STONE)
            .unlockedBy("got_vis_crystal", hasItems(+VIS_CRYSTAL))
            .save(exporter)
        shaped(RecipeCategory.BUILDING_BLOCKS, +ARCANE_STONE_PILLAR, 2)
            .pattern("A")
            .pattern("A")
            .define('A', +ARCANE_STONE)
            .unlockedBy("got_vis_crystal", hasItems(+VIS_CRYSTAL))
            .save(exporter)
        slab(exporter, RecipeCategory.BUILDING_BLOCKS, +ARCANE_STONE_SLAB, +ARCANE_STONE)
        slab(exporter, RecipeCategory.BUILDING_BLOCKS, +ARCANE_STONE_TILE_SLAB, +ARCANE_STONE_TILES)
        stair(exporter, +ARCANE_STONE_STAIRS, +ARCANE_STONE)
        stair(exporter, +ARCANE_STONE_TILE_STAIRS, +ARCANE_STONE_TILES)
        shaped(RecipeCategory.TOOLS, +IRON_WAND_CAP)
            .pattern("NNN")
            .pattern("NCN")
            .define('N', Items.IRON_NUGGET)
            .define('C', +VIS_CRYSTAL)
            .unlockedBy("got_vis_crystal", hasItems(+VIS_CRYSTAL))
            .save(exporter)
        shapeless(RecipeCategory.TOOLS, +WOOD_WAND_CORE)
            .requires(+VIS_CRYSTAL)
            .requires(Items.STICK)
            .unlockedBy("got_vis_crystal", hasItems(+VIS_CRYSTAL))
            .save(exporter)
        shaped(RecipeCategory.TOOLS, +WAND_ITEM)
            .pattern("  C")
            .pattern(" S ")
            .pattern("C  ")
            .define('C', +IRON_WAND_CAP)
            .define('S', +WOOD_WAND_CORE)
            .unlockedBy("got_wand_cap", hasItems(+IRON_WAND_CAP))
            .save(exporter)
        shaped(RecipeCategory.TOOLS, +THAUMOMETER)
            .pattern(" N ")
            .pattern("NGN")
            .pattern(" N ")
            .define('N', COMMON_GOLD_NUGGETS)
            .define('G', COMMON_GLASS_PANES)
            .unlockedBy("got_thaumometer", hasItems(+THAUMOMETER))//TODO: Should be based on obtaining the Arcane Workbench
            .arcaneWorkbench
            .setCrystals(1,1,1,1,1,1)
            .setVis(5f)
            .save(exporter)
        BasicSalisMundisRecipeBuilder(+ARCANE_WORKBENCH, Blocks.CRAFTING_TABLE).save(exporter)
        BasicSalisMundisRecipeBuilder(thaumonomiconStack, Blocks.BOOKSHELF).save(exporter, LostArcana.id("thaumonomicon"))

        shaped(RecipeCategory.TOOLS, +GOLD_WAND_CAP)
            .pattern("NNN")
            .pattern("N N")
            .define('N', COMMON_GOLD_NUGGETS)
            .unlockedBy("scanned_arcane_workbench", ThaumometerScanCriterionTrigger.scan(ARCANE_WORKBENCH))
            .unlockedBy("scanned_gold_ingot", ThaumometerScanCriterionTrigger.scanItem("gold_ingot"))
            .arcaneWorkbench
            .setCrystals(0,0,1,0,0,1)
            .setVis(10f)
            .save(exporter)
        shaped(RecipeCategory.TOOLS, +GREATWOOD_WAND_CORE)
            .pattern("W")
            .pattern("W")
            .define('W', +GREATWOOD_LOG)
            .unlockedBy("scanned_arcane_workbench", ThaumometerScanCriterionTrigger.scan(ARCANE_WORKBENCH))
            .unlockedBy("scanned_greatwood_log", ThaumometerScanCriterionTrigger.scan(GREATWOOD_LOG))
            .arcaneWorkbench
            .setCrystals(1,1,0,0,0,1)
            .setVis(15f)
            .save(exporter)

        exporter.accept(
            LostArcana.id("wand_top_cap_exchange"),
            CastingItemModificationRecipe(
                Ingredient.of(+WAND_ITEM),
                Ingredient.of(WAND_CAPS),
                LostArcana.id("wand_cap"),
                2,
                Optional.empty()
            ),
            null
        )
        exporter.accept(
                LostArcana.id("wand_bottom_cap_exchange"),
        CastingItemModificationRecipe(
            Ingredient.of(+WAND_ITEM),
            Ingredient.of(WAND_CAPS),
            LostArcana.id("wand_cap_2"),
            5,
            Optional.empty()
        ),
        null
        )
        exporter.accept(
            LostArcana.id("wand_core_exchange"),
            CastingItemModificationRecipe(
                Ingredient.of(+WAND_ITEM),
                Ingredient.of(WAND_CORES),
                LostArcana.id("wand_core"),
                4,
                Optional.empty()
            ),
            null
        )
        exporter.accept(
            LostArcana.id("special_casting_item_modification"),
            SpecialCastingItemModificationRecipe(),
            null
        )
    }

    fun stair(exporter: RecipeOutput, stair: ItemLike, block: ItemLike){
        stairBuilder(stair, Ingredient.of(block)).unlockedBy("has_base_block", hasItems(block)).save(exporter)
    }

    fun hasItems(vararg items: TagKey<Item>) = hasItems(*items.map { ItemPredicate.Builder.item().of(it).build() }.toTypedArray())
    fun hasItems(vararg items: ItemPredicate) = InventoryChangeTrigger.TriggerInstance.hasItems(*items)
    fun hasItems(vararg items: ItemLike) = InventoryChangeTrigger.TriggerInstance.hasItems(*items)
}