package com.oyosite.ticon.lostarcana.aura

import net.minecraft.world.level.Level
import kotlin.math.min

object PureNodeTrait: AuraNodeTrait {
    override fun onTick(level: Level, source: AuraSource) {
        if(level.gameTime % 20L != 0L) return

        if(level.random.nextFloat()< 0.01)
            source.flux -= min(1f, source.flux)
    }
}