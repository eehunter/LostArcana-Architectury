package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.item.VisChargeableItem
import com.oyosite.ticon.lostarcana.util.drainAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getAuraAtLocation
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class RechargePedestalBlockEntity(blockPos: BlockPos, blockState: BlockState) : AbstractPedestalBlockEntity(RECHARGE_PEDESTAL_BLOCK_ENTITY.value(), blockPos, blockState) {
    companion object: BlockEntityTicker<RechargePedestalBlockEntity>{
        override fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: RechargePedestalBlockEntity
        ) {
            if(level.gameTime%5L != 0L)return
            val stack = blockEntity.item
            val item = stack.item
            if(!((item as? VisChargeableItem)?.canAcceptCharge(stack)?:false)) return
            val localAura = getAuraAtLocation(level, blockPos.center)
            drainAuraAtLocation(level, blockPos.center, localAura - item.addVis(stack, localAura))
        }
    }


}