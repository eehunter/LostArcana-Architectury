@file:JvmName("AspectsKtImpl")
package com.oyosite.ticon.lostarcana.aspect.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.ASPECT_REGISTRY
import com.oyosite.ticon.lostarcana.aspect.Aspect
import net.minecraft.core.Registry

fun <T: Aspect> registerAspect(name: String, aspect: T): T = Registry.register(ASPECT_REGISTRY, LostArcana.id(name), aspect)
