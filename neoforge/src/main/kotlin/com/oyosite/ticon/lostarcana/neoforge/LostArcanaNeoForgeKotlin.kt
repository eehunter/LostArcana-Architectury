package com.oyosite.ticon.lostarcana.neoforge

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.LostArcana.id
import com.oyosite.ticon.lostarcana.aspect.ALL_ASPECTS
import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECT_REGISTRY_KEY
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.platform_aspect_registry
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.InfusedStoneBlock
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.item.ASPECTS_COMPONENT
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.item.VIS_STORAGE_COMPONENT
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NewRegistryEvent
import net.neoforged.neoforge.registries.RegistryBuilder
import java.util.function.Supplier


@Mod(LostArcana.MOD_ID)
class LostArcanaNeoForgeKotlin(modEventBus: IEventBus) {

    val DATA_COMPONENT_REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, LostArcana.MOD_ID)

    init {
        print("Hello world NeoForge")



        platform_aspect_registry = RegistryBuilder(ASPECT_REGISTRY_KEY).sync(true).defaultKey(PRIMAL_ASPECTS[0].id).create()
        ALL_ASPECTS.forEach { (name, aspect) -> NEOFORGE_ASPECTS.register(name, Supplier{ aspect }) }
        NEOFORGE_ASPECTS.register(modEventBus)


        DATA_COMPONENT_REGISTRAR.register("aspect", Supplier { ASPECT_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("aspects", Supplier { ASPECTS_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("vis_storage", Supplier { VIS_STORAGE_COMPONENT })

        DATA_COMPONENT_REGISTRAR.register(modEventBus)

        LostArcana.init()
    }

    @EventBusSubscriber(modid = LostArcana.MOD_ID)
    companion object {
        val NEOFORGE_ASPECTS = DeferredRegister.create(ASPECT_REGISTRY_KEY, LostArcana.MOD_ID)

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        @JvmStatic
        fun registerRegistries(event: NewRegistryEvent) {
            event.register(AspectRegistry.ASPECT_REGISTRY)
        }

        private val infusedStoneBlocks get() = INFUSED_STONES.map(RegistrySupplier<InfusedStoneBlock>::get).toTypedArray()

        @SubscribeEvent
        @JvmStatic
        fun onRegisterItemColorProviders(event: RegisterColorHandlersEvent.Item){
            event.register(LostArcanaClient.VIS_CRYSTAL_ITEM_COLOR, VIS_CRYSTAL.value(), *infusedStoneBlocks)
        }

        @SubscribeEvent
        @JvmStatic
        fun onRegisterBlockColorProviders(event: RegisterColorHandlersEvent.Block){
            event.register(LostArcanaClient.INFUSED_STONE_BLOCK_COLOR, *infusedStoneBlocks)
        }


        @SubscribeEvent // on the mod event bus
        @JvmStatic
        @Suppress("unchecked_cast")
        fun createDefaultAttributes(event: EntityAttributeModificationEvent) {
            event.add(EntityType.PLAYER, ARCANE_INSIGHT as Holder<Attribute>)
            event.add(EntityType.PLAYER, ARCANE_SIGHT as Holder<Attribute>)
        }

        @SubscribeEvent
        @JvmStatic
        fun onCommonSetup(event: FMLCommonSetupEvent) {
        }

    }
}