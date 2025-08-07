package com.oyosite.ticon.lostarcana.item.focus

import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.item.FOCUS
import com.oyosite.ticon.lostarcana.item.FOCUS_COMPONENT
import com.oyosite.ticon.lostarcana.item.FOCUS_EFFECT
import com.oyosite.ticon.lostarcana.util.ImmutableItemStack.Companion.immutableCopy
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

open class CastingFocusItem(properties: Properties) : Item(properties) {
    open fun getEffect(stack: ItemStack) = stack.get(FOCUS_EFFECT)?: CastingFocusEffect.NONE

    open fun getHolder(stack: ItemStack) = CastingFocusHolder(stack.immutableCopy, getEffect(stack))

    override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        val castingItemStack = player.getItemInHand(InteractionHand.entries[interactionHand.ordinal xor 1])
        val castingItem = castingItemStack.item
        if(castingItem !is CastingItem || !castingItem.supportsFoci(castingItemStack)) return super.use(level, player, interactionHand)
        val focusStack = player.getItemInHand(interactionHand)
        val focusItem = focusStack.item
        if(focusItem !is CastingFocusItem) return super.use(level, player, interactionHand)
        val ctx = CastingContext()
        ctx.castingItem = castingItemStack
        ctx.focus = focusStack
        ctx.caster = player
        if(!focusItem.getEffect(focusStack).isValidForContext(ctx)) return super.use(level, player, interactionHand)
        val oldFocus = castingItemStack.set(FOCUS_COMPONENT, focusItem.getHolder(focusStack))
        player.setItemInHand(interactionHand, oldFocus?.stack?.copy?: ItemStack.EMPTY)
        return InteractionResultHolder.success(focusStack)
    }
}