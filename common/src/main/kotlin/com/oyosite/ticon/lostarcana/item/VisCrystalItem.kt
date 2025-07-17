package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.AspectStacks
import com.oyosite.ticon.lostarcana.aspect.ItemAspectHolder
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

open class VisCrystalItem(properties: Properties): Item(properties), ItemAspectHolder {



    override fun getAspects(context: ItemStack): AspectStacks? {

        return super.getAspects(context)
    }
}