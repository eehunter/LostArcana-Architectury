package com.oyosite.ticon.lostarcana.recipe

import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.level.Level

class SalisMundisRecipeInput(val level: Level, vararg val blocks: BlockPos): RecipeInput {
    val items = blocks.map(level::getBlockState).map{ it.block.asItem() }

    val block = blocks[0]

    override fun getItem(i: Int): ItemStack = ItemStack(items[i])

    override fun size(): Int = items.size
}