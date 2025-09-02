package com.oyosite.ticon.lostarcana.block.fluid

import com.oyosite.ticon.lostarcana.blockentity.EssentiaLiquidBlockEntity
import com.oyosite.ticon.lostarcana.item.RAW_ASPECT_COMPONENT
import dev.architectury.core.block.ArchitecturyLiquidBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FlowingFluid

class EssentiaLiquidBlock(fluid: ()->FlowingFluid, properties: Properties) : ArchitecturyLiquidBlock(fluid, properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = EssentiaLiquidBlockEntity(blockPos, blockState)

    override fun setPlacedBy(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        livingEntity: LivingEntity?,
        itemStack: ItemStack
    ) {
        val be = level.getBlockEntity(blockPos)
        be?.applyComponentsFromItemStack(itemStack)?:println("BlockEntity not added yet.")
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack)
    }

    override fun pickupBlock(
        player: Player?,
        levelAccessor: LevelAccessor,
        blockPos: BlockPos,
        blockState: BlockState
    ): ItemStack? {
        return super.pickupBlock(player, levelAccessor, blockPos, blockState).also {
            val be = levelAccessor.getBlockEntity(blockPos)?: return@also
            it.set(RAW_ASPECT_COMPONENT, be.components().get(RAW_ASPECT_COMPONENT))
        }
    }
}