package com.oyosite.ticon.lostarcana.fabric.block

import com.oyosite.ticon.lostarcana.blockentity.WardedJarBlockEntity
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.Storage
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.fabricmc.fabric.impl.transfer.TransferApiImpl
import kotlin.math.min

@JvmInline
value class WardedJarFluidStorage(val be: WardedJarBlockEntity): Storage<FluidVariant>, StorageView<FluidVariant> {

    //WARNING: This is probably a little broken. I don't think it can currently handle more than one fluid interaction occurring in a single transaction.

    override fun insert(
        variant: FluidVariant,
        amount: Long,
        ctx: TransactionContext
    ): Long {
        val stack = be.fluidContents
        if(!stack.isEmpty && variant != FluidStackHooksFabric.toFabric(stack)) return 0L
        if(stack.amount + amount < be.maxFluidAmount){
            ctx.addOuterCloseCallback { r ->
                val s = if(stack.isEmpty) FluidStackHooksFabric.fromFabric(variant, amount) else stack.apply{grow(amount)}
                if(r.wasCommitted()) be.fluidContents = s
            }

            return amount
        } else {
            val amt = min(amount, be.maxFluidAmount-stack.amount)
            ctx.addOuterCloseCallback { r ->
                val s = if(stack.isEmpty) FluidStackHooksFabric.fromFabric(variant, amt) else stack.apply{grow(amt)}
                if(r.wasCommitted()) be.fluidContents = s
            }
            return amt
        }
    }

    override fun extract(
        variant: FluidVariant,
        amount: Long,
        ctx: TransactionContext
    ): Long {
        val stack = be.fluidContents
        if(stack.isEmpty || variant != FluidStackHooksFabric.toFabric(stack))return 0
        val amt = min(amount, stack.amount)
        val remainder = stack.copyWithAmount(stack.amount-amt)
        ctx.addOuterCloseCallback { r ->
            if(r.wasCommitted())be.fluidContents = remainder
        }
        return amt
    }

    override fun isResourceBlank(): Boolean = (be.virtualContents ?: be.fluidContents).isEmpty

    override fun getResource(): FluidVariant = FluidStackHooksFabric.toFabric(be.virtualContents ?: be.fluidContents)

    override fun getAmount(): Long = (be.virtualContents ?: be.fluidContents).amount

    override fun getCapacity(): Long = be.maxFluidAmount

    override fun iterator(): MutableIterator<StorageView<FluidVariant>> = TransferApiImpl.singletonIterator(this)
}