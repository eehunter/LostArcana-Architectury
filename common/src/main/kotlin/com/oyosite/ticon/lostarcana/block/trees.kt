package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.grower.TreeGrower
import java.util.Optional

val GREATWOOD_FEATURE = ResourceKey.create(
    Registries.CONFIGURED_FEATURE,
    LostArcana.id("greatwood")
)

val GREATWOOD_GROWER = TreeGrower("greatwood", Optional.of(GREATWOOD_FEATURE), Optional.empty(), Optional.empty())