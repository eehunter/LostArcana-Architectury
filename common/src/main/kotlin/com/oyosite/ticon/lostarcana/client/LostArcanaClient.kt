package com.oyosite.ticon.lostarcana.client

import com.oyosite.ticon.lostarcana.aspect.aspects
import net.minecraft.client.color.item.ItemColor

object LostArcanaClient {
    val VIS_CRYSTAL_ITEM_COLOR = ItemColor{ stack, _ -> stack.aspects[0].aspect.color.toInt() }

}