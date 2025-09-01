package com.oyosite.ticon.lostarcana.neoforge

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.ASPECT_REGISTRY_KEY
import com.oyosite.ticon.lostarcana.aspect.registry.AspectRegistry.platform_aspect_registry
import com.oyosite.ticon.lostarcana.attribute.ARCANE_INSIGHT
import com.oyosite.ticon.lostarcana.attribute.ARCANE_SIGHT
import com.oyosite.ticon.lostarcana.attribute.ATTRIBUTE_REGISTRY
import com.oyosite.ticon.lostarcana.block.INFUSED_STONES
import com.oyosite.ticon.lostarcana.block.InfusedStoneBlock
import com.oyosite.ticon.lostarcana.block.NITOR
import com.oyosite.ticon.lostarcana.block.VIS_LIGHT
import com.oyosite.ticon.lostarcana.blockentity.ARCANE_COLUMN_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.ARCANE_PEDESTAL_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.ARCANE_WORKBENCH_MENU_SCREEN
import com.oyosite.ticon.lostarcana.blockentity.CRUCIBLE_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.MAGIC_BRICKS_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.RECHARGE_PEDESTAL_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.WARDED_JAR_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.client.LostArcanaClient
import com.oyosite.ticon.lostarcana.client.LostArcanaClient.AURA_NODE_MODEL_LAYER
import com.oyosite.ticon.lostarcana.client.blockentity.ArcaneColumnRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.ArcaneWorkbenchScreen
import com.oyosite.ticon.lostarcana.client.blockentity.CrucibleBlockEntityRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.MagicBricksBlockEntityRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.PedestalRenderer
import com.oyosite.ticon.lostarcana.client.blockentity.WardedJarRenderer
import com.oyosite.ticon.lostarcana.client.entity.AuraNodeEntityRenderer
import com.oyosite.ticon.lostarcana.entity.AURA_NODE
import com.oyosite.ticon.lostarcana.item.*
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusEffectType
import com.oyosite.ticon.lostarcana.item.focus.registerBuiltinEffectTypes
import com.oyosite.ticon.lostarcana.neoforge.block.WardedJarFluidHandler
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.ai.attributes.Attribute
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NewRegistryEvent
import net.neoforged.neoforge.registries.RegistryBuilder
import java.util.function.Supplier


@Mod(LostArcana.MOD_ID)
class LostArcanaNeoForge(modEventBus: IEventBus) {

    val DATA_COMPONENT_REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, LostArcana.MOD_ID)
    init {
        print("Hello world NeoForge")


        NEOFORGE_ASPECTS.register(modEventBus)
        NEOFORGE_ATTRIBUTES.register(modEventBus)
        NEOFORGE_BLOCK_ENTITY_TYPES.register(modEventBus)
        NEOFORGE_RECIPE_TYPES.register(modEventBus)
        NEOFORGE_RECIPE_SERIALIZERS.register(modEventBus)
        NEOFORGE_MENU_SCREENS.register(modEventBus)
        NEOFORGE_CASTING_EFFECT_TYPES.register(modEventBus)
        NEOFORGE_LOOT_FUNCTIONS.register(modEventBus)

        CastingFocusEffectType.REGISTRY
        registerBuiltinEffectTypes()

        PRIMAL_ASPECTS
        ATTRIBUTE_REGISTRY

        NEOFORGE_ARMOR_MATERIALS.register(modEventBus)

        NEOFORGE_ADVANCEMENT_TRIGGERS.register(modEventBus)

        DATA_COMPONENT_REGISTRAR.register("aspect", Supplier { ASPECT_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("aspects", Supplier { ASPECTS_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("vis_storage", Supplier { VIS_STORAGE_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("wand_cap", Supplier { WAND_CAP })
        DATA_COMPONENT_REGISTRAR.register("wand_cap_2", Supplier { WAND_CAP_2 })
        DATA_COMPONENT_REGISTRAR.register("wand_cap_3", Supplier { WAND_CAP_3 })
        DATA_COMPONENT_REGISTRAR.register("wand_core", Supplier { WAND_CORE })
        DATA_COMPONENT_REGISTRAR.register("resonator", Supplier { RESONATOR })
        DATA_COMPONENT_REGISTRAR.register("focus_effect", Supplier { FOCUS_EFFECT })
        DATA_COMPONENT_REGISTRAR.register("focus", Supplier { FOCUS_COMPONENT })
        DATA_COMPONENT_REGISTRAR.register("single_fluid_stack", Supplier { SINGLE_FLUID_STORAGE_COMPONENT })

        DATA_COMPONENT_REGISTRAR.register(modEventBus)

        LostArcana.init()
    }

    @EventBusSubscriber(modid = LostArcana.MOD_ID)
    companion object {
        val NEOFORGE_ASPECT_REGISTRY = RegistryBuilder(ASPECT_REGISTRY_KEY).sync(true).defaultKey(PRIMAL_ASPECTS[0].id).create()

        val NEOFORGE_ASPECTS = DeferredRegister.create(ASPECT_REGISTRY_KEY, LostArcana.MOD_ID)
        val NEOFORGE_ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, LostArcana.MOD_ID)
        val NEOFORGE_ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, LostArcana.MOD_ID)
        val NEOFORGE_ADVANCEMENT_TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, LostArcana.MOD_ID)
        val NEOFORGE_BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, LostArcana.MOD_ID)
        val NEOFORGE_RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, LostArcana.MOD_ID)
        val NEOFORGE_RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, LostArcana.MOD_ID)
        val NEOFORGE_MENU_SCREENS = DeferredRegister.create(Registries.MENU, LostArcana.MOD_ID)
        val NEOFORGE_CASTING_EFFECT_TYPES = DeferredRegister.create(CastingFocusEffectType.REGISTRY_KEY, LostArcana.MOD_ID)
        val NEOFORGE_LOOT_FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, LostArcana.MOD_ID)

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        @JvmStatic
        fun registerRegistries(event: NewRegistryEvent) {
            event.register(AspectRegistry.ASPECT_REGISTRY)
            event.register(CastingFocusEffectType.REGISTRY)
        }

        private val infusedStoneBlocks get() = INFUSED_STONES.map(RegistrySupplier<InfusedStoneBlock>::get).toTypedArray()

        @SubscribeEvent
        @JvmStatic
        fun onRegisterItemColorProviders(event: RegisterColorHandlersEvent.Item){
            event.register(LostArcanaClient.VIS_CRYSTAL_ITEM_COLOR, +VIS_CRYSTAL, *infusedStoneBlocks)
            event.register(LostArcanaClient.THAUMOMETER_ITEM_COLOR, +THAUMOMETER, +GOGGLES_OF_REVEALING)
            event.register(LostArcanaClient.WAND_ITEM_COLOR, +WAND_ITEM)
            event.register(LostArcanaClient.NITOR_ITEM_COLOR, +NITOR)
        }

        @SubscribeEvent
        @JvmStatic
        fun onRegisterBlockColorProviders(event: RegisterColorHandlersEvent.Block){
            event.register(LostArcanaClient.INFUSED_STONE_BLOCK_COLOR, *infusedStoneBlocks)
            event.register(LostArcanaClient.NITOR_BLOCK_COLOR, +NITOR, +VIS_LIGHT)
        }


        @SubscribeEvent // on the mod event bus
        @JvmStatic
        @Suppress("unchecked_cast")
        fun createDefaultAttributes(event: EntityAttributeModificationEvent) {
            event.types.forEach {
                event.add(it, ARCANE_INSIGHT as Holder<Attribute>)
                event.add(it, ARCANE_SIGHT as Holder<Attribute>)
            }


        }

        @SubscribeEvent
        @JvmStatic
        fun onRegisterEntityRenderers(event: RegisterRenderers){
            event.registerEntityRenderer(+AURA_NODE, ::AuraNodeEntityRenderer)
        }

        @SubscribeEvent
        @JvmStatic
        fun onRegisterEntityModelLayers(event: EntityRenderersEvent.RegisterLayerDefinitions){
            event.registerLayerDefinition(AURA_NODE_MODEL_LAYER, AuraNodeEntityRenderer::getTexturedModelData)
            event.registerLayerDefinition(LostArcanaClient.CRUCIBLE_CONTENTS_MODEL_LAYER, CrucibleBlockEntityRenderer::getTexturedModelData)
        }

        @SubscribeEvent
        fun registerRenderers(event: RegisterRenderers) {
            event.registerBlockEntityRenderer(MAGIC_BRICKS_BLOCK_ENTITY.value()) { MagicBricksBlockEntityRenderer() }
            event.registerBlockEntityRenderer(ARCANE_COLUMN_BLOCK_ENTITY.value()) { ArcaneColumnRenderer() }
            event.registerBlockEntityRenderer(RECHARGE_PEDESTAL_BLOCK_ENTITY.value()) { PedestalRenderer() }
            event.registerBlockEntityRenderer(ARCANE_PEDESTAL_BLOCK_ENTITY.value()) { PedestalRenderer() }
            event.registerBlockEntityRenderer(CRUCIBLE_BLOCK_ENTITY.value()) { CrucibleBlockEntityRenderer(it) }
            event.registerBlockEntityRenderer(WARDED_JAR_BLOCK_ENTITY.value()) { WardedJarRenderer() }
        }

        @SubscribeEvent
        fun registerMenuScreens(event: RegisterMenuScreensEvent){
            event.register(ARCANE_WORKBENCH_MENU_SCREEN.value(), ::ArcaneWorkbenchScreen)
        }

        @SubscribeEvent
        fun registerCapabilities(event: RegisterCapabilitiesEvent){
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, WARDED_JAR_BLOCK_ENTITY.value()){ be, dir -> WardedJarFluidHandler(be) }
        }

        @SubscribeEvent
        @JvmStatic
        fun onCommonSetup(event: FMLCommonSetupEvent) {
        }

        @SubscribeEvent
        @JvmStatic
        fun onClientSetup(event: FMLClientSetupEvent){
            //LostArcanaClient.initClient()
        }

    }
}