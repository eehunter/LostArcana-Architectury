package com.oyosite.ticon.lostarcana.fabric.datagen


import com.oyosite.ticon.lostarcana.tag.*
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class ItemTagProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) : FabricTagProvider<Item>(output, Registries.ITEM, registriesFuture), LostArcanaTagProvider<Item> {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        COMMON_GOLD_INGOTS(Items.GOLD_INGOT)
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