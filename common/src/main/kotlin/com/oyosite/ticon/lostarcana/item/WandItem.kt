package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.core.component.DataComponentType
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

open class WandItem(properties: Properties) : VisStoringCastingItem(properties.component(WAND_CAP, (+IRON_WAND_CAP).castingItemComponent).component(WAND_CAP_2, (+IRON_WAND_CAP).castingItemComponent).component(WAND_CORE, (+WOOD_WAND_CORE).castingItemComponent)), VisChargeableItem {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

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

    override fun getPartComponents(stack: ItemStack): List<DataComponentType<CastingItemComponent>> = listOf(WAND_CAP, WAND_CORE, WAND_CAP_2)


    override fun availableVis(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float = (stack.get(VIS_STORAGE_COMPONENT)?:0f) * visEfficiency(stack)
    override fun consumeVis(stack: ItemStack, level: Level, pos: Vec3, amount: Float, entity: Entity?): Boolean {
        val available = availableVis(stack, level, pos, entity)
        val efficiency = visEfficiency(stack)
        if(available < amount/efficiency)return false
        stack.set(VIS_STORAGE_COMPONENT, available-amount/efficiency)
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

        fun getTintColor(stack: ItemStack, index: Int) = (
                if(index==3)stack.get(FOCUS_COMPONENT)?.effect?.color?:0u
                else(((stack.get(when(index){0 -> WAND_CORE; 1 -> WAND_CAP; else -> WAND_CAP_2}))?.color?.toUInt()?:0xFFFFFFu) or 0xFF000000u)
            ).toInt()
    }
}