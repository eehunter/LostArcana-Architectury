package com.oyosite.ticon.lostarcana.fabric.datagen


import com.oyosite.ticon.lostarcana.block.GREATWOOD_LOG
import com.oyosite.ticon.lostarcana.block.GREATWOOD_PLANKS
import com.oyosite.ticon.lostarcana.block.NITOR
import com.oyosite.ticon.lostarcana.block.WARDED_JAR
import com.oyosite.ticon.lostarcana.item.GOLD_WAND_CAP
import com.oyosite.ticon.lostarcana.item.GREATWOOD_WAND_CORE
import com.oyosite.ticon.lostarcana.item.IRON_WAND_CAP
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.item.WOOD_PLANKS
import com.oyosite.ticon.lostarcana.item.WOOD_WAND_CORE
import com.oyosite.ticon.lostarcana.tag.*
import com.oyosite.ticon.lostarcana.unaryPlus
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class ItemTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) : FabricTagProvider<Item>(output, Registries.ITEM, registriesFuture), LostArcanaTagProvider<Item> {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        WAND_CORES(+WOOD_WAND_CORE, +GREATWOOD_WAND_CORE)
        WAND_CAPS(+IRON_WAND_CAP, +GOLD_WAND_CAP)

        GREATWOOD_LOGS(+GREATWOOD_LOG)
        WOOD_PLANKS(+GREATWOOD_PLANKS)

        ESSENTIA_JARS(+WARDED_JAR)
        VIS_CRYSTALS(+VIS_CRYSTAL)

        ItemTags.DYEABLE(+NITOR)

        COMMON_COPPER_INGOTS(Items.COPPER_INGOT)
        COMMON_REDSTONE_DUSTS(Items.REDSTONE)
        COMMON_GOLD_INGOTS(Items.GOLD_INGOT)
        COMMON_GOLD_NUGGETS(Items.GOLD_NUGGET)
        COMMON_IRON_INGOTS(Items.IRON_INGOT)
        COMMON_IRON_NUGGETS(Items.IRON_NUGGET)
        COMMON_LEATHERS(Items.LEATHER)
        COMMON_QUARTZ(Items.QUARTZ)
        COMMON_STICKS(Items.STICK)
        COMMON_GLASS_PANES(
            Items.GLASS_PANE,
            Items.LIGHT_BLUE_STAINED_GLASS_PANE,
            Items.LIGHT_GRAY_STAINED_GLASS_PANE,
            Items.ORANGE_STAINED_GLASS_PANE,
            Items.CYAN_STAINED_GLASS_PANE,
            Items.MAGENTA_STAINED_GLASS_PANE,
            Items.BLACK_STAINED_GLASS_PANE,
            Items.WHITE_STAINED_GLASS_PANE,
            Items.BLUE_STAINED_GLASS_PANE,
            Items.GRAY_STAINED_GLASS_PANE,
            Items.LIME_STAINED_GLASS_PANE,
            Items.GREEN_STAINED_GLASS_PANE,
            Items.YELLOW_STAINED_GLASS_PANE,
            Items.RED_STAINED_GLASS_PANE,
            Items.PURPLE_STAINED_GLASS_PANE,
            Items.BROWN_STAINED_GLASS_PANE,
            Items.PINK_STAINED_GLASS_PANE
        )
    }

    override fun getTagBuilder(key: TagKey<Item>) = getOrCreateTagBuilder(key)
}