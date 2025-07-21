package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.advancement.THAUMOMETER_SCAN_TRIGGER
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.entity.AURA_NODE
import com.oyosite.ticon.lostarcana.entity.AuraNodeEntity
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.phys.AABB

open class ThaumometerItem(properties: Properties) : Item(properties) {

    @Suppress("unchecked_cast")
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val nodes: List<AuraNodeEntity> = level.getEntities(null, AABB.ofSize(context.clickLocation, THAUMOMETER_RANGE, THAUMOMETER_RANGE, THAUMOMETER_RANGE)) { it is AuraNodeEntity } as List<AuraNodeEntity>
        val node = nodes.minByOrNull { it.distanceToSqr(context.clickLocation) }
        val player = context.player as? ServerPlayer
        if(player!=null && (node?.eyePosition?.distanceToSqr(context.clickLocation) ?: 11.0) < 10){
            println("scanned node")
            THAUMOMETER_SCAN_TRIGGER.value().trigger(player, AURA_NODE.id, AURA_NODE.registryId)
        }
        if(!level.isClientSide)return super.useOn(context)
        val vis = node?.vis?:0f
        val sqdist = node?.eyePosition?.distanceToSqr(context.clickLocation)?.toFloat()?:0f
        val aura = vis - (sqdist/100f * vis)
        context.player?.sendSystemMessage(
            if(aura > 0) Component.translatable(AURA_LEVEL_TRANSLATION_KEY, AURA_LEVEL_FORMAT(aura))
            else Component.translatable(NO_AURA_TRANSLATION_KEY)
        )


        return super.useOn(context)
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

        val THAUMOMETER_RANGE = 20.0
    }
}