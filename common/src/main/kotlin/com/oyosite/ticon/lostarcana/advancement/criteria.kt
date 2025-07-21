package com.oyosite.ticon.lostarcana.advancement

import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.advancements.CriterionTrigger
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.core.Holder


val THAUMOMETER_SCAN_TRIGGER: Holder<ThaumometerScanCriterionTrigger> = "thaumometer_scan" % ::ThaumometerScanCriterionTrigger





@ExpectPlatform
fun <T : CriterionTriggerInstance, U : CriterionTrigger<T>> registerCriterionTrigger(name: String, supplier: ()->U): Holder<U> = throw AssertionError()

operator fun <T : CriterionTriggerInstance, U : CriterionTrigger<T>> String.rem(supplier: ()->U): Holder<U> = registerCriterionTrigger(this, supplier)




