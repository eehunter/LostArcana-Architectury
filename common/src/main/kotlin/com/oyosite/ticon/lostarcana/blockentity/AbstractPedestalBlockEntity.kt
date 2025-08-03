package com.oyosite.ticon.lostarcana.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

abstract class AbstractPedestalBlockEntity(type: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState, val slotCount: Int = 1) : BlockEntity(type, blockPos, blockState), Container {

    open val itemRendererOffset = Vec3(0.5,1.3,0.5)

    val inv: NonNullList<ItemStack> = NonNullList.withSize(slotCount, ItemStack.EMPTY)

    var item: ItemStack get() = getItem(0)
        set(value){
            setItem(0, value)
        }

    override fun getContainerSize(): Int = slotCount

    override fun getItem(i: Int): ItemStack = inv[i]

    override fun setItem(i: Int, itemStack: ItemStack) { inv[i] = itemStack; setChanged() }

    override fun clearContent() = inv.clear().also { setChanged() }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        if(level?.isClientSide?:false) clearContent()
        ContainerHelper.loadAllItems(compoundTag, inv, provider)
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        ContainerHelper.saveAllItems(compoundTag, inv, provider)
    }

    override fun removeItem(i: Int, j: Int): ItemStack =
        ContainerHelper.removeItem(inv, i, j).also { this.setChanged() }

    override fun removeItemNoUpdate(i: Int): ItemStack = ContainerHelper.takeItem(inv, i).also { this.setChanged() }

    override fun isEmpty(): Boolean = inv.all(ItemStack::isEmpty)

    override fun stillValid(player: Player): Boolean = true

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag =
        CompoundTag().also { saveAdditional(it, provider) }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this)

    override fun setChanged() {
        super.setChanged()
        val level = level?:return
        val state = level.getBlockState(blockPos)
        level.sendBlockUpdated(blockPos, state, state, Block.UPDATE_ALL_IMMEDIATE)
    }
}