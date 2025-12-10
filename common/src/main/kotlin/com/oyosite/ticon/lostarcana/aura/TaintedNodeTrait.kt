package com.oyosite.ticon.lostarcana.aura

import net.minecraft.world.level.Level

object TaintedNodeTrait: AuraNodeTrait {
    override fun onGenerateVis(level: Level, source: AuraSource, amount: Float): Float {
        if(level.random.nextFloat() < 0.25) source.flux += amount/2
        return amount
    }
}