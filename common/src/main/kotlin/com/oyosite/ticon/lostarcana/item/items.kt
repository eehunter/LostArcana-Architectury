package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.LostArcana.MOD_ID
import com.oyosite.ticon.lostarcana.aspect.AER
import com.oyosite.ticon.lostarcana.block.fluid.ESSENTIA_FLUID
import com.oyosite.ticon.lostarcana.item.aura.FluxerItem
import com.oyosite.ticon.lostarcana.item.focus.CastingFocusItem
import com.oyosite.ticon.lostarcana.item.focus.VisLightEffect
import com.oyosite.ticon.lostarcana.itemTag
import com.oyosite.ticon.lostarcana.tag.COMMON_GOLD_INGOTS
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.core.item.ArchitecturyBucketItem
import dev.architectury.injectables.annotations.ExpectPlatform
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.Util
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

val ITEM_REGISTRY:  DeferredRegister<Item>  = DeferredRegister.create(MOD_ID, Registries.ITEM)

val ALCHEMICAL_BRASS_INGOT = "alchemical_brass_ingot" * { Item(Item.Properties()) }
val ALCHEMICAL_BRASS_NUGGET = "alchemical_brass_nugget" * { Item(Item.Properties()) }
val THAUMIUM_INGOT = "thaumium_ingot" * { Item(Item.Properties()) }
val THAUMIUM_NUGGET = "thaumium_nugget" * { Item(Item.Properties()) }

val VIS_CRYSTAL = "vis_crystal" * { VisCrystalItem(Item.Properties()) }
val SALIS_MUNDIS = "salis_mundis" * { SalisMundisItem(Item.Properties()) }

val CRUDE_CASTER_GAUNTLET = "crude_caster_gauntlet" * { CasterGauntlet(Item.Properties().stacksTo(1)) }
val IRON_WAND_CAP = "iron_wand_cap" * { WandCap(Item.Properties(), 0.95f, 0xAAAAAAu) }
val GOLD_WAND_CAP = "gold_wand_cap" * { WandCap(Item.Properties(), 1f, 0xffdb05u) }
val THAUMIUM_WAND_CAP = "thaumium_wand_cap" * { WandCap(Item.Properties(), 1.05f, 0x6e00a0u) }
val WOOD_WAND_CORE = "wood_wand_core" * { WandCore(Item.Properties(), 25f, 0x75461fu) }
val GREATWOOD_WAND_CORE = "greatwood_wand_core" * { WandCore(Item.Properties(), 50f, 0x503015u) }

val WAND_ITEM = "wand" * { WandItem(Item.Properties().stacksTo(1).fireResistant(), 0f) }
val CASTER_GAUNTLET = "caster_gauntlet" * { CasterGauntlet(Item.Properties().stacksTo(1).component(WAND_CAP, (+IRON_WAND_CAP).castingItemComponent).fireResistant()) }

val FOCUS = "casting_focus" * { CastingFocusItem(Item.Properties().stacksTo(1).fireResistant()) }
val TEST_FOCUS = "test_light_focus" * { CastingFocusItem(Item.Properties().stacksTo(1).fireResistant().component(FOCUS_EFFECT, VisLightEffect())) }

val WOOD_PLANKS = itemTag("minecraft:planks")

val ESSENTIA_BUCKET_ITEM = "essentia_bucket" * { EssentiaBucketItem(ESSENTIA_FLUID::get, Item.Properties().component(RAW_ASPECT_COMPONENT, AER)) }

val GOGGLES_OF_REVEALING_MATERIAL = "goggles_of_revealing" % {
    ArmorMaterial(Util.make(EnumMap(ArmorItem.Type::class.java)) {

    }, 22, SoundEvents.ARMOR_EQUIP_LEATHER, { Ingredient.of(COMMON_GOLD_INGOTS) }, listOf(), 0f, 0f)
}
val GOGGLES_OF_REVEALING = "goggles_of_revealing" * { GogglesOfRevealingItem(Item.Properties().stacksTo(1), GOGGLES_OF_REVEALING_MATERIAL) }

val THAUMOMETER = "thaumometer" * { ThaumometerItem(Item.Properties().stacksTo(1)) }

val FLUXER = "fluxer" * { FluxerItem(Item.Properties()) }

@ExpectPlatform
fun platformRegisterArmorMaterial(name: String, materialSupplier: ()-> ArmorMaterial): Holder<ArmorMaterial> = throw AssertionError()

operator fun String.rem(materialSupplier: ()-> ArmorMaterial): Holder<ArmorMaterial> = platformRegisterArmorMaterial(this, materialSupplier)


inline operator fun <reified T: Item> String.times(noinline itemSupplier: ()->T): RegistrySupplier<T> =
    ITEM_REGISTRY.register(this, itemSupplier)