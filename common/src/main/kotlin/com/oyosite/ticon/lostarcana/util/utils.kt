package com.oyosite.ticon.lostarcana.util

import com.klikli_dev.modonomicon.registry.DataComponentRegistry
import com.klikli_dev.modonomicon.registry.ItemRegistry
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.item.VisCrystalItem
import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.Vec3

operator fun Vec3.component1() = x
operator fun Vec3.component2() = y
operator fun Vec3.component3() = z


fun Int.testCrystalInSlot(crystal: ItemStack):Boolean =
    (crystal.item as? VisCrystalItem)?.getAspects(crystal)?.get(0)?.aspect == PRIMAL_ASPECTS[this]

fun Slot(inventory: Container, index: Int, x: Int, y: Int, markDirtyCallback: ()->Unit) = object : Slot(inventory, index, x, y){
    override fun setChanged() {
        super.setChanged()
        markDirtyCallback()
    }
}

val thaumonomiconStack get() = ItemStack(ItemRegistry.MODONOMICON.get()).apply{ set(DataComponentRegistry.BOOK_ID.get(), LostArcana.id("thaumonomicon")) }