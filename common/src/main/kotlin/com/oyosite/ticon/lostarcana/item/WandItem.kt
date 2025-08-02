package com.oyosite.ticon.lostarcana.item

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

open class WandItem(properties: Properties) : CastingItem(properties), VisChargeableItem {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    override fun maxVis(stack: ItemStack): Float = 100f
    override fun storedVis(stack: ItemStack): Float = stack.get(VIS_STORAGE_COMPONENT) ?: 0f
    override fun addVis(stack: ItemStack, amount: Float): Float {
        val oldAmount = storedVis(stack)
        val max = maxVis(stack)
        var newAmount = oldAmount+amount
        var overflow = 0f
        if(newAmount>max){
            overflow = newAmount-max
            newAmount = max
        }
        stack.set(VIS_STORAGE_COMPONENT, newAmount)
        return overflow
    }

    override fun availableVis(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float = stack.get(VIS_STORAGE_COMPONENT)?:0f
    override fun consumeVis(stack: ItemStack, level: Level, pos: Vec3, amount: Float, entity: Entity?): Boolean {
        val available = availableVis(stack, level, pos, entity)
        if(available < amount)return false
        stack.set(VIS_STORAGE_COMPONENT, available-amount)
        return true
    }

    override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag)
        val wandItem = itemStack.item as? WandItem ?: return
        list.add(Component.translatable(STORED_VIS_TOOLTIP, ThaumometerItem.AURA_LEVEL_FORMAT(wandItem.storedVis(itemStack))))
    }


    companion object{
        val STORED_VIS_TOOLTIP = "tooltip.item.wand.stored_vis"
    }
}