package com.oyosite.ticon.lostarcana

import com.oyosite.ticon.lostarcana.block.BLOCK_REGISTRY
import com.oyosite.ticon.lostarcana.block.TEST_BLOCK
import com.oyosite.ticon.lostarcana.item.ITEM_REGISTRY
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object LostArcana {
    const val MOD_ID: String = "lostarcana"
    fun id(id: String): Identifier = Identifier.parse(if(id.contains(":"))id else "$MOD_ID:$id")


    @JvmStatic
    fun init() {
        println("Hello world")
        BLOCK_REGISTRY.register()
        ITEM_REGISTRY.register()
    }
}
