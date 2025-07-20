package com.oyosite.ticon.lostarcana.worldgen.feature

import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey


val INFUSED_STONE_FEATURES = INFUSED_STONES.map { ResourceKey.create(Registries.PLACED_FEATURE, it.id.withSuffix("_ore_feature")) }