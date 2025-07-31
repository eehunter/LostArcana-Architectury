package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput

interface ArcaneWorkbenchRecipeContainer: Container, RecipeInput {

    val pos: BlockPos

    val inputSlotCount: Int// = 15
    val inputStacks: MutableList<ItemStack>// = MutableList(inputSlotCount){ ItemStack.EMPTY }

    val baseCraftingContainer: CraftingContainer// = ArcaneWorkbenchBaseCraftingContainer(this)


    override fun getContainerSize(): Int = inputSlotCount

    override fun isEmpty(): Boolean = inputStacks.all(ItemStack::isEmpty)

    override fun getItem(i: Int): ItemStack = inputStacks[i]
    override fun size(): Int = inputSlotCount

    override fun removeItem(i: Int, j: Int): ItemStack {
        val stack = ContainerHelper.removeItem(inputStacks, i, j)
        if(!stack.isEmpty) updateMenu()
        return stack
    }

    override fun removeItemNoUpdate(i: Int): ItemStack = ContainerHelper.takeItem(inputStacks, i);

    override fun setItem(i: Int, stack: ItemStack) {
        inputStacks[i] = stack
        updateMenu()
    }

    override fun setChanged() {

    }

    override fun stillValid(player: Player): Boolean = true

    override fun clearContent() = (0 until containerSize).forEach { setItem(it, ItemStack.EMPTY) }

    fun updateMenu(){
        //TODO("Update menu")
    }


    class Wrapper(val container: ArcaneWorkbenchRecipeContainer, val player: Player): Container by container, RecipeInput{
        override fun getItem(i: Int): ItemStack = container.getItem(i)
        override fun size(): Int = container.size()

        val baseCraftingContainer by container::baseCraftingContainer
        val pos by container::pos
        operator fun get(slot: Int) = getItem(slot)
        override fun isEmpty(): Boolean = container.isEmpty()
    }

    class ArcaneWorkbenchBaseCraftingContainer(val recipeContainer: ArcaneWorkbenchRecipeContainer): CraftingContainer{
        override fun getWidth(): Int = 3

        override fun getHeight(): Int = 3

        override fun getItems(): List<ItemStack> = recipeContainer.inputStacks.subList(0,8)

        override fun getContainerSize(): Int = 9

        override fun isEmpty(): Boolean = items.all(ItemStack::isEmpty)

        override fun getItem(i: Int): ItemStack = items[i]

        override fun removeItem(i: Int, amount: Int): ItemStack = recipeContainer.removeItem(i, amount)

        override fun removeItemNoUpdate(i: Int): ItemStack = recipeContainer.removeItemNoUpdate(i)

        override fun setItem(i: Int, itemStack: ItemStack) = recipeContainer.setItem(i, itemStack)

        override fun setChanged() = recipeContainer.setChanged()

        override fun stillValid(player: Player): Boolean = recipeContainer.stillValid(player)

        override fun clearContent() {
            for(i in 0 until containerSize)setItem(i, ItemStack.EMPTY)
        }

        override fun fillStackedContents(stackedContents: StackedContents) {
            for (itemStack in this.items) {
                stackedContents.accountSimpleStack(itemStack)
            }
        }

    }
}