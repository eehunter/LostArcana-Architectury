package com.oyosite.ticon.lostarcana

import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

fun itemTag(id: String): TagKey<Item> = TagKey.create(Registries.ITEM, LostArcana.id(id))
fun blockTag(id: String): TagKey<Block> = TagKey.create(Registries.BLOCK, LostArcana.id(id))

operator fun <T> RegistrySupplier<T>.unaryPlus(): T = get()


@Suppress("unchecked_cast")
val Player.canSeeAuraNode: Boolean get() = (getAttribute(ARCANE_SIGHT as Holder<Attribute>)?.value ?: .0) >= 1.0
