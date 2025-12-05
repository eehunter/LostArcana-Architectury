package com.oyosite.ticon.lostarcana.item

import dev.architectury.core.item.ArchitecturyBucketItem
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.material.Fluid

class EssentiaBucketItem(fluid: ()-> Fluid, properties: Properties) : ArchitecturyBucketItem(fluid, properties) {

    /*override fun finishUsingItem(itemStack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack? {
        return super.finishUsingItem(itemStack, level, livingEntity)
    }

    override fun emptyContents(
        player: Player?,
        level: Level,
        blockPos: BlockPos,
        blockHitResult: BlockHitResult?
    ): Boolean {
        if(!super.emptyContents(player, level, blockPos, blockHitResult))return false
        //level.getBlockEntity(blockPos)?.applyComponentsFromItemStack(blockHitResult?)
        return true
    }*/

    override fun appendHoverText(
        itemStack: ItemStack,
        tooltipContext: TooltipContext,
        list: MutableList<Component?>,
        tooltipFlag: TooltipFlag
    ) {
        val aspect = itemStack.components.get(RAW_ASPECT_COMPONENT)?:return
        list += Component.translatable(aspect.translationKey).setStyle(Style.EMPTY.withColor(aspect.color.toInt()))
    }

}