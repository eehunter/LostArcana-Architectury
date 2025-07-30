package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.AspectStacks
import com.oyosite.ticon.lostarcana.aspect.AER
import com.oyosite.ticon.lostarcana.aspect.ItemAspectHolder
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

open class VisCrystalItem(properties: Properties): Item(properties.component(ASPECT_COMPONENT, +AER)), ItemAspectHolder {

    override fun getAspects(context: ItemStack): AspectStacks? {
        return listOf(context.components.get(ASPECT_COMPONENT)?:return null)
    }

    override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        list: MutableList<Component?>,
        tooltipFlag: TooltipFlag
    ) {
        val aspects = getAspects(itemStack)?:return
        for(aspect in aspects){
            list += Component.translatable(aspect.aspect.translationKey).setStyle(Style.EMPTY.withColor(aspect.aspect.color.toInt()))
        }
    }
}