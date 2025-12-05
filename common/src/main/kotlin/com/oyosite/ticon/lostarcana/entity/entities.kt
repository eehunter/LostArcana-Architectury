package com.oyosite.ticon.lostarcana.entity

import com.oyosite.ticon.lostarcana.LostArcana
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import java.util.function.Supplier


val ENTITY_REGISTRY: DeferredRegister<EntityType<*>> = DeferredRegister.create(LostArcana.MOD_ID, Registries.ENTITY_TYPE)



val AURA_NODE = "aura_node" % {
    EntityType.Builder.of(::AuraNodeEntity, MobCategory.MISC)
        .sized(.5f, .5f)
        .eyeHeight(.25f)
        .fireImmune()
        .canSpawnFarFromPlayer()
        .clientTrackingRange(8)
        .updateInterval(10)
        .build(ResourceKey.create(Registries.ENTITY_TYPE,
        LostArcana.id("aura_node")).toString())
}





inline operator fun <reified T: Entity> String.rem(supplier: Supplier<EntityType<T>>): RegistrySupplier<EntityType<T>> =
    ENTITY_REGISTRY.register(this, supplier)


