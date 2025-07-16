package com.oyosite.ticon.lostarcana

import dev.architectury.registry.registries.RegistrySupplier

inline operator fun <reified T> RegistrySupplier<T>.unaryPlus(): T = get()