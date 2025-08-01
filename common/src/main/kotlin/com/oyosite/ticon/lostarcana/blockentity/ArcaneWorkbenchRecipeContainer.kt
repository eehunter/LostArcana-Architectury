package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.aura.AuraSource
import com.oyosite.ticon.lostarcana.item.CasterGauntlet
import com.oyosite.ticon.lostarcana.util.drainAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getNearestAuraSourceInRange
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.inventory.ResultContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.level.Level

class ArcaneWorkbenchRecipeContainer(): Container, RecipeInput {

    var be: ArcaneWorkbenchBlockEntity? = null
    val pos: BlockPos? get() = be?.pos

    val inputSlotCount: Int = 15
    val inputStacks: NonNullList<ItemStack> = NonNullList.withSize(inputSlotCount, ItemStack.EMPTY)

    val baseCraftingContainer: CraftingContainer = ArcaneWorkbenchBaseCraftingContainer(this)

    override fun getContainerSize(): Int = inputSlotCount

    override fun isEmpty(): Boolean = inputStacks.all(ItemStack::isEmpty)

    override fun getItem(i: Int): ItemStack = inputStacks[i]
    override fun size(): Int = inputSlotCount

    override fun removeItem(i: Int, j: Int): ItemStack {
        val stack = ContainerHelper.removeItem(inputStacks, i, j)
        //if(!stack.isEmpty)
        setChanged()
        return stack
    }

    override fun removeItemNoUpdate(i: Int): ItemStack = ContainerHelper.takeItem(inputStacks, i).also { if(!it.isEmpty)this.setChanged() }

    override fun setItem(i: Int, stack: ItemStack) {
        inputStacks[i] = stack
        setChanged()
    }

    override fun setChanged() {
        be?.setChanged()
    }

    override fun stillValid(player: Player): Boolean = true

    override fun clearContent() {
        (0 until containerSize).forEach { setItem(it, ItemStack.EMPTY) }
        setChanged()
    }

    fun updateMenu(){
        //TODO("Update menu")
        setChanged()
    }

    fun wrap(player: Player) = Wrapper(this, player)


    class Wrapper(val container: ArcaneWorkbenchRecipeContainer, val player: Player): Container by container, RecipeInput{
        override fun getItem(i: Int): ItemStack = container.getItem(i)
        override fun size(): Int = container.size()
        val result = ResultContainer()
        var markDirtyCallback: ()->Unit = {}
        override fun setChanged() {
            container.setChanged()
            markDirtyCallback()
        }

        fun getAura(level: Level): Float { return getAuraAtLocation(level, pos?.center ?: return 0f) }
        fun drainAura(level: Level, amount: Float) { drainAuraAtLocation(level, pos?.center?: return, amount) }

        val baseCraftingContainer by container::baseCraftingContainer
        val pos by container::pos
        operator fun get(slot: Int) = getItem(slot)
        override fun isEmpty(): Boolean = container.isEmpty()
    }

    class ArcaneWorkbenchBaseCraftingContainer(val recipeContainer: ArcaneWorkbenchRecipeContainer): CraftingContainer{
        override fun getWidth(): Int = 3

        override fun getHeight(): Int = 3

        override fun getItems(): List<ItemStack> = NonNullList.of(ItemStack.EMPTY, *recipeContainer.inputStacks.subList(0,9).toTypedArray())//.subList(0,9)

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