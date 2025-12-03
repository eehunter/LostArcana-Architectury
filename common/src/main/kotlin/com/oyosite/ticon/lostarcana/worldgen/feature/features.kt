package com.oyosite.ticon.lostarcana.worldgen.feature

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.util.platformRegisterFeature
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey


val AURA_NODE_FEATURE = platformRegisterFeature("aura_node"){ AuraNodeFeature(AuraNodeFeature.Config.CODEC) }
val PLACED_AURA_NODE = ResourceKey.create(Registries.PLACED_FEATURE, LostArcana.id("placed_aura_node"))

val INFUSED_STONE_FEATURES = INFUSED_STONES.map { ResourceKey.create(Registries.PLACED_FEATURE, it.id.withSuffix("_ore_feature")) }
val SPARSE_GREATWOOD_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, LostArcana.id("trees_greatwood_sparse"))