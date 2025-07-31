package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack

class ArcaneWorkbenchMenu(id: Int, val inventory: Inventory, val container: Container): AbstractContainerMenu(ARCANE_WORKBENCH_MENU_SCREEN.value(), id) {
    constructor(id: Int, inventory: Inventory): this(id, inventory, SimpleContainer(15))

    


    override fun quickMoveStack(
        player: Player,
        i: Int
    ): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots.getOrNull(i)
        if(slot?.hasItem()?:false){
            val original = slot.item
            newStack = original.copy()
            if(i < container.containerSize){
                if (!this.moveItemStackTo(original, this.container.containerSize, this.slots.size, true)) return ItemStack.EMPTY;
            } else if(!this.moveItemStackTo(original, 0, this.container.containerSize, false)) {
                return ItemStack.EMPTY;
            }
            if(original.isEmpty)slot.set(ItemStack.EMPTY)
            else slot.setChanged()
        }
        return newStack
    }

    override fun stillValid(player: Player): Boolean = container.stillValid(player)
}