package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.RECHARGE_PEDESTAL_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.RechargePedestalBlockEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class RechargePedestal(properties: Properties): PedestalBlock(properties, ::RechargePedestalBlockEntity) {


    override fun <T : BlockEntity> getTicker(
        level: Level,
        blockState: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if(blockEntityType == RECHARGE_PEDESTAL_BLOCK_ENTITY.value()) RechargePedestalBlockEntity as BlockEntityTicker<T> else null
    }
}