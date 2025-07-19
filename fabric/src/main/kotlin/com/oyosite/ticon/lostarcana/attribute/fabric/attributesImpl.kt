@file:JvmName("AttributesKtImpl")
package com.oyosite.ticon.lostarcana.attribute.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.ai.attributes.Attribute


@Suppress("unchecked_cast")
fun <T: Attribute> registerAttribute(name: String, supplier: ()->T): Holder<T> =
    Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, LostArcana.id(name), supplier()) as Holder<T>