@file:JvmName("AttributesKtImpl")
package com.oyosite.ticon.lostarcana.attribute.neoforge

import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge
import net.minecraft.core.Holder
import net.minecraft.world.entity.ai.attributes.Attribute


@Suppress("unchecked_cast")
fun <T: Attribute> registerAttribute(name: String, supplier: ()->T): Holder<T> =
    LostArcanaNeoForge.NEOFORGE_ATTRIBUTES.register(name, supplier) as Holder<T>

