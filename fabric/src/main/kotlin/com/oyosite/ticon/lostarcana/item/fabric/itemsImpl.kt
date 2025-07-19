@file:JvmName("ItemsKtImpl")
package com.oyosite.ticon.lostarcana.item.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.ArmorMaterial


fun platformRegisterArmorMaterial(name: String, materialSupplier: ()-> ArmorMaterial): Holder<ArmorMaterial> =
    Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, LostArcana.id(name), materialSupplier())
