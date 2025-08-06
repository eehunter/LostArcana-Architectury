package com.oyosite.ticon.lostarcana.emi

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.SALIS_MUNDIS
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import com.oyosite.ticon.lostarcana.item.WAND_ITEM
import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import com.oyosite.ticon.lostarcana.recipe.SalisMundisTransformRecipe
import com.oyosite.ticon.lostarcana.tag.COMMON_REDSTONE_DUSTS
import com.oyosite.ticon.lostarcana.tag.WAND_CAPS
import com.oyosite.ticon.lostarcana.tag.WAND_CORES
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiCraftingRecipe
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.client.Minecraft
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.crafting.Ingredient

@EmiEntrypoint
class LostArcanaEmiPlugin: EmiPlugin {
    val E = EmiIngredient.of(Ingredient.EMPTY)
    val visCrystal = EmiIngredient.of(PRIMAL_ASPECTS.map(Aspect::unaryPlus).map { ItemStack(+VIS_CRYSTAL).apply{ set(ASPECT_COMPONENT, it) } }.map{ Ingredient.of(it) }.map(EmiIngredient::of))

    override fun register(emiRegistry: EmiRegistry) {
        emiRegistry.addCategory(ArcaneWorkbenchEmi.CATEGORY)
        emiRegistry.addWorkstation(ArcaneWorkbenchEmi.CATEGORY, ArcaneWorkbenchEmi.WORKSTATION)

        val recipeManager = emiRegistry.recipeManager
        for (recipe in recipeManager.getAllRecipesFor(ArcaneWorkbenchRecipe.Type)){
            emiRegistry.addRecipe(ArcaneWorkbenchEmi(recipe.id, recipe.value))
        }

        for (recipe in recipeManager.getAllRecipesFor(SalisMundisTransformRecipe.Type)){
            emiRegistry.addRecipe(EmiWorldInteractionRecipe.builder().id(recipe.id).leftInput(EmiIngredient.of(
                Ingredient.of(+SALIS_MUNDIS))).rightInput(EmiIngredient.of(recipe.value.ingredients.first()), false).output(EmiStack.of(recipe.value.getResultItem(
                Minecraft.getInstance().level!!.registryAccess()))).build())
        }

        registerModifyCastingItemRecipes(emiRegistry)

        val salisMundisCraftingLore = ItemLore(listOf(
            Component.translatable(SALIS_MUNDIS_TOOLTIP)
        ))
        emiRegistry.addRecipe(EmiCraftingRecipe(
            listOf(visCrystal, visCrystal, visCrystal, EmiIngredient.of(COMMON_REDSTONE_DUSTS)),
            EmiStack.of(ItemStack(SALIS_MUNDIS.get()).apply { set(DataComponents.LORE, salisMundisCraftingLore) }),
            LostArcana.id("/crafting/salis_mundis")
        ))
    }

    fun registerModifyCastingItemRecipes(emiRegistry: EmiRegistry){
        val wandCap = EmiIngredient.of(WAND_CAPS)
        val wand = EmiIngredient.of(Ingredient.of(WAND_ITEM.get()))
        val wandCore = EmiIngredient.of(WAND_CORES)
        val modifiedWandLore = ItemLore(listOf(
            Component.translatable(MODIFIED_WAND_TOOLTIP_1),
            Component.translatable(MODIFIED_WAND_TOOLTIP_2),
            Component.translatable(MODIFIED_WAND_TOOLTIP_3),
            Component.translatable(MODIFIED_WAND_TOOLTIP_4),
        ))
        val modifiedWand = EmiStack.of(ItemStack(WAND_ITEM.get()).apply{ set(DataComponents.LORE, modifiedWandLore) })
        emiRegistry.addRecipe(EmiCraftingRecipe(
            listOf(
                E,E,wandCap,
                E,wand,wandCore,
                wandCap,E,E
            ),
            modifiedWand,
            LostArcana.id("/crafting/modified_wand"),
            false
        ))
    }

    companion object{
        val SALIS_MUNDIS_TOOLTIP = "tooltip.emi.salis_mundis"

        val MODIFIED_WAND_TOOLTIP_1 = "tooltip.emi.modified_wand_1"
        val MODIFIED_WAND_TOOLTIP_2 = "tooltip.emi.modified_wand_2"
        val MODIFIED_WAND_TOOLTIP_3 = "tooltip.emi.modified_wand_3"
        val MODIFIED_WAND_TOOLTIP_4 = "tooltip.emi.modified_wand_4"
    }
}