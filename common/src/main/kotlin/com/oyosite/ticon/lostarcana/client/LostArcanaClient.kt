package com.oyosite.ticon.lostarcana.client

import com.oyosite.ticon.lostarcana.aspect.aspects
import com.oyosite.ticon.lostarcana.block.InfusedStoneBlock
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor

object LostArcanaClient {
    val VIS_CRYSTAL_ITEM_COLOR = ItemColor{ stack, _ -> (stack.aspects[0].aspect.color or 0xFF000000u).toInt()  }

    val INFUSED_STONE_BLOCK_COLOR = BlockColor{ state, getter, pos, i -> ((state.block as InfusedStoneBlock).aspect.color).toInt() }
}