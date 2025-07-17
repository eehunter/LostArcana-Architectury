package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.BlockProperties
import com.oyosite.ticon.lostarcana.LostArcana.MOD_ID
import com.oyosite.ticon.lostarcana.item.times
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

val BLOCK_REGISTRY: DeferredRegister<Block> = DeferredRegister.create(MOD_ID, Registries.BLOCK)
/** Returns a new instance of BlockBehaviour.Properties each time it is referenced.*/
val prop: BlockProperties get() = BlockBehaviour.Properties.of()

// Why do I make stuff so cursed?
val TEST_BLOCK = "test_block" % { Block(prop) } % {}









inline operator fun <reified T: Block> String.rem(noinline blockSupplier: ()->T): RegistrySupplier<T> =
    BLOCK_REGISTRY.register(this, blockSupplier)

inline operator fun <reified T: Block> RegistrySupplier<T>.rem(noinline itemPropertiesConfig: Item.Properties.()->Unit): RegistrySupplier<T> =
    this.also { this.id.path * { BlockItem(+this, Item.Properties().apply(itemPropertiesConfig)) } }
