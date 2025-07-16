package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.unaryPlus
import com.oyosite.ticon.lostarcana.item.invoke
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.Registrar
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

// Why do I make stuff so cursed?
val TEST_BLOCK = "test_block" % { Block(BlockBehaviour.Properties.of()) } % {}








inline operator fun <reified T: Block> String.rem(noinline blockSupplier: ()->T): RegistrySupplier<T> =
    LostArcana.BLOCK_REGISTRY.register(this, blockSupplier)

inline operator fun <reified T: Block> RegistrySupplier<T>.rem(noinline itemPropertiesConfig: Item.Properties.()->Unit): RegistrySupplier<T> =
    this.also { this.registryId.path { BlockItem(+this, Item.Properties().apply(itemPropertiesConfig)) } }
