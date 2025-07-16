package com.oyosite.ticon.lostarcana

import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object LostArcana {
    const val MOD_ID: String = "lostarcana"
    fun id(id: String): Identifier = Identifier.parse(if(id.contains(":"))id else "$MOD_ID:$id")

    val BLOCK_REGISTRY: DeferredRegister<Block> = DeferredRegister.create(MOD_ID, Registries.BLOCK)
    val ITEM_REGISTRY:  DeferredRegister<Item>  = DeferredRegister.create(MOD_ID, Registries.ITEM)

    @JvmStatic
    fun init() {
        BLOCK_REGISTRY.register()
        ITEM_REGISTRY.register()
    }
}
