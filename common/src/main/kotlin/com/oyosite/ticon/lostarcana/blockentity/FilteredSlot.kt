package com.oyosite.ticon.lostarcana.block.entity


import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

open class FilteredSlot(inventory: Container, index: Int, x: Int, y: Int, val predicate: (ItemStack)->Boolean, val markDirtyCallback: ()->Unit = {}): Slot(inventory, index, x, y) {


    override fun mayPlace(stack: ItemStack): Boolean = predicate(stack)


    class Delegated(inventory: Container, index: Int, x: Int, y: Int, markDirtyCallback: ()->Unit = {}): FilteredSlot(inventory, index, x, y, {inventory.canPlaceItem(index, it)}, markDirtyCallback)


}