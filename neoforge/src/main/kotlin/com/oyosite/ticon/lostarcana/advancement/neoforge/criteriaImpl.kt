@file:JvmName("CriteriaKtImpl")
package com.oyosite.ticon.lostarcana.advancement.neoforge

import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge.Companion.NEOFORGE_ADVANCEMENT_TRIGGERS
import net.minecraft.advancements.CriterionTrigger
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.core.Holder

@Suppress("unchecked_cast")
fun <T : CriterionTriggerInstance, U : CriterionTrigger<T>> registerCriterionTrigger(name: String, supplier: ()->U): Holder<U> =
    NEOFORGE_ADVANCEMENT_TRIGGERS.register(name, supplier) as Holder<U>