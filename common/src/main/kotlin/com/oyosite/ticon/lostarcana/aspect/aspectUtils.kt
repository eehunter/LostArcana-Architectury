package com.oyosite.ticon.lostarcana.aspect

import com.oyosite.ticon.lostarcana.AspectStacks
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import org.jetbrains.annotations.ApiStatus


@Suppress("unchecked_cast")
val ItemStack.aspects: AspectStacks get() = (this.item as IAspectHolder<ItemStack>).`lostarcana$getAspects`(this)

@Suppress("unchecked_cast")
@ApiStatus.Experimental
fun Item.setStaticAspects(aspects: AspectStacks) = (this as IAspectHolder<ItemStack>).`lostarcana$setStaticAspects`(aspects)

@Suppress("unchecked_cast")
@ApiStatus.Experimental
fun Item.setAspectGetter(aspectGetter: (ItemStack)->AspectStacks) = (this as IAspectHolder<ItemStack>).`lostarcana$setAspectGetter`(aspectGetter)

fun ItemLike.setStaticAspects(vararg aspects: AspectStack) = this.asItem().setStaticAspects(listOf(*aspects))

operator fun Int.times(aspect: Aspect) = AspectStack(aspect, this)