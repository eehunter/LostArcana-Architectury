package com.oyosite.ticon.lostarcana.fabric.block

import com.oyosite.ticon.lostarcana.aspect.AER
import com.oyosite.ticon.lostarcana.item.ESSENTIA_BUCKET_ITEM
import com.oyosite.ticon.lostarcana.item.RAW_ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.unaryPlus
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.core.component.TypedDataComponent
import kotlin.jvm.optionals.getOrNull


fun fillEssentiaBucket(fluid: FluidVariant, maxAmount: Long, transaction: TransactionContext, context: ContainerItemContext): Long{
    val newVariant = ItemVariant.of(+ESSENTIA_BUCKET_ITEM, DataComponentPatch.builder().apply { context.itemVariant.components.entrySet().forEach { set(TypedDataComponent.createUnchecked(it.key, it.value)) } }.set(
        RAW_ASPECT_COMPONENT, fluid.components.get(RAW_ASPECT_COMPONENT)?.getOrNull()?:AER
    ).build())

    if (context.exchange(newVariant, 1, transaction) == 1L) {
        return FluidConstants.BUCKET
    }
    return 0
}