package com.oyosite.ticon.lostarcana

import com.oyosite.ticon.lostarcana.aspect.registerAspectsForVanillaItems
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECTS
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.attribute.ATTRIBUTE_REGISTRY
import com.oyosite.ticon.lostarcana.block.BLOCK_REGISTRY
import com.oyosite.ticon.lostarcana.entity.ENTITY_REGISTRY
import com.oyosite.ticon.lostarcana.item.ITEM_REGISTRY
import dev.architectury.platform.Platform
import dev.architectury.registry.level.entity.EntityAttributeRegistry
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player

object LostArcana {
    const val MOD_ID: String = "lostarcana"
    fun id(id: String): Identifier = Identifier.parse(if(id.contains(":"))id else "$MOD_ID:$id")


    @JvmStatic
    fun init() {
        println("Hello world")
        registerAspectsForVanillaItems()
        if(Platform.isFabric())ASPECTS.register()// NeoForge doesn't like calling Architectury DeferredRegisters from the mod's main entrypoint
        BLOCK_REGISTRY.register()
        ITEM_REGISTRY.register()
        if(Platform.isFabric())ATTRIBUTE_REGISTRY.register()
        ENTITY_REGISTRY.register()

        if(Platform.isFabric())EntityAttributeRegistry.register({ EntityType.PLAYER }){Player.createAttributes().add(ARCANE_SIGHT).add(ARCANE_INSIGHT)}
    }
}
