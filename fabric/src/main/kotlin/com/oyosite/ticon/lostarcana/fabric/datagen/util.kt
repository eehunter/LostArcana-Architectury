package com.oyosite.ticon.lostarcana.fabric.datagen

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.TagKey

val ShapedRecipeBuilder.arcaneWorkbench get() = (this as CraftingRecipeBuilder).arcaneWorkbench()

context(provider: LostArcanaTagProvider<T>)
inline operator fun <reified T> TagKey<T>.invoke(vararg objects: T) = provider.getTagBuilder(this)(*objects)

context(provider: LostArcanaTagProvider<T>)
inline operator fun <reified T> TagKey<T>.invoke(tagKey: TagKey<T>) = provider.getTagBuilder(this)(tagKey)

inline operator fun <reified T> FabricTagProvider<T>.FabricTagBuilder.invoke(vararg objects: T) = add(*objects)
inline operator fun <reified T> FabricTagProvider<T>.FabricTagBuilder.invoke(tagKey: TagKey<T>) = addTag(tagKey)
