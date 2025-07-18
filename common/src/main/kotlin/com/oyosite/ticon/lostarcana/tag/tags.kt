package com.oyosite.ticon.lostarcana.tag

import com.oyosite.ticon.lostarcana.LostArcana
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

val COMMON_ORES = TagKey<Block>.create(Registries.BLOCK, LostArcana.id("c:ores"))