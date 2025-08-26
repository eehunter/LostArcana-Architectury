package com.oyosite.ticon.lostarcana.block

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.grower.TreeGrower
import java.util.Optional

val GREATWOOD_FEATURE = ResourceKey.create(
    Registries.CONFIGURED_FEATURE,
    ResourceLocation.withDefaultNamespace("greatwood")
)

val GREATWOOD_GROWER = TreeGrower("greatwood", Optional.empty(), Optional.of(GREATWOOD_FEATURE), Optional.empty())