package com.oyosite.ticon.lostarcana.fabric.client

import com.oyosite.ticon.lostarcana.item.RAW_ASPECT_COMPONENT
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import kotlin.jvm.optionals.getOrNull


val FluidVariant.aspectColor: Int get() = (componentMap[RAW_ASPECT_COMPONENT]?:components[RAW_ASPECT_COMPONENT]?.getOrNull())?.color?.or(0xFF000000u)?.toInt()?:-1