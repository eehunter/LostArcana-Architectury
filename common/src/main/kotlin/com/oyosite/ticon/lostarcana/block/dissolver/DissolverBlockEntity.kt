package com.oyosite.ticon.lostarcana.block.dissolver

import com.oyosite.ticon.lostarcana.blockentity.DISSOLVER_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.item.SINGLE_FLUID_STORAGE_COMPONENT
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.tag.ESSENTIA_JARS
import com.oyosite.ticon.lostarcana.tag.VIS_CRYSTALS
import com.oyosite.ticon.lostarcana.util.ImmutableFluidStack.Companion.immutableCopy
import com.oyosite.ticon.lostarcana.util.NonGuiInventoryHelper
import dev.architectury.fluid.FluidStack
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.registries.BuiltInRegistries
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
import net.minecraft.world.level.block.state.BlockState

class DissolverBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(DISSOLVER_BLOCK_ENTITY.value(), blockPos, blockState), Container {

    val items = NonNullList.withSize(2, ItemStack.EMPTY)

    var jar: ItemStack get() = items[0]
        set(value) {items[0] = value}

    var crystal: ItemStack get() = items[1]
        set(value) {items[1] = value}

    val inventoryHelper = NonGuiInventoryHelper<DissolverBlockEntity>(this, items,
        { it.itemHolder.`is`(ESSENTIA_JARS) },
        { it.item == VIS_CRYSTAL.get() },
        maxSlotAmount = { i:Int -> if(i==0) 1 else 64 }
    )

    /*var fluidContents: FluidStack
    get() = components().get(SINGLE_FLUID_STORAGE_COMPONENT)?.copy?: FluidStack.empty()
        set(value) {
            applyComponents(components(), DataComponentPatch.builder().set(SINGLE_FLUID_STORAGE_COMPONENT, value.immutableCopy).build())
            setChanged()
        }*/

    override fun getContainerSize(): Int = 2

    override fun isEmpty(): Boolean = jar.isEmpty && crystal.isEmpty

    override fun getItem(i: Int): ItemStack = items[i]

    override fun removeItem(i: Int, j: Int): ItemStack? {
        val stack = ContainerHelper.removeItem(items, i, j)
        setChanged()
        return stack
    }

    override fun removeItemNoUpdate(i: Int): ItemStack? = ContainerHelper.takeItem(items, i)

    override fun setItem(i: Int, itemStack: ItemStack?) {
        items[i] = itemStack
        setChanged()
    }

    override fun stillValid(player: Player?): Boolean = true

    override fun clearContent() {
        items[0] = ItemStack.EMPTY
        items[1] = ItemStack.EMPTY
    }

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag =
        CompoundTag().also { saveAdditional(it, provider) }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this)

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        if(level?.isClientSide?:false) clearContent()
        ContainerHelper.loadAllItems(compoundTag, items, provider)
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        ContainerHelper.saveAllItems(compoundTag, items, provider)
    }

    override fun setChanged() {
        super.setChanged()
        val level = level?:return
        val state = level.getBlockState(blockPos)
        level.sendBlockUpdated(blockPos, state, state, Block.UPDATE_ALL_IMMEDIATE)
    }
}