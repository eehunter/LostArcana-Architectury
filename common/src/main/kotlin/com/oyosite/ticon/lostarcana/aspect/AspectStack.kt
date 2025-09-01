package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.LostArcana

@JvmRecord
data class AspectStack(val aspect: Aspect, val amount: Int){
constructor(aspect: String, amount: Int): this(ASPECT_REGISTRY.get(LostArcana.id(aspect))!!, amount)
}
