package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.Identifier

open class Aspect(val id: Identifier, val color: UInt, val translationKey: String = "aspects.${id.namespace}.${id.path}.name") {

    operator fun times(amount: Int): AspectStack = AspectStack(this, amount)

    operator fun unaryPlus(): AspectStack = this * 1

}