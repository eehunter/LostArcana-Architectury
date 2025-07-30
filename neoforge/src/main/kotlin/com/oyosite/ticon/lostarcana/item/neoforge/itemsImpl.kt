@file:JvmName("ItemsKtImpl")
package com.oyosite.ticon.lostarcana.item.neoforge

import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge
import net.minecraft.core.Holder
import net.minecraft.world.item.ArmorMaterial


fun platformRegisterArmorMaterial(name: String, materialSupplier: ()-> ArmorMaterial): Holder<ArmorMaterial> =
    LostArcanaNeoForge.NEOFORGE_ARMOR_MATERIALS.register(name, materialSupplier)

