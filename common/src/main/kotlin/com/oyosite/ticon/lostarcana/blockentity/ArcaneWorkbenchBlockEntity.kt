package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class ArcaneWorkbenchBlockEntity(override val pos: BlockPos, state: BlockState): BlockEntity(ARCANE_WORKBENCH_BLOCK_ENTITY.value(), pos, state), ArcaneWorkbenchRecipeContainer, MenuProvider/*, Container, RecipeInput*/ {

    override val inputSlotCount: Int = 15
    override val inputStacks: MutableList<ItemStack> = MutableList(inputSlotCount){ ItemStack.EMPTY }
    override val baseCraftingContainer: CraftingContainer = ArcaneWorkbenchRecipeContainer.ArcaneWorkbenchBaseCraftingContainer(this)
    override fun setChanged() = updateMenu()


    override fun getDisplayName(): Component = blockState.block.name

    override fun createMenu(
        i: Int,
        inventory: Inventory,
        player: Player
    ): AbstractContainerMenu = ArcaneWorkbenchMenu(i, inventory, ArcaneWorkbenchRecipeContainer.Wrapper(this, player))


    //val container = ArcaneWorkbenchRecipeContainer(pos)



    /*
    val inputSlotCount = 15
    val inputStacks = MutableList(inputSlotCount){ ItemStack.EMPTY }

    override fun getContainerSize(): Int = inputSlotCount

    override fun isEmpty(): Boolean = inputStacks.any { !it.isEmpty }

    override fun getItem(i: Int): ItemStack = inputStacks[i]

    override fun removeItem(i: Int, amount: Int): ItemStack = inputStacks[i].split(amount)
        /*val stack = inputStacks[i]
        if(stack.isEmpty)return stack
        if(stack.count<=amount){
            inputStacks[i] = ItemStack.EMPTY
            return stack
        }
        inputStacks[i] = stack.copyWithCount(stack.count-amount)
        return stack.split()*/


    override fun removeItemNoUpdate(i: Int): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun setItem(i: Int, itemStack: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearContent() {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }

*/
}