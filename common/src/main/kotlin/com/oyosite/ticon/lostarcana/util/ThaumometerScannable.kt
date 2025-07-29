package com.oyosite.ticon.lostarcana.util

import net.minecraft.world.item.context.UseOnContext

interface ThaumometerScannable {
    fun onScan(context: UseOnContext)
}