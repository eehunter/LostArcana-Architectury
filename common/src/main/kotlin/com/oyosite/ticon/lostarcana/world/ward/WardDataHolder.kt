package com.oyosite.ticon.lostarcana.world.ward

import java.util.UUID

interface WardDataHolder {
    fun getWardData(): ChunkWardData?
    fun getOrCreateWardData(): ChunkWardData
}