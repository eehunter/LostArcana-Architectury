package com.oyosite.ticon.lostarcana.util;

import com.oyosite.ticon.lostarcana.blockentity.AbstractPedestalBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import kotlin.jvm.optionals.getOrNull

class NonGuiInventoryHelper<T: BlockEntity>(val be: T, val items: MutableList<ItemStack>, vararg val slotRestrictions: (ItemStack)->Boolean, val maxSlotAmount: Int = 64) {


    fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult,
    ): ItemInteractionResult {
        if(interactionHand != InteractionHand.MAIN_HAND) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        if(level.isClientSide)return ItemInteractionResult.SUCCESS
        //val be = level.getBlockEntity(blockPos, beType).getOrNull() ?: return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

        if(player.getItemInHand(interactionHand).isEmpty)
            for(i in items.indices)
                if (!items[i].isEmpty) {
                    val (x, y, z) = player.position()
                    level.addFreshEntity(ItemEntity(level, x, y, z, items[i]))
                    items[i] = ItemStack.EMPTY
                    be.setChanged()
                    return ItemInteractionResult.SUCCESS
                }

        for(i in items.indices) {
            if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty) {
                if (!slotRestrictions[i](player.getItemInHand(InteractionHand.MAIN_HAND))) continue
                if (!items[i].isEmpty) {
                    val (x, y, z) = player.position()
                    level.addFreshEntity(ItemEntity(level, x, y, z, items[i]))
                }
                items[i] = player.inventory.removeItem(player.inventory.selected, maxSlotAmount)
                be.setChanged()
                return ItemInteractionResult.SUCCESS
            }
        }
        return ItemInteractionResult.SUCCESS
    }
}
