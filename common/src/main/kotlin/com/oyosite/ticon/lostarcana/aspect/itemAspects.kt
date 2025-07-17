package com.oyosite.ticon.lostarcana.aspect

import net.minecraft.world.level.block.Blocks.*


fun registerAspectsForVanillaItems(){
    COBBLESTONE.setStaticAspects(+TERRA, +PERDITIO)
    STONE.setStaticAspects(2*TERRA)
}

