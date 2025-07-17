package com.oyosite.ticon.lostarcana

import com.oyosite.ticon.lostarcana.aspect.registerAspectsForVanillaItems
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECTS
import com.oyosite.ticon.lostarcana.block.BLOCK_REGISTRY
import com.oyosite.ticon.lostarcana.item.ITEM_REGISTRY
import dev.architectury.platform.Platform

object LostArcana {
    const val MOD_ID: String = "lostarcana"
    fun id(id: String): Identifier = Identifier.parse(if(id.contains(":"))id else "$MOD_ID:$id")


    @JvmStatic
    fun init() {
        println("Hello world")
        registerAspectsForVanillaItems()
        if(Platform.isFabric())ASPECTS.register()//
        BLOCK_REGISTRY.register()
        ITEM_REGISTRY.register()
    }
}
