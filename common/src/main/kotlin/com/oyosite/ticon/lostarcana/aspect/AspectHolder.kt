package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.AspectStacks

interface AspectHolder<T> {

    val staticAspects: AspectStacks? get() = null
    fun getAspects(context: T): AspectStacks? = staticAspects

}