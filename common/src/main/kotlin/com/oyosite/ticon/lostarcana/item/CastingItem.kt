package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.aspect.aspects
import com.oyosite.ticon.lostarcana.block.ARCANE_COLUMN
import com.oyosite.ticon.lostarcana.block.ARCANE_STONE_PILLAR
import com.oyosite.ticon.lostarcana.tag.*
import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponentType
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

abstract class CastingItem(properties: Properties) : Item(properties) {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    abstract fun getPartComponents(stack: ItemStack): List<DataComponentType<CastingItemComponent>>

    abstract fun availableVis(stack: ItemStack, level: Level, pos: Vec3, entity: Entity?): Float
    abstract fun consumeVis(stack: ItemStack, level: Level, pos: Vec3, amount: Float, entity: Entity?): Boolean

    open fun visEfficiency(stack: ItemStack): Float =
        getPartComponents(stack).mapNotNull(stack::get).map(CastingItemComponent::efficiency).fold(1f){a,b->a*b}.coerceIn(Float.MIN_VALUE, Float.POSITIVE_INFINITY)

    open val craftItemRange: Double get() = .7

    @Suppress("unchecked_cast")
    override fun useOn(useOnContext: UseOnContext): InteractionResult {
        val player = useOnContext.player
        val level = useOnContext.level

        if(level.getBlockState(useOnContext.clickedPos) == (+ARCANE_STONE_PILLAR).defaultBlockState()){
            if((+ARCANE_COLUMN).construct(useOnContext)) return InteractionResult.SUCCESS_NO_ITEM_USED
        }

        val items = level.getEntities(player, AABB.ofSize(useOnContext.clickLocation, craftItemRange, craftItemRange, craftItemRange)){ it is ItemEntity }.mapNotNull{it as? ItemEntity}
        val crystals = items.filter { it.item.item is VisCrystalItem }
        if(crystals.size == 3 && consumeVis(useOnContext.itemInHand, level, useOnContext.clickLocation, 10f, player)){
            val pos = crystals[0].position()
            level.addFreshEntity(ItemEntity(level, pos.x, pos.y, pos.z, ItemStack(+SALIS_MUNDIS)))
            for(i in 0..2) crystals[i].item.consume(1, player)
        } else if (crystals.size == 6 && PRIMAL_ASPECTS.all { aspect -> crystals.filter { c -> c.item.aspects.let{ it[0].aspect == aspect } }.size == 1 }){
            val gold = items.firstOrNull { it.item.`is`(COMMON_GOLD_INGOTS) }
            val pane = items.firstOrNull { it.item.`is`(COMMON_GLASS_PANES) }
            if(gold!=null && pane!=null && consumeVis(useOnContext.itemInHand, level, useOnContext.clickLocation, 20f, player)){
                val pos = crystals[0].position()
                listOf(gold, pane, *crystals.toTypedArray()).forEach { it.item.consume(1, player) }
                level.addFreshEntity(ItemEntity(level, pos.x, pos.y, pos.z, ItemStack(THAUMOMETER as Holder<Item>)))
            }
        }

        return super.useOn(useOnContext)
    }

    override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag)
        list.add(Component.translatable(VIS_EFFICIENCY_TOOLTIP, VIS_EFFICIENCY_FORMAT(visEfficiency(itemStack))))
    }


    companion object{
        val VIS_EFFICIENCY_TOOLTIP = "tooltip.item.casting_item.vis_efficiency"

        val VIS_EFFICIENCY_FORMAT = { f: Float -> String.format("%.1f", f * 100f) }
    }
}