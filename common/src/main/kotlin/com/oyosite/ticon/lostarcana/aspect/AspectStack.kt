package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry

@JvmRecord
data class AspectStack(val aspect: Aspect, val amount: Int){
constructor(aspect: String, amount: Int): this(AspectRegistry.ASPECTS.registrar.get(LostArcana.id(aspect))!!, amount)
}
