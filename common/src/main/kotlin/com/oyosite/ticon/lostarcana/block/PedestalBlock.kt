package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.AbstractPedestalBlockEntity
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

open class PedestalBlock(properties: Properties, val blockEntityFactory: (BlockPos, BlockState) -> BlockEntity?) : Block(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity? = blockEntityFactory(blockPos, blockState)

    open fun isItemAllowed(stack: ItemStack) = true

    override fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult
    ): ItemInteractionResult {
        if(interactionHand != InteractionHand.MAIN_HAND) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        if(level.isClientSide)return ItemInteractionResult.SUCCESS
        val be = level.getBlockEntity(blockPos) as? AbstractPedestalBlockEntity ?: return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        if(!be.item.isEmpty && player.getItemInHand(interactionHand).isEmpty){
            val (x,y,z) = player.position()
            level.addFreshEntity(ItemEntity(level, x, y, z, be.item))
            be.item = ItemStack.EMPTY
            be.setChanged()
        } else if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty) {
            if(!isItemAllowed(player.getItemInHand(InteractionHand.MAIN_HAND)))return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            if(!be.item.isEmpty){
                val (x,y,z) = player.position()
                level.addFreshEntity(ItemEntity(level, x, y, z, be.item))
            }
            be.item = player.inventory.removeItem(player.inventory.selected, 1)
            be.setChanged()
        }
        return ItemInteractionResult.SUCCESS
    }

    override fun onRemove(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        Containers.dropContentsOnDestroy(blockState, blockState2, level, blockPos)
        super.onRemove(blockState, level, blockPos, blockState2, bl)
    }
}