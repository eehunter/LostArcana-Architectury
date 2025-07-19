package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.aspect.aspects
import net.minecraft.core.Holder
import net.minecraft.tags.ItemTags
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.phys.AABB

abstract class CastingItem(properties: Properties) : Item(properties) {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    abstract fun availableVis(stack: ItemStack, entity: Entity?): Float
    abstract fun consumeVis(stack: ItemStack, amount: Float, entity: Entity?): Boolean

    open val craftItemRange: Double get() = .7

    @Suppress("unchecked_cast")
    override fun useOn(useOnContext: UseOnContext): InteractionResult {
        val player = useOnContext.player
        val level = useOnContext.level
        val items = level.getEntities(player, AABB.ofSize(useOnContext.clickLocation, craftItemRange, craftItemRange, craftItemRange)){ it is ItemEntity }.mapNotNull{it as? ItemEntity}
        val crystals = items.filter { it.item.item is VisCrystalItem }
        if(crystals.size == 3 && consumeVis(useOnContext.itemInHand, 10f, player)){
            val pos = crystals[0].position()
            level.addFreshEntity(ItemEntity(level, pos.x, pos.y, pos.z, ItemStack(SALIS_MUNDIS)))
            for(i in 0..2) crystals[i].item.consume(1, player)
        } else if (crystals.size == 6 && PRIMAL_ASPECTS.all { aspect -> crystals.filter { c -> c.item.aspects.let{ it[0].aspect == aspect } }.size == 1 }){
            val gold = items.firstOrNull { it.item.`is`(COMMON_GOLD_INGOTS) }
            val pane = items.firstOrNull { it.item.`is`(COMMON_GLASS_PANES) }
            if(gold!=null && pane!=null && consumeVis(useOnContext.itemInHand, 20f, player)){
                val pos = crystals[0].position()
                listOf(gold, pane, *crystals.toTypedArray()).forEach { it.item.consume(1, player) }
                level.addFreshEntity(ItemEntity(level, pos.x, pos.y, pos.z, ItemStack(THAUMOMETER as Holder<Item>)))
            }
        }

        return super.useOn(useOnContext)
    }


}