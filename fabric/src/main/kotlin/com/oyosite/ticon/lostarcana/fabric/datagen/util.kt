package com.oyosite.ticon.lostarcana.fabric.datagen

import com.oyosite.ticon.lostarcana.fabric.datagen.recipe.builder.CraftingRecipeBuilder
import com.oyosite.ticon.lostarcana.fabric.datagen.recipe.builder.UniqueVisCrystalRecipeBuilder
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike

val ShapelessRecipeBuilder.uniqueVisCrystal get() = UniqueVisCrystalRecipeBuilder(this)

val ShapedRecipeBuilder.arcaneWorkbench get() = (this as CraftingRecipeBuilder).arcaneWorkbench()

context(provider: LostArcanaTagProvider<Item>)
operator fun TagKey<Item>.invoke(vararg objects: ItemLike): FabricTagProvider<Item>.FabricTagBuilder = provider.getTagBuilder(this)(*objects.map(ItemLike::asItem).toTypedArray())

context(provider: LostArcanaTagProvider<T>)
inline operator fun <reified T> TagKey<T>.invoke(vararg objects: T): FabricTagProvider<T>.FabricTagBuilder = provider.getTagBuilder(this)(*objects)

context(provider: LostArcanaTagProvider<T>)
inline operator fun <reified T> TagKey<T>.invoke(tagKey: TagKey<T>): FabricTagProvider<T>.FabricTagBuilder = provider.getTagBuilder(this)(tagKey)

operator fun FabricTagProvider<Item>.FabricTagBuilder.invoke(vararg objects: ItemLike): FabricTagProvider<Item>.FabricTagBuilder = add(*objects.map(ItemLike::asItem).toTypedArray())
inline operator fun <reified T> FabricTagProvider<T>.FabricTagBuilder.invoke(vararg objects: T): FabricTagProvider<T>.FabricTagBuilder = add(*objects)
inline operator fun <reified T> FabricTagProvider<T>.FabricTagBuilder.invoke(tagKey: TagKey<T>): FabricTagProvider<T>.FabricTagBuilder = addTag(tagKey)
