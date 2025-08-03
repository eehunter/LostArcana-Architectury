package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.RECHARGE_PEDESTAL_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.RechargePedestalBlockEntity
import com.oyosite.ticon.lostarcana.item.Resonator
import com.oyosite.ticon.lostarcana.item.VisChargeableItem
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.stream.Stream

class RechargePedestal(properties: Properties, val slotCount: Int = 1): PedestalBlock(properties, { pos, state -> RechargePedestalBlockEntity(pos, state, slotCount) }) {

    override fun isItemAllowed(stack: ItemStack): Boolean = stack.item is VisChargeableItem

    override fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult
    ): ItemInteractionResult {
        if(slotCount==1)
            return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult)
        if(interactionHand != InteractionHand.MAIN_HAND) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        if(level.isClientSide)return ItemInteractionResult.SUCCESS
        val be = level.getBlockEntity(blockPos) as? RechargePedestalBlockEntity ?: return ItemInteractionResult.SUCCESS
        val heldStack = player.getItemInHand(interactionHand)
        val heldItem = heldStack.item
        if(heldStack.isEmpty){
            if(!be.item.isEmpty) {
                val (x, y, z) = player.position()
                level.addFreshEntity(ItemEntity(level, x, y, z, be.item))
                be.item = ItemStack.EMPTY
            } else if(!be.resonator.isEmpty){
                val (x, y, z) = player.position()
                level.addFreshEntity(ItemEntity(level, x, y, z, be.resonator))
                be.resonator = ItemStack.EMPTY
            }
        } else {
            if(heldItem is Resonator){
                if(!be.resonator.isEmpty){
                    if(be.item.isEmpty && heldItem is VisChargeableItem){
                        be.item = player.inventory.removeItem(player.inventory.selected, 1)
                        return ItemInteractionResult.SUCCESS
                    }
                    val (x,y,z) = player.position()
                    level.addFreshEntity(ItemEntity(level, x, y, z, be.resonator))
                }
                be.resonator = player.inventory.removeItem(player.inventory.selected, 1)
            } else if (heldItem is VisChargeableItem){
                if(!be.item.isEmpty){
                    val (x,y,z) = player.position()
                    level.addFreshEntity(ItemEntity(level, x, y, z, be.item))
                }
                be.item = player.inventory.removeItem(player.inventory.selected, 1)
            }
        }
        return ItemInteractionResult.SUCCESS
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if(blockEntityType == RECHARGE_PEDESTAL_BLOCK_ENTITY.value()) RechargePedestalBlockEntity as BlockEntityTicker<T> else null
    }

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = VISUAL_SHAPE

    override fun getCollisionShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = VOXEL_SHAPE

    override fun getVisualShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = VISUAL_SHAPE

    override fun getInteractionShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos
    ): VoxelShape = VISUAL_SHAPE

    companion object{
        val VOXEL_SHAPE = Stream.of(
            box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            box(3.0, 4.0, 3.0, 13.0, 8.0, 13.0),
            box(5.0, 8.0, 5.0, 11.0, 16.0, 11.0)
        ).reduce({ v1, v2 -> Shapes.join(v1, v2, BooleanOp.OR) }).get()

        val VISUAL_SHAPE = Stream.of(
            box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            box(3.0, 4.0, 3.0, 13.0, 8.0, 13.0),
            box(5.0, 8.0, 5.0, 11.0, 16.0, 11.0),
            Stream.of(
                box(7.0, 15.0, 4.0, 9.0, 18.0, 5.0),
                box(4.0, 15.0, 7.0, 5.0, 18.0, 9.0),
                box(11.0, 15.0, 7.0, 12.0, 18.0, 9.0),
                box(7.0, 15.0, 11.0, 9.0, 18.0, 12.0)
            ).reduce { v1, v2 -> Shapes.join(v1, v2, BooleanOp.OR) }.get(),
        Stream.of(
        box(7.0, 11.0, 4.0, 9.0, 13.0, 5.0),
        box(4.0, 11.0, 7.0, 5.0, 13.0, 9.0),
        box(11.0, 11.0, 7.0, 12.0, 13.0, 9.0),
        box(7.0, 11.0, 11.0, 9.0, 13.0, 12.0)
        ).reduce { v1, v2 -> Shapes.join(v1, v2, BooleanOp.OR) }.get()
        ).reduce { v1, v2 -> Shapes.join(v1, v2, BooleanOp.OR) }.get();
    }
}