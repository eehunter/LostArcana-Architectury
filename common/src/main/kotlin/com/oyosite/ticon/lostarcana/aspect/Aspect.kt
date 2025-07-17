package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.Identifier

data class Aspect(val id: Identifier, val color: UInt, val translationKey: String = "aspects.${id.namespace}.${id.path}.name") {

}