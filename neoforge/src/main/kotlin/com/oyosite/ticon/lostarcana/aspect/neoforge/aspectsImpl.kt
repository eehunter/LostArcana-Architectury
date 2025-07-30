@file:JvmName("AspectsKtImpl")
package com.oyosite.ticon.lostarcana.aspect.neoforge

import com.google.common.base.Supplier
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.neoforge.LostArcanaNeoForge

fun <T: Aspect> registerAspect(name: String, aspect: T): T = aspect.apply { LostArcanaNeoForge.NEOFORGE_ASPECTS.register(name, Supplier { aspect }) }

