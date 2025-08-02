package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class AbstractPedestalBlockEntity(type: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState) : BlockEntity(type, blockPos, blockState), Container {

    val inv: NonNullList<ItemStack> = NonNullList.withSize(1, ItemStack.EMPTY)

    var item: ItemStack get() = getItem(0)
        set(value){
            setItem(0, value)
            setChanged()
        }

    override fun getContainerSize(): Int = 1

    override fun getItem(i: Int): ItemStack = inv[i]

    override fun setItem(i: Int, itemStack: ItemStack) { inv[i] = itemStack }

    override fun clearContent() = inv.clear()

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        ContainerHelper.loadAllItems(compoundTag, inv, provider)
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        ContainerHelper.saveAllItems(compoundTag, inv, provider)
    }

    override fun removeItem(i: Int, j: Int): ItemStack =
        ContainerHelper.removeItem(inv, i, j).also { this.setChanged() }

    override fun removeItemNoUpdate(i: Int): ItemStack = ContainerHelper.takeItem(inv, i)

    override fun isEmpty(): Boolean = inv[0].isEmpty

    override fun stillValid(player: Player): Boolean = true
}