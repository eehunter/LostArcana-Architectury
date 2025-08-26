package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.blockentity.VisLightBlockEntity
import net.minecraft.core.component.DataComponents
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block

class NitorItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun place(blockPlaceContext: BlockPlaceContext): InteractionResult {
        if (super.place(blockPlaceContext) == InteractionResult.FAIL) return InteractionResult.FAIL
        val be = blockPlaceContext.level.getBlockEntity(blockPlaceContext.clickedPos) as? VisLightBlockEntity?:return InteractionResult.SUCCESS
        val color = (blockPlaceContext.itemInHand[DataComponents.DYED_COLOR]?.rgb?.toUInt()?: DEFAULT_COLOR) or ALPHA_MASK
        be.color = color
        return InteractionResult.SUCCESS
    }

    companion object{
        val DEFAULT_COLOR = 0xFFFFFFFFu
        val ALPHA_MASK = 0xFF000000u
    }
}