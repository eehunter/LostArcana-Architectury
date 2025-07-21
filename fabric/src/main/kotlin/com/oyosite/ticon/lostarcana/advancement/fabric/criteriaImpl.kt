@file:JvmName("CriteriaKtImpl")
package com.oyosite.ticon.lostarcana.advancement.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.advancements.CriterionTrigger
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries

@Suppress("unchecked_cast")
fun <T : CriterionTriggerInstance, U : CriterionTrigger<T>> registerCriterionTrigger(name: String, supplier: ()->U): Holder<U> =
    Registry.registerForHolder(BuiltInRegistries.TRIGGER_TYPES, LostArcana.id(name), supplier()) as Holder<U>