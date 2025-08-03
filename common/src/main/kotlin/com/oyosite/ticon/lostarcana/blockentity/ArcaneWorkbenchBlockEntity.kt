package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Predicate

class ArcaneWorkbenchBlockEntity(val pos: BlockPos, state: BlockState, val container: ArcaneWorkbenchRecipeContainer = ArcaneWorkbenchRecipeContainer()): BlockEntity(ARCANE_WORKBENCH_BLOCK_ENTITY.value(), pos, state), Container by container, MenuProvider/*, Container, RecipeInput*/ {
    init {
        container.be = this
    }

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
        ContainerHelper.loadAllItems(compoundTag, container.inputStacks, provider)
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        ContainerHelper.saveAllItems(compoundTag, container.inputStacks, provider)
    }

}