package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.advancement.THAUMOMETER_SCAN_TRIGGER
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.entity.AURA_NODE
import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import com.oyosite.ticon.lostarcana.util.getNearestAuraSourceInRange
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.phys.AABB
import kotlin.jvm.optionals.getOrNull

open class ThaumometerItem(properties: Properties) : Item(properties) {

    override fun interactLivingEntity(
        itemStack: ItemStack,
        player: Player,
        livingEntity: LivingEntity,
        interactionHand: InteractionHand
    ): InteractionResult? {
        if(player as? ServerPlayer != null) {
            val entityRegistry = BuiltInRegistries.ENTITY_TYPE
            THAUMOMETER_SCAN_TRIGGER.value().trigger(player, entityRegistry.getKey(livingEntity.type), entityRegistry.key().location())
        }
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand)
    }

    @Suppress("unchecked_cast")
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val pos = context.clickLocation
        val node = getNearestAuraSourceInRange(level, pos, THAUMOMETER_RANGE)
        val sqdist = node?.pos?.distanceToSqr(pos)
        val player = context.player as? ServerPlayer
        if(player!=null){
            if((sqdist ?: (THAUMOMETER_SCAN_RANGE+1)) < THAUMOMETER_SCAN_RANGE)THAUMOMETER_SCAN_TRIGGER.value().trigger(player, AURA_NODE.id, AURA_NODE.registryId)
            val state = level.getBlockState(context.clickedPos)
            THAUMOMETER_SCAN_TRIGGER.value().trigger(player, BuiltInRegistries.BLOCK.getKey(state.block), Registries.BLOCK.location())
        }
        if(!level.isClientSide)return super.useOn(context)
        val vis = node?.vis?:0f
        val sqdistf = sqdist?.toFloat()?:0f
        val aura = vis - (sqdistf/100f * vis)
        context.player?.sendSystemMessage(
            if(aura > 0) Component.translatable(AURA_LEVEL_TRANSLATION_KEY, AURA_LEVEL_FORMAT(aura))
            else Component.translatable(NO_AURA_TRANSLATION_KEY)
        )
        return InteractionResult.SUCCESS_NO_ITEM_USED
    }

    val attributeModifiers: ItemAttributeModifiers = ItemAttributeModifiers.builder()
        .add(ARCANE_SIGHT, AttributeModifier(LostArcana.id("thaumometer.arcane_sight.main_hand"), .5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .add(ARCANE_INSIGHT, AttributeModifier(LostArcana.id("thaumometer.arcane_insight.main_hand"), 1.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .add(ARCANE_SIGHT, AttributeModifier(LostArcana.id("thaumometer.arcane_sight.off_hand"), .5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND)
        .add(ARCANE_INSIGHT, AttributeModifier(LostArcana.id("thaumometer.arcane_insight.off_hand"), 1.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND).build()

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getDefaultAttributeModifiers(): ItemAttributeModifiers {
        return attributeModifiers
    }

    companion object{
        val AURA_LEVEL_TRANSLATION_KEY = "info.lostarcana.thaumometer.aura_level"
        val NO_AURA_TRANSLATION_KEY = "info.lostarcana.thaumometer.no_aura"
        val AURA_LEVEL_FORMAT = { f: Float -> String.format("%.2f", f) }

        val THAUMOMETER_RANGE = 10.0
        val THAUMOMETER_SCAN_RANGE = 1.0
    }
}