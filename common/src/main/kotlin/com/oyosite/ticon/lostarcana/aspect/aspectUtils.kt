package com.oyosite.ticon.lostarcana.aspect

import net.minecraft.world.item.ItemStack


@Suppress("unchecked_cast")
val ItemStack.aspects: Array<AspectStack> get() = (this.item as IAspectHolder<ItemStack>).`lostarcana$getAspects`(this)

