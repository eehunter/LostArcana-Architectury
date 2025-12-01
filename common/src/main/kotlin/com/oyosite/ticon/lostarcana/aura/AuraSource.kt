package com.oyosite.ticon.lostarcana.aura

import net.minecraft.world.phys.Vec3

interface AuraSource {
    var vis: Float
    var flux: Float
    val fluxAffinity: Float get() = 1f
    val pos: Vec3
}