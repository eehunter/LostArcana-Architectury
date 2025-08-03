package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.item.Resonator
import com.oyosite.ticon.lostarcana.item.VisChargeableItem
import com.oyosite.ticon.lostarcana.util.drainAuraAtLocation
import com.oyosite.ticon.lostarcana.util.getAuraAtLocation
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.state.BlockState

open class RechargePedestalBlockEntity(blockPos: BlockPos, blockState: BlockState, slotCount: Int = 1) : AbstractPedestalBlockEntity(RECHARGE_PEDESTAL_BLOCK_ENTITY.value(), blockPos, blockState, slotCount) {

    var resonator: ItemStack get() = if(slotCount == 1) ItemStack.EMPTY else getItem(1)
        set(value){
            if (slotCount == 1) throw IllegalCallerException("The resonator of a non-modular pedestal cannot be changed.")
            setItem(1, value)
        }

    fun getAvailableAura(level: Level, pos: BlockPos): Float{
        if(slotCount == 1) return getAuraAtLocation(level, blockPos.center)
        val r = resonator
        if(r.isEmpty) return 0f
        return (r.item as? Resonator)?.getAvailableAura(r, level, pos.center)?:0f
    }

    fun drainAura(level: Level, pos: BlockPos, amount: Float){
        if(slotCount == 1) return drainAuraAtLocation(level, pos.center, amount)
        val r = resonator
        if(r.isEmpty) return
        (r.item as? Resonator)?.drainAura(r, level, pos.center, amount)
    }

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
            val localAura = blockEntity.getAvailableAura(level, blockPos)
            blockEntity.drainAura(level, blockPos, localAura - item.addVis(stack, localAura))
        }
    }


}