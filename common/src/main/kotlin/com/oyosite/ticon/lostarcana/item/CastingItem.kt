package com.oyosite.ticon.lostarcana.item

import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.phys.AABB

abstract class CastingItem(properties: Properties) : Item(properties) {
    constructor(properties: Properties, defaultVisAmount: Float): this(properties.component(VIS_STORAGE_COMPONENT, defaultVisAmount))

    abstract fun availableVis(stack: ItemStack, entity: Entity?): Float

    override fun useOn(useOnContext: UseOnContext): InteractionResult {
        val player = useOnContext.player
        val level = useOnContext.level
        val crystals = level.getEntities(player, AABB.ofSize(useOnContext.clickLocation, .5, .5, .5)){ (it as? ItemEntity)?.item?.item is VisCrystalItem }.mapNotNull{it as? ItemEntity}
        if(crystals.size >= 3){
            val pos = crystals[0].position()
            level.addFreshEntity(ItemEntity(level, pos.x, pos.y, pos.z, ItemStack(SALIS_MUNDIS)))
            for(i in 0..2) crystals[i].item.consume(1, player)
        }

        return super.useOn(useOnContext)
    }
}