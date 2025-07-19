@file:JvmName("AspectsKtImpl")
package com.oyosite.ticon.lostarcana.aspect.fabric

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import net.minecraft.core.Registry

fun <T: Aspect> registerAspect(name: String, aspect: T): T = Registry.register(AspectRegistry.ASPECT_REGISTRY, LostArcana.id(name), aspect)
