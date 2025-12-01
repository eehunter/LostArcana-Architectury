package com.oyosite.ticon.lostarcana.item.aura

import com.oyosite.ticon.lostarcana.util.releaseFluxAtLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class FluxerItem(properties: Properties) : Item(properties) {
    override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val amt = if(player.isCrouching) 100f else 1f
        if(releaseFluxAtLocation(level, player.position(), amt)){
            if(!player.isCreative) player.getItemInHand(interactionHand).shrink(1)
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide)
        }
        return super.use(level, player, interactionHand)
    }
}