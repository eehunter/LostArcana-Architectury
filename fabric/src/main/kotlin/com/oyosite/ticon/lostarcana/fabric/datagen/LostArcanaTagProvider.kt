package com.oyosite.ticon.lostarcana.fabric.datagen

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.tags.TagKey

interface LostArcanaTagProvider<T> {
    fun getTagBuilder(key: TagKey<T>): FabricTagProvider<T>.FabricTagBuilder
}