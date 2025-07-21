package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import net.minecraft.core.Holder
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.component.ItemAttributeModifiers

class GogglesOfRevealingItem(properties: Properties, material: Holder<ArmorMaterial>) : ArmorItem(material, Type.HELMET, properties) {

    override fun getDefaultAttributeModifiers(): ItemAttributeModifiers {
        return super.getDefaultAttributeModifiers().withModifierAdded(ARCANE_SIGHT, GOGGLES_SIGHT_MODIFIER, EquipmentSlotGroup.HEAD)
    }

    companion object{
        val GOGGLES_SIGHT_MODIFIER = AttributeModifier(LostArcana.id("goggles_of_revealing.arcane_sight"), 1.0, AttributeModifier.Operation.ADD_VALUE)
    }

}