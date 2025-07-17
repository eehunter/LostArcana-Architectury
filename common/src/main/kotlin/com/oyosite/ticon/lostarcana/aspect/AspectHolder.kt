package com.oyosite.ticon.lostarcana.aspect

fun interface AspectHolder<T> {

    fun getAspects(context: T): Array<Aspect>

}