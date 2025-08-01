package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Predicate

class ArcaneWorkbenchBlockEntity(val pos: BlockPos, state: BlockState, val container: ArcaneWorkbenchRecipeContainer = ArcaneWorkbenchRecipeContainer()): BlockEntity(ARCANE_WORKBENCH_BLOCK_ENTITY.value(), pos, state), Container by container, MenuProvider/*, Container, RecipeInput*/ {
    init {
        container.be = this
    }

    //override val inputSlotCount: Int = 15
    //override val inputStacks: MutableList<ItemStack> = MutableList(inputSlotCount){ ItemStack.EMPTY }
    //override val baseCraftingContainer: CraftingContainer = ArcaneWorkbenchRecipeContainer.ArcaneWorkbenchBaseCraftingContainer(this)
    override fun getMaxStackSize(): Int {
        return container.getMaxStackSize()
    }

    override fun getMaxStackSize(itemStack: ItemStack): Int {
        return container.getMaxStackSize(itemStack)
    }

    override fun setChanged() {
        super<BlockEntity>.setChanged()
    }

    override fun startOpen(player: Player) {
        container.startOpen(player)
    }

    override fun stopOpen(player: Player) {
        container.stopOpen(player)
    }

    override fun canPlaceItem(i: Int, itemStack: ItemStack): Boolean {
        return container.canPlaceItem(i, itemStack)
    }

    override fun canTakeItem(
        container: Container,
        i: Int,
        itemStack: ItemStack
    ): Boolean {
        return container.canTakeItem(container, i, itemStack)
    }

    override fun countItem(item: Item): Int {
        return container.countItem(item)
    }

    override fun hasAnyOf(set: Set<Item?>): Boolean {
        return container.hasAnyOf(set)
    }

    override fun hasAnyMatching(predicate: Predicate<ItemStack?>): Boolean {
        return container.hasAnyMatching(predicate)
    }


    override fun getDisplayName(): Component = blockState.block.name

    override fun createMenu(
        i: Int,
        inventory: Inventory,
        player: Player
    ): AbstractContainerMenu = ArcaneWorkbenchMenu(i, inventory, ArcaneWorkbenchRecipeContainer.Wrapper(this.container, player),
        level?.let{ ContainerLevelAccess.create(it, pos) } ?: ContainerLevelAccess.NULL)

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        val list = NonNullList.withSize(container.inputSlotCount, ItemStack.EMPTY)
        ContainerHelper.loadAllItems(compoundTag, list, provider)
        list.forEachIndexed(container.inputStacks::set)
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        val list = NonNullList.of(ItemStack.EMPTY, *container.inputStacks.toTypedArray())
        ContainerHelper.saveAllItems(compoundTag, list, provider)
    }

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